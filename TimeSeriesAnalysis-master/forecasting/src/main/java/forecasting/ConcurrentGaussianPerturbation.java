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

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentGaussianPerturbation implements AbstractGeneticAlgorithmOperation {

    private double probability = 1.0;

    /**
     * 设置执行变异的概率。
     *
     * @param probability Prawdopodobienstwo
     */
    @Override
    public void setProbability(double probability) {
        this.probability = probability;
    }

    /**
     * 他将突变视为高斯扰动
     *
     * @param population Populacja
     * @return 新人口
     */
    public Chromosome[] performGeneticOperation(Chromosome[] population) {

        Random rand = new Random();
        Chromosome[] newPopulation = new Chromosome[population.length];

        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < population.length; i++) {

            Runnable worker = new MutationOperation(i,
                    rand,
                    population,
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

    private class MutationOperation extends Thread{

        private int i;
        private Random rand;
        private Chromosome[] population;
        private Chromosome[] newPopulation;

        private MutationOperation(int i,
                                   Random rand,
                                   Chromosome[] population,
                                   Chromosome[] newPopulation){
            this.i = i;
            this.rand = rand;
            this.population = population;
            this.newPopulation = newPopulation;
        }

        @Override
        public void run() {

            Chromosome chromosome = new Chromosome(population[i].getGenes());
            newPopulation[i] = chromosome;

            double roll;

            for(int j = 0; j < chromosome.getSize(); j++){
                roll = rand.nextDouble();
                if(roll <= probability){
                    double gauss =  rand.nextGaussian();//产生高斯分布的随机数

                    if(!(chromosome.getGene(j) + gauss < 0)){
                        chromosome.addToGene(j, gauss);
                    }
                }
            }
        }
    }
}

