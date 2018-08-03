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

/**
 * 该类实现基于ARMA的预测计算
 */
public class ARMAForecastCalculator implements AbstractForecastCalculator {

    /**
     *该方法计算给定时间点的近似值作为总和:
     * <ul>
     * 染色体基因的前半部分的线性组合和使用STW的先前值以及染色体基因的后半部分的线性组合和先前值的预测误差
     *  <li>kombinacji liniowej pierwszej połowy genow chromosomu i poprzednich wartosci pobranych korzystajac z STW oraz</li>
     *  <li>kombinacji liniowej drugiej połowy genów chromosomu i błędów predykcji poprzednich wartości </li>
     * </ul>
     * @param timeSeries 执行近似的时间序列
     * @param window 预测时间窗口
     * @param chromosome 要计算预测的染色体
     * @param currentIndex 计算预测的时间点索引
     * @return 如果您无法匹配STW，则预测或为null
     */
    @Override
    public Double calculateForecast(TimeSeries timeSeries,
                                    SlidingTimeWindow window,
                                    Chromosome chromosome,
                                    int currentIndex) {

        double returnValue = chromosome.getGene(0);

        for(int i = 0; i < window.getLength(); i++){

            int pastIndex = currentIndex - window.getValueAt(i);

            if(pastIndex < 0){
                return null;
            }

            Double pastForecast = new LinearCombinationForecastCalculator().calculateForecast(timeSeries,
                    window,
                    chromosome,
                    pastIndex);

            returnValue += chromosome.getGene(i + 1) *
                    timeSeries.getValue(pastIndex).doubleValue();

            if(pastForecast != null){
                returnValue += chromosome.getGene(i + 1 + window.getLength()) *
                    (timeSeries.getValue(pastIndex).doubleValue() - pastForecast);
            }
        }

        return returnValue;
    }
}
