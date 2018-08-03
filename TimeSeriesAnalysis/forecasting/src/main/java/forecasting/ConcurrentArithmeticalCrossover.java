/**
 * Copyright (c) 2013,
 * Tomasz Choma, Olgierd Grodzki, Łukasz Potępa, Monika Rakoczy, Paweł Synowiec, Łukasz Szarkowicz
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package forecasting;

import forecasting.model.Chromosome;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentArithmeticalCrossover implements AbstractGeneticAlgorithmOperation {

    private double probability = 1.0;

    /**
     * 设置执行交叉的概率。
     *
     * @param probability Prawdopodobienstwo
     */
    @Override
    public void setProbability(double probability) {
        this.probability = probability;
    }

    /**
     * 
     *执行交叉操作
     * @param population 选择后的染色体集合
     * @return 交叉后的染色体集合
     */
    @Override
    public Chromosome[] performGeneticOperation(Chromosome[] population) {
    	
  //  	System.out.println("执行选择之后的染色体集合：");
    	for(int a=0;a<population.length;a++){
  //  		System.out.println(a+" "+Arrays.toString(population[a].getGenes()));
    	}
        int crossTabSize = population.length / 2;
        Chromosome[] crossTab1 = new Chromosome[crossTabSize];
        Chromosome[] crossTab2 = new Chromosome[crossTabSize];
        int[] matchTab = new int[population.length];

        for (int i = 0; i < population.length; i++) {
            matchTab[i] = -1;
        }

        int matchNo = 0;
        Random rand = new Random();

        for (int i = 0; i < crossTabSize; i++) {
            int match = rand.nextInt(population.length);
            while (matchTab[match] != -1) {
                match = rand.nextInt(population.length);
            }
            matchTab[match] = matchNo;
            crossTab1[matchNo] = population[match];
            match = rand.nextInt(population.length);
            while (matchTab[match] != -1) {
                match = rand.nextInt(population.length);
            }
            matchTab[match] = matchNo;
            crossTab2[matchNo] = population[match];
            matchNo++;
        }

        Chromosome[] newPopulation = new Chromosome[population.length];

        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < crossTabSize; i++) {

            Runnable worker = new CrossoverOperation(i,
                    rand,
                    crossTab1,
                    crossTab2,
                    newPopulation);

            executor.execute(worker);
        }

        executor.shutdown();

        try {
            executor.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return newPopulation;
    }

    private class CrossoverOperation extends Thread{

        private int i;
        private Random rand;
        private Chromosome[] crossTab1;
        private Chromosome[] crossTab2;
        private Chromosome[] newPopulation;

        private CrossoverOperation(int i,
                                  Random rand,
                                  Chromosome[] crossTab1,
                                  Chromosome[] crossTab2,
                                  Chromosome[] newPopulation){
            this.i = i;
            this.rand = rand;
            this.crossTab1 = crossTab1;
            this.crossTab2 = crossTab2;
            this.newPopulation = newPopulation;
        }

        @Override
        public void run() {

            Chromosome parent1 = crossTab1[i];
            Chromosome parent2 = crossTab2[i];

            double roll = rand.nextDouble();

            if(roll <= probability){

                double lambda = rand.nextDouble();

                double[] offspring1Genes = new double[parent1.getSize()];
                double[] offspring2Genes = new double[parent2.getSize()];

                for (int j = 0; j < offspring1Genes.length; j++) {
                    offspring1Genes[j] = lambda * parent1.getGene(j) + (1 - lambda) * parent2.getGene(j);
                    offspring2Genes[j] = lambda * parent2.getGene(j) + (1 - lambda) * parent1.getGene(j);
                }

                Chromosome offspring1 = new Chromosome(offspring1Genes);
                Chromosome offspring2 = new Chromosome(offspring2Genes);

                newPopulation[2 * i] = offspring1;
                newPopulation[(2 * i) + 1] = offspring2;

            }else{
                newPopulation[2 * i] = parent1;
                newPopulation[(2 * i) + 1] = parent2;
            }
        }
    }
}
