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

package gui;

import data.CSVDataAcquisitor;
import data.SwingTableDataAcquisitor;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import javax.swing.ButtonGroup;

import org.jfree.data.time.TimeSeries;

import action.ResetChartsAndParameters;
import action.ShowTimeSeriesDataInAction;
import action.ShowTimeSeriesWithForecastAction;
import service.action.GAChartObserver;
import service.chart.FitnessChart;
import service.chart.TimeSeriesChart;
import statistics.Statistics;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.NumberFormat;
import java.util.LinkedList;

import javax.swing.JComboBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.JFormattedTextField;

public class TSAFrame extends JFrame {
		
	//----------------------------Genetic----------------------------
	
	/**
	 * 时间序列列表
	 */
	LinkedList<TimeSeries> timeSeries;
	
	/**
	 *当前时间序列
	 */
	TimeSeries currentTimeSeries; 
	
	/**
	 * 显示带预测的时间序列的图表
	 */
	private FitnessChart fitnessChart; 
	
	/**
	 * 显示时间序列的图表
	 */
	private TimeSeriesChart timeSeriesChartWithForecast;
	
	/**
	 * wykres przedstawiający wejściowy szereg czasowy
	 */
	private TimeSeriesChart timeSeriesChartDataIn;
	
	/**
	 * Tabela zawierająca dane szeregów czasowych
	 */
	JTable dataTable;
	
	//-----------------------------ImportantFrame---------------------------

	/**
	 * panel zakładki Data Table
	 */
	JPanel dataTablePanel;
	/**
	 * panel przewijania tabeli
	 */
	JScrollPane tableScrollPane;
	/**
	 * 包含日期格式的文本字段
	 */
	JTextField dateFormatTextField;
	/**
	 * 
	 */
	JComboBox<String> dataComboBox;
	/**
	 * 包含时间序列值的列
	 */
	JTextField valueColumnTextField;
	/**
	 * 包含时间序列的时间值的列
	 */
	JTextField timeColumnTextField;
	
	JComboBox<String> statDataChooserCB;
	JPanel statisticsPanel;
	//-----------------------------Frame-------------------------------------
	private JFormattedTextField populSizeField;
	private JFormattedTextField iterNumberField;
	private JFormattedTextField periodOfPredField;
	private JTextField timeWindowField;
	private JLabel timeSeriesField;
	
	/**
	 * 滑块用于输入一个参数，表示选择后剩下的个体百分比
	 */
	private JSlider sliderSelekcji;
	/**
	 * 滑块输入一个参数，表示交叉后留下的个体百分比
	 */
	private JSlider sliderKrzyzowania;
	/**
	 * 滑块输入一个参数，表示变异后留下的个体百分比
	 */
	private JSlider sliderMutacji;
	/**
	 * 滑块用于输入表示突变概率的参数
	 */
	private JSlider sliderProbOfMutat;
	/**
	 * 滑块输入指示交叉概率的参数
	 */
	private JSlider sliderProbOfCross;
	
	/**
	 * 一组选择按钮，用于选择选择方法
	 */
	private ButtonGroup radioBtnGroup;
	/**
	 * 一组用于设置预测计数方法的选择按钮
	 */
	private ButtonGroup radioBtnGroup2;
	/**
	 *轮盘赌方法的复选框 
	 */
	private JRadioButton rdBtnRoulette;
	/**
	 * 选中随机方法的复选框
	 */
	private JRadioButton rdBtnStochastic;
	/**
	 * 复选框为“线性组合”方法
	 */
	private JRadioButton rdbtnLinearCombination;
	/**
	 * 复选框以显示Arma Forecast方法
	 */
	private JRadioButton rdbtnArmaForecast;
	
