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

import gui.TSAFrame;

import org.jfree.data.time.TimeSeries;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * Load data-in chart
 * Klasa implementuje interfejs ActionListener
 */
public class ShowTimeSeriesDataInAction implements ActionListener {

    TSAFrame window;

    public ShowTimeSeriesDataInAction(TSAFrame window){
        this.window = window;
    }
    
    /**
	 * 支持事件的方法，负责显示当前所选时间序列的图形（输入数据 - 无预测）
	 */
    public void actionPerformed(ActionEvent e){
    	
    	try{
    		List<TimeSeries> timeSeriesWithForecastList = new ArrayList<TimeSeries>();
    	
	        timeSeriesWithForecastList.add(window.getCurrentTimeSeries());
	        
	        window.getTimeSeriesChartDataIn().createChartPanel(timeSeriesWithForecastList, 0);
	        window.getTimeSeriesChartDataIn().validate();
	        window.getTabbedPane().setSelectedIndex(1);
        }
        catch (IllegalArgumentException iae){
    		JOptionPane.showMessageDialog(window, "Current data is not set or empty", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    }   
}
