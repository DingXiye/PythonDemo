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
/**
 * 通过轮盘赌的方式获得新的染色体集合
 * @author dingye
 *
 */
public class ConcurrentRouletteWheelSelection implements AbstractGeneticAlgorithmOperation {

    /**
     *该方法没有实现，因为选择是遗传算法的操作的特殊情况，其概率未被定义
     *
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

        Arrays.sort(sortedPopulation);//根据fitness顺排
       // System.out.println("选择之前的染色体集合：");
        for (int sn=0;sn<population.length;sn++){
      //  	System.out.println(sn+" "+Arrays.toString(population[sn].getGenes()));
        }
        double[] rankingTab = new double[population.length];

        for(int i = 0; i < population.length; i++){//用最大的fitness-染色体集合中的每个fitness
            rankingTab[i] = sortedPopulation[population.length - 1].getFitness() -
                    population[i].getFitness();
        }

        double fitnessSum = 0;
        for (double fitness : rankingTab) {
            fitnessSum += fitness;
        }

        double probabilityTab[] = new double[population.length];

        for (int i = 0; i < probabilityTab.length; i++) {//计算每个fitness所在区域的概率
            probabilityTab[i] = rankingTab[i] / fitnessSum;
        }

        Chromosome[] tempPopulation = new Chromosome[population.length];

        Random rand = new Random();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < population.length; i++) {

            Runnable worker = new SelectionOperation(i,
                    rand,
                    population,
                    tempPopulation,
                    probabilityTab);

            executor.execute(worker);
        }

        executor.shutdown();

        try {
            executor.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return tempPopulation;
    }

    private class SelectionOperation extends Thread{

        private int i;
        private Random rand;
        private Chromosome[] population;
        private Chromosome[] tempPopulation;
        private double[] probabilityTab;

        private SelectionOperation(int i,
                                   Random rand,
                                   Chromosome[] population,
                                   Chromosome[] tempPopulation,
                                   double[] probabilityTab){
            this.i = i;
            this.rand = rand;
            this.population = population;
            this.tempPopulation = tempPopulation;
            this.probabilityTab = probabilityTab;
        }

        @Override
        public void run() {

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
    }

}
