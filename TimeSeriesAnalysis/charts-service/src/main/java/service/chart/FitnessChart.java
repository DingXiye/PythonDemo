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

package service.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;

import java.awt.*;

/**
 * 适应值的图表
 */
public class FitnessChart extends JPanel {
  
    private DefaultCategoryDataset dataset;
    private ChartPanel chartPanel;
    
    public FitnessChart() {
        
        dataset = new DefaultCategoryDataset ();
        
        JFreeChart chart = createChart(dataset);
        
        chartPanel = new ChartPanel(chart);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);
        
        this.add(chartPanel);
    }
    
    /**
     * 适应值
     * 
     * @param categoryDataset 所要展示图表的适应值数据集
     * @return fitness 功能图
     */
    private static JFreeChart createChart(CategoryDataset categoryDataset) {

        JFreeChart chart = ChartFactory.createLineChart(
            "Best fitness function value",  			// title
            "Iteration",         // x-axis label
            "Fitness",   		// y-axis label
            categoryDataset,    // data
            PlotOrientation.VERTICAL,
            true,               // create legend?
            true,               // generate tooltips?
            true               // generate URLs?
        );

        chart.setBackgroundPaint(Color.white);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        CategoryItemRenderer r = plot.getRenderer();
        if (r instanceof LineAndShapeRenderer) {
            LineAndShapeRenderer renderer = (LineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(false);
            renderer.setBaseShapesFilled(false);
        }

        return chart;
    }
    
    /**
     * 向数据集添加新的适应度函数值
     * 
     * @param val 在给定迭代算法中最佳个体的适应度函数的值
     * @param series 数据系列的名称
     * @param ix 迭代次数
     */
    public void addValue(double val, String series, int ix){
    	System.out.println("series:"+series+";value:"+val+";ix:"+ix);
    	dataset.addValue(val, series, Integer.toString(ix));
    }
    
    /**
     * 刷新并重绘面板
     */
    public void ripejnt(){
    	chartPanel.setRefreshBuffer(true);
    	chartPanel.repaint();
    }
    
    /**
     * 清洁面板
     */
    public void clear(){
    	dataset.clear();
    	this.ripejnt();
    }
}
