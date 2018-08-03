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
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 该类实现了适应度函数作为间接平方误差的元素
 */
public class RMSEFitnessCalculator implements AbstractFitnessCalculator{

    @Autowired
    private AbstractForecastCalculator forecastCalculator;

    /**
     * 该方法计算给定染色体的适应度函数值(Root Mean Squared Error(RMSE))，它是来自中等平方误差的元素。
     *
     * @param timeSeries Szereg czasowy
     * @param window Okno czasowe
     * @param chromosome 要计算适应度函数值的染色体
     * @return 适应度函数的值(均方根RMSE)
     */
    public double calculateFitness(TimeSeries timeSeries, SlidingTimeWindow window, Chromosome chromosome){

        /*
        SSE -平方误差之和
         */
        double sse = 0;

        /*
        l - 预测数量
         */
        int l = 0;

        for(int i = timeSeries.getItemCount() - 1; i > 0; i--){
        	//预测值
            Double forecast = forecastCalculator.calculateForecast(timeSeries,
                    window,
                    chromosome,
                    i);

            if(forecast == null){
                continue;
            }
            l++;

            double realVal = timeSeries.getValue(i).doubleValue();

            double e = realVal - forecast;//e(t)=x(t)-x(t) 预测误差由实际值与预测值之间的差异给出

            sse += Math.pow(e, 2);
        }
        
//        System.out.println("sse:"+sse);
//        System.out.println("l:"+l);
        return Math.sqrt(sse / l);
    }
}
