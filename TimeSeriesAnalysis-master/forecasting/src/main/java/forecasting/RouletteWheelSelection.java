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

/**
 * 该类实现了轮盘赌方法选择算法
 */
public class RouletteWheelSelection implements AbstractGeneticAlgorithmOperation {

    /**
     * 该方法没有实现，因为选择是遗传算法的操作的特殊情况，其概率未被定义
     * @param probability Prawdopodobienstwo
     */
    @Override
    public void setProbability(double probability) {
    }

    /**
     * 
     *执行轮盘赌方法选择
     * @param population Populacja
     * @return Nowa populacja
     */
    public Chromosome[] performGeneticOperation(Chromosome[] population) {

        Chromosome[] sortedPopulation = new Chromosome[population.length];
        System.arraycopy(population, 0, sortedPopulation, 0, population.length);

        Arrays.sort(sortedPopulation);

        double[] rankingTab = new double[population.length];

        for(int i = 0; i < population.length; i++){
            rankingTab[i] = sortedPopulation[population.length - 1].getFitness() -
                    population[i].getFitness();
        }

        double fitnessSum = 0;
        for (double fitness : rankingTab) {
            fitnessSum += fitness;
        }

        double probabilityTab[] = new double[population.length];

        for (int i = 0; i < probabilityTab.length; i++) {
            probabilityTab[i] = rankingTab[i] / fitnessSum;
        }

        Chromosome[] tempPopulation = new Chromosome[population.length];

        Random rand = new Random();

        for (int i = 0; i < population.length; i++) {
            double roll = rand.nextDouble();
            double currentProb = 0;
            for (int j = 0; j < probabilityTab.length; j++) {
                currentProb += probabilityTab[j];
                if (roll <= currentProb) {
                    tempPopulation[i] = population[j];
                    break;
                }
            }
        }

        return tempPopulation;
    }
}