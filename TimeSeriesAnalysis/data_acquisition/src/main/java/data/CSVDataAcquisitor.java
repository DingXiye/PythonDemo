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

package data;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

/**
 * 允许以CSV格式和转换读取文件的类
 */
public class CSVDataAcquisitor 
{
	/**
	 * 一个类，用于读取和解释CSV文件中与模式相关的数据
	 */
	CSVReader csvreader;
	/**
	 * 文件路径
	 */
	String filePath;
	/**
	 * 处理日期的格式
	 */
	String dateFormat;
	/**
	 * 日期所在列的编号
	 */
	int timeTab;
	/**
	 * 带有值的列号
	 */
	int valueTab;
    
	/**
	 * 默认情况下接受值的类构造函数：
	 * ',' - 间隔
	 * '\"' - 数据指定
	 * "yyyy-MM-dd" - format daty
	 * 0 - 列号与日期
	 * 1 - 带有值的列号
	 * @param file CSV文件的路径
	 * @throws IOException 文件处理错误
	 */
	public CSVDataAcquisitor(String file) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file),',','\"');
		this.filePath=file;
		this.dateFormat="yyyy-MM-dd";
		this.timeTab=0;
		this.valueTab=1;
	}
	
	/**
	 * 类构造函数
	 * @param file CSV文件的路径
	 * @param separator 用于分隔数据字段的字符
	 * @param quotechar 标志指定
	 * @throws IOException bład przetwarzania pliku
	 */
	public CSVDataAcquisitor(String file,char separator, char quotechar) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), separator, quotechar);
		this.filePath=file;
		this.dateFormat="yyyy-MM-dd";
		this.timeTab=0;
		this.valueTab=1;
	}
	
	/**
	 *类构造函数
	 * @param file CSV文件的路径
	 * @param separator 用于分隔数据字段的字符
	 * @param quotechar 标志指定
	 * @param tTab 列号与日期
	 * @param vTab 带有值的列号
	 * @throws IOException bład przetwarzania pliku
	 */
	public CSVDataAcquisitor(String file,char separator, char quotechar,int tTab,int vTab) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), separator, quotechar);
		this.filePath=file;
		this.dateFormat="yyyy-MM-dd";
		this.timeTab=tTab;
		this.valueTab=vTab;
	}
	
	/**
	 * 类构造函数
	 * @param file scieżka do pliku CSV
	 * @param separator znak oddzielający pola danych
	 * @param quotechar znak wyznaczający daną
	 * @param tTab numer kolumny z datą
	 * @param vTab numer kolumny z wartością
	 * @param dateFormat format daty
	 * @throws IOException bład przetwarzania pliku
	 */
	public CSVDataAcquisitor(String file,char separator, char quotechar,int tTab,int vTab,String dateFormat) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), separator, quotechar);
		this.filePath=file;
		this.dateFormat=dateFormat;
		this.timeTab=tTab;
		this.valueTab=vTab;
	}
	
	/**
	 * 类构造函数
	 * @param file scieżka do pliku CSV
	 * @param tTab numer kolumny z datą
	 * @param vTab numer kolumny z wartością
	 * @param dateFormat format daty
	 * @throws IOException bład przetwarzania pliku
	 */
	public CSVDataAcquisitor(String file,int tTab,int vTab,String dateFormat) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file), ',', '\"');
		this.filePath=file;
		this.dateFormat=dateFormat;
		this.timeTab=tTab;
		this.valueTab=vTab;
	}
	
	/**
	 * Konstruktor klasy
	 * @param file scieżka do pliku CSV
	 * @param tTab numer kolumny z datą
	 * @param vTab numer kolumny z wartością
	 * @throws IOException bład przetwarzania pliku
	 */
	public CSVDataAcquisitor(String file,int tTab,int vTab) throws IOException{
		this.csvreader=new CSVReader(new FileReader(file));
		this.filePath=file;
		this.dateFormat="yyyy-MM-dd";
		this.timeTab=tTab;
		this.valueTab=vTab;
	}
	
	/**
	 * 用日期设置列号的函数
	 * @param tTab 列号与日期
	 */
	public void setTimeTab(int tTab){
		this.timeTab=tTab;
	}
	/**
	 * 使用值设置列号的函数
	 * @param vTab 带有值的列号
	 */
	public void setValueTab(int vTab){
		this.valueTab=vTab;
	}
	/**
	 * 用于设置日期格式的函数
	 * @param format format daty
	 */
	public void setDateFormat(String format){
		this.dateFormat=format;
	}
	
	/**
	 * 从文件中读取数据的方法确定结果并将它们传递给输出
	 * @return 从适当的列中选择的数据
	 * @throws IOException błąd przetwarzania - brak przetworzonych danych
	 */
	public TimeSeries readData_TimeSeries() throws IOException{
		String name=this.filePath.substring(this.filePath.lastIndexOf("\\")+1,this.filePath.length());
		TimeSeries timeSeries=new TimeSeries(name);
		String[] nextLine;
		SimpleDateFormat dateFormatter = new SimpleDateFormat(this.dateFormat);
		while ((nextLine=this.csvreader.readNext())!=null){
			int added=timeSeries.getItemCount();
			try{
				if (nextLine[this.valueTab].contains(",")){
					nextLine[this.valueTab]=nextLine[this.valueTab].replaceAll(",",".");
				}
				if (nextLine[this.timeTab].contains("\"")){
					nextLine[this.timeTab]=nextLine[this.timeTab].replaceAll("\"", "");
				}
				timeSeries.add(new Day(dateFormatter.parse(nextLine[this.timeTab])),Double.valueOf(nextLine[this.valueTab]));
			}
			catch (Exception e){
				if (!timeSeries.isEmpty()){
					if (timeSeries.getItemCount()!=added){
						timeSeries.clear();
					}
				}
			}
		}
		if (timeSeries.isEmpty()){
			throw new IOException();
		}
		return timeSeries;
	}
	
	/**
	 * 从文件中读取数据的方法确定结果并将它们传递给输出
	 * @return 从适当的列中选择的数据
	 * @throws IOException 处理错误 - 未处理数据
	 */
	public double[] readData_DoubleValueTable() throws IOException{
		LinkedList<Double> list=new LinkedList<Double>();
		String[] nextLine;
		while ((nextLine=this.csvreader.readNext())!=null){
			int added=list.size();
			try{
				if (nextLine[this.valueTab].contains(",")){
					nextLine[this.valueTab]=nextLine[this.valueTab].replaceAll(",",".");
				}
				if (nextLine[this.timeTab].contains("\"")){
					nextLine[this.timeTab]=nextLine[this.timeTab].replaceAll("\"", "");
				}
				list.add(Double.parseDouble(nextLine[this.valueTab]));
			}
			catch (Exception e){
				if (!list.isEmpty()){
					if (list.size()!=added){
						list.clear();
					}
				}
			}
		}
		if (list.isEmpty()){
			throw new IOException();
		}
		double[] out=new double[list.size()];
		for (int i=0;i<list.size();i++){
			out[i]=list.get(i);
		}
		return out;
	}
}
