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

/**
 * 该类实现了算术交叉
 */
public class ArithmeticalCrossover implements AbstractGeneticAlgorithmOperation {

    private double probability = 1.0;

    /**
     * 
     *设置执行交叉的概率。
     * @param probability 可能性
     */
    @Override
    public void setProbability(double probability) {
        this.probability = probability;
    }

    /**
     *
     *执行交叉
     * @param population Populacja
     * @return 新人口
     */
    @Override
    public Chromosome[] performGeneticOperation(Chromosome[] population) {

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

        double lambda;
        int newPopulationIndex = 0;
        Chromosome[] newPopulation = new Chromosome[population.length];

        for (int i = 0; i < crossTabSize; i++) {

            Chromosome parent1 = crossTab1[i];
            Chromosome parent2 = crossTab2[i];

            double roll = rand.nextDouble();

            if(roll <= probability){

                lambda = rand.nextDouble();

                double[] offspring1Genes = new double[parent1.getSize()];
                double[] offspring2Genes = new double[parent2.getSize()];

                for (int j = 0; j < offspring1Genes.length; j++) {
                    offspring1Genes[j] = lambda * parent1.getGene(j) + (1 - lambda) * parent2.getGene(j);
                    offspring2Genes[j] = lambda * parent2.getGene(j) + (1 - lambda) * parent1.getGene(j);
                }

                Chromosome offspring1 = new Chromosome(offspring1Genes);
                Chromosome offspring2 = new Chromosome(offspring2Genes);

                newPopulation[newPopulationIndex++] = offspring1;
                newPopulation[newPopulationIndex++] = offspring2;

            }else{
                newPopulation[newPopulationIndex++] = parent1;
                newPopulation[newPopulationIndex++] = parent2;
            }
        }

        return newPopulation;
    }
}