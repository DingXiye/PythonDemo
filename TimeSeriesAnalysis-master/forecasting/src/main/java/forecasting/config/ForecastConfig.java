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

import forecasting.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 作为Spring配置的类（Spring JavaConfig）
 */
@Configuration
public class ForecastConfig {

    @Bean
    public AbstractGeneticAlgorithmOperation selection() {
        GASettings settings = GASettings.getInstance();
        if(settings.getSelectionMethod() == SelectionMethod.ROULETTE_WHEEL_SELECTION){
            if(settings.isConcurrent()){
                return new ConcurrentRouletteWheelSelection();
            }else{
                return new RouletteWheelSelection();
            }
        }
        if(settings.getSelectionMethod() == SelectionMethod.STOCHASTIC_UNIVERSAL_SAMPLING_SELECTION){
            if(settings.isConcurrent()){
                return new ConcurrentStochasticUniversalSamplingSelection();
            }else{
                return new StochasticUniversalSamplingSelection();
            }
        }
        return null;
    }

    @Bean
    public AbstractGeneticAlgorithmOperation crossover(){
        GASettings settings = GASettings.getInstance();
        if(settings.isConcurrent()){
            return new ConcurrentArithmeticalCrossover();
        }else{
            return new ArithmeticalCrossover();
        }
    }

    @Bean
    public AbstractGeneticAlgorithmOperation mutation(){
        GASettings settings = GASettings.getInstance();
        if(settings.isConcurrent()){
            return new ConcurrentGaussianPerturbation();
        }else{
            return new GaussianPerturbation();
        }
    }

    @Bean
    public AbstractForecast forecast(){
        return new TimeSeriesForecast();
    }

    @Bean
    public AbstractFitnessCalculator fitnessCalculator(){
        return new RMSEFitnessCalculator();
    }

    @Bean
    public AbstractForecastCalculator forecastCalculator(){
        if(GASettings.getInstance().getForecastMethod() == ForecastMethod.LINEAR_COMBINATION_FORECAST){
            return new LinearCombinationForecastCalculator();
        }
        if(GASettings.getInstance().getForecastMethod() == ForecastMethod.ARMA_FORECAST){
            return new ARMAForecastCalculator();
        }
        return null;
    }
}