	private JTabbedPane tabbedPane;
	private JLabel selectionValueLabel;
	private JLabel crossingValueLabel;
	private JLabel mutationValueLabel;
	private JLabel probabilityMutationValueLabel;
	private JLabel probabilityCrossingValueLabel;

	
	private Statistics stat;
	private TimeSeries forecast;
	private JLabel lblCorrelationCoefficient;
    private JLabel lblLowerBound;
	private JLabel lblUpperBound;
	private JLabel lblMeanSeries1;
	private JLabel lblMeanSeries2;
	private JLabel lblVarianceSeries1;
	private JLabel lblVarianceSeries2;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {			        
				    UIManager.setLookAndFeel(
				        UIManager.getSystemLookAndFeelClassName());
				}catch (Exception e){}
				
				try {
					TSAFrame frame = new TSAFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TSAFrame() {
		
		//--------------------------------MainFieldsInit-------------------------
		
		this.timeSeries=new LinkedList<TimeSeries>();
		this.currentTimeSeries=null;
		this.stat = new Statistics();
		this.forecast=new TimeSeries("Forecast");
		
		//-----------------------------------------------------------------------
		
		setTitle("Time Series Analisys");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870, 550);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		/**
		 *一个按钮，显示当前所选时间序列的图表
		 */
		JButton btnCustomValues = new JButton("Load data-in chart");
		btnCustomValues.addActionListener(new ShowTimeSeriesDataInAction(this));
		menuBar.add(btnCustomValues);
		
		/**
		 * Przycisk służący resetowaniu parametrów
		 */
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ResetChartsAndParameters(this));
		menuBar.add(btnReset);
		
