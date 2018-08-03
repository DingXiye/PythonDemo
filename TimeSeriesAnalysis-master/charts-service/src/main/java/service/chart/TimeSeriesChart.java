/**
 * Copyright (c) 2013
 * Tomasz Choma, Olgierd Grodzki, Ĺ�ukasz PotÄ™pa, Monika Rakoczy, PaweĹ‚ Synowiec, Ĺ�ukasz Szarkowicz
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

import java.awt.Color;
import java.awt.Paint;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

/**
 * 时间序列图表
 */
public class TimeSeriesChart extends JPanel {

    private static final Color centroidColor = Color.blue;
    private int firstColumnID;
    private int lastColumnID;

    private JTable jtable;
    private JPanel jp=new JPanel();
    
    JTabbedPane tabbedPane=new JTabbedPane();
    
    
    private XYLineAndShapeRenderer renderer;//画线条的渲染器

    /**
     *自定义类后渲染继承图表  XYLineAndShapeRenderer 
     */
    private class MyRenderer extends XYLineAndShapeRenderer {
    	
        public MyRenderer(boolean lines, boolean shapes) {
            super(lines, shapes);
        }
        
        /**
         * 检查系列中的元素是否来自预测并设置适当的颜色
         * 
         * @param row 元素行
         * @param col 项目编号
         */
        @Override
        public Paint getItemPaint(int row, int col) {
            if (col >= firstColumnID && col <= lastColumnID) {
                return centroidColor;
            } else {
                return super.getItemPaint(row, col);
            }
        }
    }
    
    public TimeSeriesChart(){
    	createChartPanel(new ArrayList<TimeSeries>(), 0);
    	tabbedPane.addTab("Data table", null, jp, null);
    	String[] columnNames = {"Date","Value"};
    	jp.setLayout(null);
		jtable = new JTable(new DefaultTableModel(columnNames,1));
		
    }
    
    public TimeSeriesChart(List<TimeSeries> timeSeriesList){
    	createChartPanel(timeSeriesList, 0);
    }

    public TimeSeriesChart(List<TimeSeries> timeSeriesList, int numOfDataPoints){
    	createChartPanel(timeSeriesList, numOfDataPoints);
    }
    
    /**
     * 创建图表画布
     * 
     * @param timeSeriesList 时间序列列表
     * @param numOfDataPoints 进行预测的时间点数
     */
    public void createChartPanel(List<TimeSeries> timeSeriesList, int numOfDataPoints){
        XYDataset dataset = createDataset(timeSeriesList);

        System.out.println("进行预测的时间点数："+numOfDataPoints);
        System.out.println("时间点的数量:"+timeSeriesList.size());
        if(numOfDataPoints > 0){
        	firstColumnID = dataset.getItemCount(0) - numOfDataPoints;
        	System.out.println("预测起始点:"+firstColumnID);
        	lastColumnID = dataset.getItemCount(0) -1;
        	System.out.println("预测结束点:"+lastColumnID);
            renderer = new MyRenderer(true, false); 
//            renderer = new XYLineAndShapeRenderer(true, false);
        }else{
            renderer = new XYLineAndShapeRenderer(true, false);
        }

        JFreeChart chart = createChart(dataset);

        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);

        this.removeAll();
        this.add(panel);
    }
    
    /**
     * 创建图表
     * 
     * @param dataset 数据集
     * @return 时间序列图
     */
    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Time series", 		 // title
                "Date",             // x-axis label
                "Value",   			// y-axis label
                dataset,            // data
                true,               // create legend?
                true,               // generate tooltips?
                false               // generate URLs?
        );

        chart.setBackgroundPaint(Color.white);

        XYPlot plot = chart.getXYPlot();
        //plot.
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.setRenderer(renderer);

        
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(false);//是否将对应的折线点用正方形表示
            renderer.setBaseShapesFilled(false);//是否填充上面的正方形
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM-yyyy"));
        //axis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH, 1));
        return chart;
    }
    
    /**
     * 根据时间序列列表创建一个数据集
     * 
     * @param timeSeriesList 时间序列列表
     * @return 数据集
     */
    private XYDataset createDataset(List<TimeSeries> timeSeriesList) {

        TimeSeriesCollection dataset = new TimeSeriesCollection();

        for(TimeSeries timeSeries : timeSeriesList){
            dataset.addSeries(timeSeries);
        }

        return dataset;
    }
    
    /**
     *清洁面板
     */
    public void clear(){
    	createChartPanel(new ArrayList<TimeSeries>(), 0);
    }
}
