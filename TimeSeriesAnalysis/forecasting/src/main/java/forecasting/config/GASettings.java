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

package forecasting.config;

/**
 *包含遗传算法设置的单例，允许您选择选择方法和如何计算预测并定义是否使用遗传算法的多级版本 .
 */
public class GASettings {

    private static GASettings instance = new GASettings();

    private SelectionMethod selectionMethod = SelectionMethod.ROULETTE_WHEEL_SELECTION;
    private ForecastMethod forecastMethod = ForecastMethod.LINEAR_COMBINATION_FORECAST;

    private boolean concurrent = false;

    private GASettings(){
    }

    /**
     * 下载单例实例
     *
     * @return Singleton
     */
    public static GASettings getInstance(){
        return instance;
    }

    /**
     * 获取当前设置的预测计算方法
     *
     * @return 计算预测的方法
     */
    public ForecastMethod getForecastMethod() {
        return forecastMethod;
    }

    /**
     * 设置预测计算方法
     *
     * @param forecastMethod 计算预测的方法
     */
    public void setForecastMethod(ForecastMethod forecastMethod) {
        this.forecastMethod = forecastMethod;
    }

    /**
     * 下载当前选择的选择方法
     *
     * @return 选择方法
     */
    public SelectionMethod getSelectionMethod() {
        return selectionMethod;
    }

    /**
     * 设置选择方法
     *
     * @param selectionMethod 
     */
    public void setSelectionMethod(SelectionMethod selectionMethod) {
        this.selectionMethod = selectionMethod;
    }

    /**
     * 下载多层设置
     *
     * @return 多层旗帜
     */
    public boolean isConcurrent() {
        return concurrent;
    }

    /**
     * 设置多层
     *
     * @param concurrent 多层旗帜
     */
    public void setConcurrent(boolean concurrent) {
        this.concurrent = concurrent;
    }

}
