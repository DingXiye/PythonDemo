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
import forecasting.model.SlidingTimeWindow;
import org.jfree.data.time.TimeSeries;

import javax.swing.*;

/**
 * 时间序列预测界面。
 */
public abstract class AbstractForecast extends SwingWorker<Chromosome, Double[]> {

    /**
     * 初始化预测，调用此方法意味着完成遗传算法后的类将开始预测，完成方法中的时间序列将包含预测。
     *
     * @param numOfDataPoints 要进行预测的时间点的数量
     */
    public abstract void initializeForecast(int numOfDataPoints);

    /**
     * 初始化遗传算法
     *
     * @param timeSeries 时间序列
     * @param populationSize 人口规模
     * @param slidingTimeWindow 时间窗口
     * @param numOfIterations 迭代次数
     * @param crossoverProbability 交叉的概率
     * @param mutationProbability 突变的可能性
     * @param percentOfKeptFromSelection 选择后留下的个人百分比
     * @param percentOfKeptFromCrossover 交叉后留下的个人百分比
     * @param percentOfKeptFromMutation 变异后留下的个人百分比
     */
    public abstract void initializeGeneticAlgorithm(TimeSeries timeSeries,
                                                    int populationSize,
                                                    SlidingTimeWindow slidingTimeWindow,
                                                    int numOfIterations,
                                                    double crossoverProbability,
                                                    double mutationProbability,
                                                    double percentOfKeptFromSelection,
                                                    double percentOfKeptFromCrossover,
                                                    double percentOfKeptFromMutation);

    /**
     *添加一个观察者
     *
     * @param o Obserwator
     */
    public abstract void addObserver(GAObserver o);

    /**
     * 计算预测
     *
     * @param timeSeries 要进行预测的时间序列
     * @param slidingTimeWindow 时间窗口
     * @param numOfDataPoints 要进行预测的时间点的数量
     * @param genes 要在预测中使用的解决方案
     * @return 预测增加了时间序列
     */
    public abstract TimeSeries doForecast(TimeSeries timeSeries,
                                          SlidingTimeWindow slidingTimeWindow,
                                          int numOfDataPoints,
                                          double[] genes);
}
