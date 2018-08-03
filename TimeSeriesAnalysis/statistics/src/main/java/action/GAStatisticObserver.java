/**
 * Copyright (c) 2013, Tomasz Choma, Olgierd Grodzki, Łukasz Potępa, Monika
 * Rakoczy, Paweł Synowiec, Łukasz Szarkowicz
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package action;

import forecasting.GAObserver;

import org.jfree.data.time.TimeSeries;

/**
 * 该类实现了遗传算法的观察者，以便在算法结束后获得与预测一起的时间序列
 */
public class GAStatisticObserver implements GAObserver {

	private TimeSeries forecast;
	private int numOfDataPoints;
    
    public GAStatisticObserver(TimeSeries forecast, int numOfDataPoints){
    	this.forecast = forecast;
        this.numOfDataPoints = numOfDataPoints;
    }
    
    /**
     * 
     *
     * @param fitness Wartosc funkcji fitness najlepszego osobnika w tej iteracji
     * @param best Geny najlepszego osobnika w tej iteracji
     * @param i Numer iteracji
     */
    @Override
    public void update(double fitness, double[] best, int i) {
    }
    
    /**
     * 在遗传算法操作之后调用的方法，根据方法初始化/调用，传送包含预测与否的时间序列。 接下来，它将输入数据与预测数据分开。
     *
     * @param timeSeries Szereg czasowy
     */
    @Override
    public void done(TimeSeries timeSeriesWithForecast){
    	int number = timeSeriesWithForecast.getItemCount() - 1;
		try {
			forecast.clear();
			TimeSeries tmp=timeSeriesWithForecast.createCopy(number - numOfDataPoints, number);
			for (int i=0;i<tmp.getItemCount();i++){
				forecast.add(tmp.getDataItem(i));
			}
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Metoda nie posiada implementacji
     *
     * @param best Chromosom najlepszego osobnika jako tablica doubli
     */
    @Override
    public void done(double[] best) {
    }
}