		/**
		 * 开始计算的按钮
		 */
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ShowTimeSeriesWithForecastAction(this));
		menuBar.add(btnRun);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		NumberFormat format2 = NumberFormat.getInstance();
		NumberFormatter formatter2 = new NumberFormatter(format2);
		NumberFormat format3 = NumberFormat.getInstance();
		NumberFormatter formatter3 = new NumberFormatter(format3);
		formatter.setValueClass(Integer.class);
		formatter2.setValueClass(Integer.class);
		formatter3.setValueClass(Integer.class);
		
		radioBtnGroup = new ButtonGroup();
		radioBtnGroup2 = new ButtonGroup();
		
		//-------------------DataTable-------------------
		
		dataTablePanel = new JPanel();
		tabbedPane.addTab("Data table", null, dataTablePanel, null);
		
		String[] columnNames = {"Date","Value"};
		//Object[][] data = {{"", new Double(0)}};
		dataTablePanel.setLayout(null);
		dataTable = new JTable(new DefaultTableModel(columnNames,1));
		
		tableScrollPane = new JScrollPane(dataTable);
		tableScrollPane.setBounds(198, 5, 452, 427);
		dataTable.setFillsViewportHeight(true);
		
		dataTablePanel.add(tableScrollPane);
		/**
		 * Przycisk dodaje nowy wiersz w tabeli danych
		 */
		JButton btnAddDataRow = new JButton("Add data");
		btnAddDataRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model=(DefaultTableModel)dataTable.getModel();
				model.addRow(new Object[]{"", ""});
			}
		});
		btnAddDataRow.setBounds(10, 8, 113, 23);
		dataTablePanel.add(btnAddDataRow);
		/**
		 * 该按钮用于保存当前时间序列
		 */
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO saving current state
				try{
					if (currentTimeSeries!=null){
						String df=dateFormatTextField.getText();
						SwingTableDataAcquisitor tableAcq=new SwingTableDataAcquisitor(dataTable,df);
						int currentIndex=timeSeries.indexOf(currentTimeSeries);
						currentTimeSeries=tableAcq.readData_TimeSeries((String)currentTimeSeries.getKey());
						timeSeries.remove(currentIndex);
						timeSeries.add(currentIndex, currentTimeSeries);
						dataComboBox.firePopupMenuWillBecomeVisible();
						dataComboBox.setSelectedIndex(timeSeries.indexOf(currentTimeSeries));
						timeSeriesField.setText((String)currentTimeSeries.getKey());
					}
					else{
						String df=dateFormatTextField.getText();
						String name=JOptionPane.showInputDialog(dataTablePanel,"Choose name for data");
						if (name!=null){
							SwingTableDataAcquisitor tableAcq=new SwingTableDataAcquisitor(dataTable,df);
							currentTimeSeries=tableAcq.readData_TimeSeries(name);
							timeSeries.add(currentTimeSeries);
							dataComboBox.firePopupMenuWillBecomeVisible();
							dataComboBox.setSelectedIndex(timeSeries.indexOf(currentTimeSeries));
							timeSeriesField.setText((String)currentTimeSeries.getKey());
						}
					}
				}
				catch(Exception exc){
					exc.printStackTrace();
					JOptionPane.showMessageDialog(dataTablePanel,"Table data parse error","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSave.setBounds(10, 76, 113, 23);
		dataTablePanel.add(btnSave);
		
		JButton btnDeleteDataRow = new JButton("Delete data");
		btnDeleteDataRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model=(DefaultTableModel)dataTable.getModel();
				for (@SuppressWarnings("unused") int i : dataTable.getSelectedRows()){
					model.removeRow(dataTable.getSelectedRow());
				}
			}
		});
		btnDeleteDataRow.setBounds(10, 42, 113, 23);
		dataTablePanel.add(btnDeleteDataRow);
		
		dataComboBox = new JComboBox<String>();
		dataComboBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				try{
					if (!timeSeries.isEmpty()){
						currentTimeSeries=timeSeries.get(dataComboBox.getSelectedIndex());
						SwingTableDataAcquisitor.updateJTable(dataTable,currentTimeSeries,dateFormatTextField.getText());
						timeSeriesField.setText((String)currentTimeSeries.getKey());
					}
				}
				catch(Exception exc){
					exc.printStackTrace();
				}
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				dataComboBox.removeAllItems();
				if (!timeSeries.isEmpty()){
					for (int i=0;i<timeSeries.size();i++){
						dataComboBox.addItem((String)timeSeries.get(i).getKey());
					}
				}
			}
		});
		dataComboBox.setBounds(660, 9, 179, 20);
		dataTablePanel.add(dataComboBox);
		
		JButton btnSaveAs = new JButton("Save as...");
		btnSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String df=dateFormatTextField.getText();
					String name=JOptionPane.showInputDialog(dataTablePanel,"Choose name for data");
					if (name!=null){
						SwingTableDataAcquisitor tableAcq=new SwingTableDataAcquisitor(dataTable,df);
						timeSeries.add(tableAcq.readData_TimeSeries(name));
						currentTimeSeries=timeSeries.getLast();
						dataComboBox.firePopupMenuWillBecomeVisible();
						dataComboBox.setSelectedIndex(timeSeries.indexOf(currentTimeSeries));
						timeSeriesField.setText((String)currentTimeSeries.getKey());
					}
				}
				catch(Exception exc){
					exc.printStackTrace();
					JOptionPane.showMessageDialog(dataTablePanel,"Table data parse error","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSaveAs.setBounds(10, 110, 113, 23);
		dataTablePanel.add(btnSaveAs);
		
		JButton btnImport = new JButton("Import");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser importFileChooser=new JFileChooser();
				int returnVal = importFileChooser.showOpenDialog(TSAFrame.this);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	try{
			            File file = importFileChooser.getSelectedFile();
			            CSVDataAcquisitor csvDataAcquisitor=new CSVDataAcquisitor(file.getAbsolutePath(),Integer.parseInt(timeColumnTextField.getText()),Integer.parseInt(valueColumnTextField.getText()),dateFormatTextField.getText()); //TODO wybranie column
			            timeSeries.add(csvDataAcquisitor.readData_TimeSeries());
			            currentTimeSeries=timeSeries.getLast();
			            dataComboBox.firePopupMenuWillBecomeVisible();
						dataComboBox.setSelectedIndex(timeSeries.indexOf(currentTimeSeries));
						SwingTableDataAcquisitor.updateJTable(dataTable, currentTimeSeries, dateFormatTextField.getText());
						timeSeriesField.setText((String)currentTimeSeries.getKey());
		        	}
		        	catch(Exception exc){
		        		exc.printStackTrace();
		        		JOptionPane.showMessageDialog(dataTablePanel,"File parsing error","Error",JOptionPane.ERROR_MESSAGE);
		        	}
		        } 
			}
		});
		btnImport.setBounds(10, 175, 113, 23);
		dataTablePanel.add(btnImport);
		
		dateFormatTextField = new JTextField();
		dateFormatTextField.setText("yyyy-MM-dd");
		dateFormatTextField.setBounds(743, 45, 96, 20);
		dataTablePanel.add(dateFormatTextField);
		dateFormatTextField.setColumns(10);
		
		JLabel dateFormatLabel = new JLabel("Date format:");
		dateFormatLabel.setBounds(660, 48, 86, 14);
		dataTablePanel.add(dateFormatLabel);
		
		valueColumnTextField = new JTextField();
		valueColumnTextField.setText("1");
		valueColumnTextField.setBounds(62, 231, 42, 20);
		dataTablePanel.add(valueColumnTextField);
		valueColumnTextField.setColumns(10);
		
		timeColumnTextField = new JTextField();
		timeColumnTextField.setText("0");
		timeColumnTextField.setBounds(10, 231, 42, 20);
		dataTablePanel.add(timeColumnTextField);
		timeColumnTextField.setColumns(10);
		
		JLabel lblImportColumns = new JLabel("Import columns (date-value):");
		lblImportColumns.setBounds(10, 209, 178, 14);
		dataTablePanel.add(lblImportColumns);
		
		//-------------------Charts----------------------
		timeSeriesChartDataIn = new TimeSeriesChart();
		tabbedPane.addTab("Time Series Chart - Data-in", null, timeSeriesChartDataIn, null);
        
		timeSeriesChartWithForecast = new TimeSeriesChart();
        tabbedPane.addTab("Time Series Chart with Forecast", null, timeSeriesChartWithForecast, null);
        
        fitnessChart = new FitnessChart();
        tabbedPane.addTab("Fitness Chart", null, fitnessChart, null);

        // ============ STATYSTYKI =================
        statisticsPanel = new JPanel();
        tabbedPane.addTab("Statistics", null, statisticsPanel, null);
        statisticsPanel.setLayout(null);
        
