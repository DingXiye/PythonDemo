/**
 * Copyright (c) 2013
 * Tomasz Choma, Olgierd Grodzki, Łukasz Potępa, Monika Rakoczy, Paweł Synowiec, Łukasz Szarkowicz
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package service.action;

import forecasting.GAObserver;

import org.jfree.data.time.TimeSeries;
import service.chart.FitnessChart;
import service.chart.TimeSeriesChart;
import java.util.ArrayList;
import java.util.List;

/**
 * 该类实现遗传算法的观察者以将变化应用于图表
 */
public class GAChartObserver implements GAObserver {

    private FitnessChart fitChart;
    private TimeSeriesChart tmChart;
    private int numOfDataPoints;
    
    public GAChartObserver(FitnessChart fitChart, TimeSeriesChart tmChart, int numOfDataPoints){
        this.fitChart = fitChart;
        this.tmChart = tmChart;
        this.numOfDataPoints = numOfDataPoints;
    }
    
    /**
     * 每次迭代的算法将这些数据“推”给观察者。 然后添加元素到数据集之后，刷新并重新绘制带有图形的面板。
     *向fitness chart中添加数据并展示
     * @param fitness 此迭代中最佳个体的适应度函数的值
     * @param best 这次迭代中最佳个体的基因
     * @param i 迭代次数
     */
    @Override
    public void update(double fitness, double[] best, int i) {

    	fitChart.setVisible(true);

        try{
        	fitChart.addValue(fitness,"Max", i);
        }catch(IllegalArgumentException ex){
            System.err.println("Some minor chart problem: "+ex.getMessage());
        }

        try{
        	fitChart.ripejnt();
        	fitChart.validate();

        }catch(IllegalArgumentException ex){
            System.err.println("Some minor chart problem: "+ex.getMessage());
        }
    }
    
    /**
     * 在遗传算法操作之后调用的方法，根据方法初始化/调用，传送包含预测与否的时间序列。 接下来，基于接收的时间序列创建图表。
     *
     * @param timeSeries 时间序列
     */
    @Override
    public void done(TimeSeries timeSeriesWithForecast){

        List<TimeSeries> timeSeriesWithForecastList = new ArrayList<>();
        timeSeriesWithForecastList.add(timeSeriesWithForecast);
        
        tmChart.createChartPanel(timeSeriesWithForecastList, this.numOfDataPoints);
        tmChart.validate();
    }
    
    /**
     * 该方法没有实现
     *
     * @param best Chromosom najlepszego osobnika jako tablica doubli
     */
    @Override
    public void done(double[] best) {
    }
}
