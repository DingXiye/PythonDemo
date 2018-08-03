/**
 * Copyright (c) 2013
 * Tomasz Choma, Olgierd Grodzki, 艁ukasz Pot臋pa, Monika Rakoczy, Pawe艂 Synowiec, 艁ukasz Szarkowicz
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

package action;

import forecasting.AbstractForecast;
import forecasting.config.ForecastConfig;
import forecasting.config.ForecastMethod;
import forecasting.config.GASettings;
import forecasting.config.SelectionMethod;
import forecasting.model.SlidingTimeWindow;
import gui.TSAFrame;

import org.bouncycastle.crypto.DataLengthException;
import org.jfree.data.time.TimeSeries;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import service.action.GAChartObserver;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

/**
 * Run 
 * Klasa implementuje interfejs ActionListener
 */
public class ShowTimeSeriesWithForecastAction implements ActionListener {

    TSAFrame window;

    public ShowTimeSeriesWithForecastAction(TSAFrame window){
        this.window = window;
    }
    
    /**
	 * 一种事件处理方法，负责遗传算法的初始化
	 */
    public void actionPerformed(ActionEvent e){
    	
    	try{
    		
    		if (window.getSliderSelekcji().getValue() + window.getSliderKrzyzowania().getValue() + window.getSliderMutacji().getValue() != 100)
    			throw new ParseException("Please insert correct data for Selection, Crossing and Mutation. The sum of the three has to equal 100%", 0);
    		
	    	TimeSeries timeSeries = window.getCurrentTimeSeries();
	    	if (timeSeries==null || timeSeries.isEmpty()) throw new DataLengthException();
	        
	    	SlidingTimeWindow slidingTimeWindow = new SlidingTimeWindow(this.parseToWindowForm(window.getTimeWindowField().getText()));
	
	        if(window.getRdBtnStochastic().isSelected())
	        	GASettings.getInstance().setSelectionMethod(SelectionMethod.STOCHASTIC_UNIVERSAL_SAMPLING_SELECTION);
	        
	        if(window.getRdbtnArmaForecast().isSelected())
	        	GASettings.getInstance().setForecastMethod(ForecastMethod.ARMA_FORECAST);
	
	        GASettings.getInstance().setConcurrent(true);
	
	        ApplicationContext context = new AnnotationConfigApplicationContext(ForecastConfig.class);
	        AbstractForecast forecast = (AbstractForecast) context.getBean("forecast");
	
	        forecast.initializeGeneticAlgorithm(
	    		(TimeSeries) timeSeries.clone(),				
	            (Integer) window.getPopulSizeField().getValue(),
	            slidingTimeWindow,				               
	            (Integer) window.getIterNumberField().getValue(),
	            (double)window.getSliderProbOfCross().getValue()/100,
	            (double)window.getSliderProbOfMutat().getValue()/100,
	            (double)window.getSliderSelekcji().getValue()/100,
	            (double)window.getSliderKrzyzowania().getValue()/100,
	            (double)window.getSliderMutacji().getValue()/100);
	        forecast.initializeForecast((Integer) window.getPeriodOfPredField().getValue());
	                
	        forecast.addObserver(new GAChartObserver(window.getFitnessChart(), window.getTimeSeriesChartWithForecast(), (Integer) window.getPeriodOfPredField().getValue()));
	        forecast.addObserver(new GAStatisticObserver(window.getForecast(), (Integer) window.getPeriodOfPredField().getValue()));
	        forecast.execute();
	        window.getTabbedPane().setSelectedIndex(3);
	        
        } 
    	catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }
    	catch (DataLengthException de){
    		JOptionPane.showMessageDialog(window, "Current data is not set or empty", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    	catch (ParseException pe){
    		JOptionPane.showMessageDialog(window, pe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	}
    	catch(Exception exc){
    		JOptionPane.showMessageDialog(window, "Wrong data", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    /**
     * 从作为一系列字符输入的数字创建整数表（大于0），用分隔符分隔
     * 
     * @param str 字符串
     * @return 一个大于0的整数数组
     * @throws NumberFormatException 
     */
    public int[] parseToWindowForm(String str) throws NumberFormatException{
    	str=str.replaceAll(" ", "");
    	str=str.replaceAll("\t", "");
    	str=str.replaceAll("\n", "");
    	String[] values=str.split(",");
    	int[] parsed=new int[values.length];
    	for (int i=0;i<parsed.length;i++){
    		parsed[i]=Integer.valueOf(values[i]);
    		if (parsed[i]<=0) throw new NumberFormatException();
    	}
    	return parsed;
    }
    
}