//        double[] results = stat.findBestCoefficient();
        JButton statisticRun = new JButton("Show statistics");
        statisticRun.setBounds(25, 10, 137, 24);
        statisticsPanel.add(statisticRun);

        JLabel correlationCoefficientLabel = new JLabel("Correlation of coeffivient: ");
        correlationCoefficientLabel.setBounds(25, 45, 174, 16);
        JLabel lowerLimitLabel = new JLabel("Lower boundary: ");
        lowerLimitLabel .setBounds(28, 73, 114, 16);
        JLabel upperLimitLabel = new JLabel("Upper boundary: ");
        upperLimitLabel.setBounds(28, 101, 114, 16);
        JLabel mean1Label = new JLabel("Mean of series 1: ");
        mean1Label.setBounds(28, 129, 114, 16);
        JLabel mean2Label = new JLabel("Mean of series 2: ");
        mean2Label.setBounds(28, 157, 114, 16);
        JLabel var1Label = new JLabel("Variance of series 1: ");
        var1Label.setBounds(28, 185, 134, 16);
        JLabel var2Label = new JLabel("Variance of series 2: ");
        var2Label.setBounds(28, 213, 134, 16);
//
        statisticsPanel.add(correlationCoefficientLabel);
        statisticsPanel.add(lowerLimitLabel);
        statisticsPanel.add(upperLimitLabel);
        statisticsPanel.add(mean1Label);
        statisticsPanel.add(mean2Label);
        statisticsPanel.add(var1Label);
        statisticsPanel.add(var2Label);
        
        lblCorrelationCoefficient = new JLabel("0");
        lblCorrelationCoefficient.setBounds(195, 45, 61, 16);
        statisticsPanel.add(lblCorrelationCoefficient);

        lblLowerBound = new JLabel("0");
        lblLowerBound.setBounds(154, 73, 61, 16);
        statisticsPanel.add(lblLowerBound);
        
        lblUpperBound = new JLabel("0");
        lblUpperBound.setBounds(154, 101, 61, 16);
        statisticsPanel.add(lblUpperBound);
        
        lblMeanSeries1 = new JLabel("0");
        lblMeanSeries1.setBounds(154, 129, 61, 16);
        statisticsPanel.add(lblMeanSeries1);
        
        lblMeanSeries2 = new JLabel("0");
        lblMeanSeries2.setBounds(154, 157, 61, 16);
        statisticsPanel.add(lblMeanSeries2);
        
        lblVarianceSeries1 = new JLabel("0");
        lblVarianceSeries1.setBounds(174, 185, 61, 16);
        statisticsPanel.add(lblVarianceSeries1);
        
        lblVarianceSeries2 = new JLabel("0");
        lblVarianceSeries2.setBounds(174, 213, 61, 16);
        statisticsPanel.add(lblVarianceSeries2);
        
        statDataChooserCB = new JComboBox<String>();
        statDataChooserCB.addPopupMenuListener(new PopupMenuListener() {
        	public void popupMenuCanceled(PopupMenuEvent e) {
        	}
        	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        	}
        	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        		statDataChooserCB.removeAllItems();
				if (!timeSeries.isEmpty()){
					for (int i=0;i<timeSeries.size();i++){
						statDataChooserCB.addItem((String)timeSeries.get(i).getKey());
					}
				}
        	}
        });
        statDataChooserCB.setBounds(532, 44, 157, 20);
        statisticsPanel.add(statDataChooserCB);
        
        JLabel lblChooseDataTo = new JLabel("Data chosen to compare:");
        lblChooseDataTo.setBounds(364, 46, 174, 14);
        statisticsPanel.add(lblChooseDataTo);
        
        statisticRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try{
	            	int index=statDataChooserCB.getSelectedIndex();
	            	TimeSeries compare=timeSeries.get(index);
	            	if (forecast.getItemCount() <8 || compare.getItemCount()<8) throw new Exception();
	            	stat.loadTimeSeries(forecast,compare);
	                double[] results = stat.findBestCoefficient();
	                lblCorrelationCoefficient.setText(String.valueOf(results[0]));
	                lblLowerBound.setText(String.valueOf(results[1]));
	                lblUpperBound.setText(String.valueOf(results[2]));
	                lblMeanSeries1.setText(String.valueOf(stat.getMeanOfSeries1()));
	                lblMeanSeries2.setText(String.valueOf(stat.getMeanOfSeries2()));
	                lblVarianceSeries1.setText(String.valueOf(stat.getVarOfSeries1()));
	                lblVarianceSeries2.setText(String.valueOf(stat.getVarOfSeries2()));
            	}
            	catch (Exception exc){
            		JOptionPane.showMessageDialog(statisticsPanel, "Too little data", "Error", JOptionPane.ERROR_MESSAGE);
            	}
            }
        });
        
        
        
        JPanel panel = new JPanel();
        tabbedPane.addTab("Parameters", null, panel, null);
        panel.setLayout(null);
        
        JLabel parametr1 = new JLabel("Population size");
        parametr1.setBounds(25, 81, 134, 16);
        panel.add(parametr1);
        
        JLabel parametr2 = new JLabel("Iteration number");
        parametr2.setBounds(25, 109, 134, 16);
        panel.add(parametr2);
        
        JLabel parametr3 = new JLabel("Probability of mutation");
        parametr3.setBounds(415, 156, 170, 16);
        panel.add(parametr3);
        
        //populSizeField的格式约束
        formatter.setMinimum(10);
		formatter.setMaximum(1000);
		formatter.setCommitsOnValidEdit(true);
        populSizeField = new JFormattedTextField(formatter);
        populSizeField.setToolTipText("(10-1000)");
        populSizeField.setText("100");
        populSizeField.setBounds(176, 75, 180, 28);
        panel.add(populSizeField);
        populSizeField.setColumns(10);
        
        //iterNumberField的格式化约束
		formatter2.setMinimum(0);
		formatter2.setMaximum(10000);
		formatter2.setCommitsOnValidEdit(true);
        iterNumberField = new JFormattedTextField(formatter2);
        iterNumberField.setToolTipText("<10000");
        iterNumberField.setText("1000");
        iterNumberField.setBounds(176, 103, 180, 28);
        panel.add(iterNumberField);
        iterNumberField.setColumns(10);
        
        sliderProbOfMutat = new JSlider(0,100,50);
        sliderProbOfMutat.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		int value = sliderProbOfMutat.getValue();
    			probabilityMutationValueLabel.setText(value + "%");
        	}
        });
        sliderProbOfMutat.setBounds(566, 150, 190, 29);
        panel.add(sliderProbOfMutat);
        
        JLabel lblNewLabel = new JLabel("Probability of crossing");
        lblNewLabel.setBounds(415, 188, 154, 16);
        panel.add(lblNewLabel);
        
        sliderProbOfCross = new JSlider(0,100,50);
        sliderProbOfCross.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		int value = sliderProbOfCross.getValue();
    			probabilityCrossingValueLabel.setText(value + "%");
        	}
        });
        sliderProbOfCross.setBounds(566, 182, 190, 29);
        panel.add(sliderProbOfCross);
        
        JLabel lblNewLabel_1 = new JLabel("Method of selection:");
        lblNewLabel_1.setBounds(25, 185, 170, 16);
        panel.add(lblNewLabel_1);
        
        rdBtnRoulette = new JRadioButton("Roulette");
        rdBtnRoulette.setBounds(50, 204, 141, 23);
        rdBtnRoulette.setSelected(true);
        panel.add(rdBtnRoulette);
        
        rdBtnStochastic = new JRadioButton("Stochastic Universal Sampling");
        rdBtnStochastic.setBounds(50, 231, 221, 23);
        panel.add(rdBtnStochastic);
        radioBtnGroup.add(rdBtnRoulette);
        radioBtnGroup.add(rdBtnStochastic);
        
        JLabel lblOkresPredykacji = new JLabel("Period of prediction");
        lblOkresPredykacji.setBounds(25, 137, 134, 16);
        panel.add(lblOkresPredykacji);
        
        //formatowanie ograniczen dla periodOfPredField
		formatter3.setMinimum(1);
		formatter3.setMaximum(Integer.MAX_VALUE);
		formatter3.setCommitsOnValidEdit(true);
        periodOfPredField = new JFormattedTextField(formatter3);
        periodOfPredField.setToolTipText(">0");
        periodOfPredField.setBounds(176, 131, 180, 28);
        panel.add(periodOfPredField);
        periodOfPredField.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("Time series");
        lblNewLabel_2.setBounds(25, 53, 134, 16);
        panel.add(lblNewLabel_2);
        
        timeWindowField = new JTextField();
        timeWindowField.setBounds(176, 19, 180, 28);
        panel.add(timeWindowField);
        timeWindowField.setColumns(10);
        
        JLabel lblOknoCzasowe = new JLabel("Time window");
        lblOknoCzasowe.setBounds(25, 25, 134, 16);
        panel.add(lblOknoCzasowe);
        
        timeSeriesField = new JLabel();
        timeSeriesField.setBounds(176, 47, 180, 28);
        timeSeriesField.setText("---");
        panel.add(timeSeriesField);
        
        JLabel lblProcentOsobnikwPozostwionych = new JLabel("Percent of species left after:");
        lblProcentOsobnikwPozostwionych.setBounds(415, 25, 281, 16);
        panel.add(lblProcentOsobnikwPozostwionych);
        
        JLabel lblProcentPoSelekcji = new JLabel("Selection");
        lblProcentPoSelekcji.setLabelFor(lblProcentPoSelekcji);
        lblProcentPoSelekcji.setBounds(503, 53, 66, 16);
        panel.add(lblProcentPoSelekcji);
        
        JLabel lblProcentPoKrzyzowaniu = new JLabel("Crossing");
        lblProcentPoKrzyzowaniu.setLabelFor(lblProcentPoKrzyzowaniu);
        lblProcentPoKrzyzowaniu.setBounds(503, 81, 66, 16);
        panel.add(lblProcentPoKrzyzowaniu);
        
        JLabel lblProcentPoMutacji= new JLabel("Mutation");
        lblProcentPoMutacji.setLabelFor(lblProcentPoMutacji);
        lblProcentPoMutacji.setBounds(503, 109, 66, 16);
        panel.add(lblProcentPoMutacji);
        
        	sliderSelekcji = new JSlider(0,100,40);
        	sliderSelekcji.addChangeListener(new ChangeListener() {
        		public void stateChanged(ChangeEvent e) {
        			int value = sliderSelekcji.getValue();
        			selectionValueLabel.setText(value + "%");
        			
        			int difference = sliderSelekcji.getValue() + sliderKrzyzowania.getValue() + sliderMutacji.getValue() - 100;
        			if (difference > 0) {
						sliderKrzyzowania.setValue(sliderKrzyzowania.getValue() - difference + difference / 2);
						sliderMutacji.setValue(sliderMutacji.getValue() - difference + difference / 2);
					}
        			
        		}
        	});
        	sliderSelekcji.setBounds(566, 47, 190, 29);
        	panel.add(sliderSelekcji);
        	
        	sliderKrzyzowania = new JSlider(0,100,40);
        	sliderKrzyzowania.addChangeListener(new ChangeListener() {
        		public void stateChanged(ChangeEvent e) {
        			int value = sliderKrzyzowania.getValue();
        			crossingValueLabel.setText(value + "%");
        			
        			int difference = sliderSelekcji.getValue() + sliderKrzyzowania.getValue() + sliderMutacji.getValue() - 100;
        			if (difference > 0) {
						sliderSelekcji.setValue(sliderSelekcji.getValue() - difference + difference / 2);
						sliderMutacji.setValue(sliderMutacji.getValue() - difference + difference / 2);
					}
        		}
        	});
        	sliderKrzyzowania.setBounds(566, 74, 190, 29);
        	panel.add(sliderKrzyzowania);
        	
        	sliderMutacji = new JSlider(0,100,20);
        	sliderMutacji.addChangeListener(new ChangeListener() {		
				public void stateChanged(ChangeEvent e) {
					int value = sliderMutacji.getValue();
        			mutationValueLabel.setText(value + "%");
        			
        			int difference = sliderSelekcji.getValue() + sliderKrzyzowania.getValue() + sliderMutacji.getValue() - 100;
        			if (difference > 0) {
						sliderKrzyzowania.setValue(sliderKrzyzowania.getValue() - difference + difference / 2);
						sliderSelekcji.setValue(sliderSelekcji.getValue() - difference + difference / 2);
					}
				}
			});
        	sliderMutacji.setBounds(566, 102, 190, 29);
        	panel.add(sliderMutacji);
        	
        	selectionValueLabel = new JLabel();
        	selectionValueLabel.setText("40%");
        	selectionValueLabel.setBounds(755, 47, 50, 28);
        	panel.add(selectionValueLabel);
        	
        	crossingValueLabel = new JLabel();
        	crossingValueLabel.setText("40%");
        	crossingValueLabel.setBounds(755, 75, 50, 28);
        	panel.add(crossingValueLabel);
        	
        	mutationValueLabel = new JLabel();
        	mutationValueLabel.setText("20%");
        	mutationValueLabel.setBounds(755, 103, 50, 28);
        	panel.add(mutationValueLabel);
        	
        	probabilityMutationValueLabel = new JLabel();
        	probabilityMutationValueLabel.setText("50%");
        	probabilityMutationValueLabel.setBounds(755, 150, 50, 28);
        	panel.add(probabilityMutationValueLabel);
        	
        	probabilityCrossingValueLabel = new JLabel();
        	probabilityCrossingValueLabel.setText("50%");
        	probabilityCrossingValueLabel.setBounds(755, 182, 50, 28);
        	panel.add(probabilityCrossingValueLabel);
        	
        	JLabel lblMethodOfCounting = new JLabel("Method of counting prediction:");
        	lblMethodOfCounting.setBounds(25, 282, 215, 16);
        	panel.add(lblMethodOfCounting);
        	
        	rdbtnLinearCombination = new JRadioButton("Linear combination");
        	rdbtnLinearCombination.setBounds(50, 304, 170, 23);
        	rdbtnLinearCombination.setSelected(true);
        	panel.add(rdbtnLinearCombination);
        	  
        	rdbtnArmaForecast = new JRadioButton("Arma forecast");
        	rdbtnArmaForecast.setBounds(50, 331, 141, 23);
        	panel.add(rdbtnArmaForecast);
        	        
        	radioBtnGroup2.add(rdbtnLinearCombination);
        	radioBtnGroup2.add(rdbtnArmaForecast);
        	
