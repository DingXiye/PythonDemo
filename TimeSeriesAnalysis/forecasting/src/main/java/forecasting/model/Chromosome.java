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

package forecasting.model;

/**
 * 染色体模型类
 */
public class Chromosome implements Comparable {

    private double[] genes;
    private double fitness;

    /**
     * 带参构造器
     *
     * @param genes Geny
     */
    public Chromosome(double[] genes){
        setGenes(genes);
    }

    /**
     * Pobierz geny
     *
     * @return Geny
     */
    public double[] getGenes(){
        return genes;
    }

    /**
     * Ustaw geny
     *
     * @param genes Geny
     */
    public void setGenes(double[] genes){
        this.genes = genes;
    }

    /**
     * 下载给定指数的基因
     *
     * @param index Indeks
     * @return Gen
     */
    public double getGene(int index){
        return genes[index];
    }

    /**
     * 添加到给定的基因
     *
     * @param index 基因索引
     * @param val 要添加的值
     */
    public void addToGene(int index, double val){
        genes[index] += val;
    }

    /**
     * Pobierz wartosc fukcji fitness
     *
     * @return Wartosc funkcji fitness
     */
    public double getFitness(){
        return fitness;
    }

    /**
     * Ustaw wartosc funkcji fitness
     *
     * @param fitness Wartosc funkcji fitness
     */
    public void setFitness(double fitness){
        this.fitness = fitness;
    }

    /**
     * 下载染色体的大小
     *
     * @return Rozmiar chromosomu
     */
    public int getSize(){
        return genes.length;
    }

    /**
     * 将对象与对象进行比较
     *
     * @param o Obiekt
     * @return 
     */
    @Override
    public int compareTo(Object o) {
        Chromosome chromosome = (Chromosome) o;
        if(this.fitness < chromosome.getFitness()){
            return -1;
        }
        if(this.fitness > chromosome.getFitness()){
            return 1;
        }
        return 0;
    }
}
