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
 * 
73/5000
该类实现了随机通用抽样选择算法
 */
public class StochasticUniversalSamplingSelection implements AbstractGeneticAlgorithmOperation {

    /**
     * Metoda nie posiada implementacji, gdyz selekcja jest szczegolnym przypadkiem operacji algorytmu genetycznego
     * dla ktorego nie definiuje sie prawdopodobienstwa
     *
     * @param probability Prawdopodobienstwo
     */
    @Override
    public void setProbability(double probability) {
    }

    /**
     *使用随机通用抽样方法执行选择
     *
     * @param population Populacja
     * @return Nowa populacja
     */
    public Chromosome[] performGeneticOperation(Chromosome[] population) {

        double fitnessSum = 0;
        for (Chromosome chromosome : population) {
            fitnessSum += chromosome.getFitness();
        }

        Random rand = new Random();

        double p = fitnessSum / population.length;
        double start = p * rand.nextDouble();

        double fitnessPoints[] = new double[population.length];

        for (int i = 0; i < fitnessPoints.length; i++) {
            fitnessPoints[i] = start + i * p;
        }

        Chromosome[] tempPopulation = new Chromosome[population.length];
        int ix = 0;

        for (double point : fitnessPoints) {
            double lFitnessSum = 0;
            for(Chromosome chromosome : population){
                lFitnessSum += chromosome.getFitness();
                if(lFitnessSum >= point){
                    tempPopulation[ix++] = chromosome;
                    break;
                }
            }
        }

        return tempPopulation;
    }
}