//        statisticsPanel.add(var2Value);
        //================================
        
    }
	
	public JLabel getLblLowerBound() {
		return lblLowerBound;
	}

	public JLabel getLblUpperBound() {
		return lblUpperBound;
	}

	public JLabel getLblMeanSeries1() {
		return lblMeanSeries1;
	}

	public JLabel getLblMeanSeries2() {
		return lblMeanSeries2;
	}

	public JLabel getLblVarianceSeries1() {
		return lblVarianceSeries1;
	}

	public JLabel getLblVarianceSeries2() {
		return lblVarianceSeries2;
	}

	public JLabel getSelectionValueLabel() {
		return selectionValueLabel;
	}

	public JLabel getCrossingValueLabel() {
		return crossingValueLabel;
	}

	public JLabel getMutationValueLabel() {
		return mutationValueLabel;
	}

	public TimeSeries getCurrentTimeSeries() {
		return currentTimeSeries;
	}

	public JFormattedTextField getPopulSizeField() {
		return populSizeField;
	}

	public JFormattedTextField getIterNumberField() {
		return iterNumberField;
	}

	public JFormattedTextField getPeriodOfPredField() {
		return periodOfPredField;
	}

	public JTextField getTimeWindowField() {
		return timeWindowField;
	}

	public JLabel getTimeSeriesField() {
		return timeSeriesField;
	}

	public JSlider getSliderSelekcji() {
		return sliderSelekcji;
	}

	public JSlider getSliderKrzyzowania() {
		return sliderKrzyzowania;
	}

	public JSlider getSliderMutacji() {
		return sliderMutacji;
	}

	public JSlider getSliderProbOfMutat() {
		return sliderProbOfMutat;
	}

	public JSlider getSliderProbOfCross() {
		return sliderProbOfCross;
	}

	public ButtonGroup getRadioBtnGroup() {
		return radioBtnGroup;
	}

	public JRadioButton getRdBtnRoulette() {
		return rdBtnRoulette;
	}
	
	public JRadioButton getRdbtnLinearCombination() {
		return rdbtnLinearCombination;
	}
	
	public JRadioButton getRdBtnStochastic() {
		return rdBtnStochastic;
	}

	public FitnessChart getFitnessChart() {
		return fitnessChart;
	}

	public TimeSeriesChart getTimeSeriesChartDataIn() {
		return timeSeriesChartDataIn;
	}
	
	public TimeSeriesChart getTimeSeriesChartWithForecast() {
		return timeSeriesChartWithForecast;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public Statistics getStat() {
		return stat;
	}

	public JRadioButton getRdbtnArmaForecast() {
		return rdbtnArmaForecast;
	}

	public TimeSeries getForecast() {
		return forecast;
	}
}
