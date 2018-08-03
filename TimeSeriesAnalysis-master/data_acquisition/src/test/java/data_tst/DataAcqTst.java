package data_tst;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import org.jfree.data.time.TimeSeries;
import org.junit.Assert;
import org.junit.Test;
import data.CSVDataAcquisitor;
import data.SimpleDataAcquisitor;

public class DataAcqTst {

	@Test
	public void readingCSVTst(){
		try{
			CSVDataAcquisitor dataAcq=new CSVDataAcquisitor("res\\gielda1.csv", ',', '\"',1,12);
			TimeSeries ts=dataAcq.readData_TimeSeries();
			System.out.print(ts.getItemCount());
			Assert.assertEquals(ts.getItemCount(),212);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void doubleParseTst1() throws Exception{
		Assert.assertEquals(Double.valueOf("32.4"),32.4,0);
	}
	
	@Test
	public void doubleParseTst2() throws Exception{
		Assert.assertEquals(Double.valueOf("32,4"),32.4,0);
	}
	
	@Test
	public void dateTst() throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = formatter.parse("2013-01-23");
		Assert.assertEquals(d1.toString(),"2013-01-23");
	}
	
	@Test
	public void simpleDataAcqTst() throws Exception{
		SimpleDataAcquisitor sda=new SimpleDataAcquisitor("res\\simple1.txt");
		TimeSeries ts=sda.readData_TimeSeries();
		Assert.assertEquals(ts.getItemCount(),9);
	}
	@Test
	public void linkedListTst() throws Exception{
		LinkedList<Integer> list=new LinkedList<Integer>();
		list.add(new Integer(1));
		list.add(new Integer(2));
		list.add(new Integer(3));
		Integer i=list.get(1);
		i=8;
		Assert.assertEquals(((Integer)list.get(1)).intValue(),((Integer)8).intValue());
	}
}
