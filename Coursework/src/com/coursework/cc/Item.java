package com.coursework.cc;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Item {

	String passengerid;
	String flightid;
	String dest;
	String arrive;
	String date;
	String time;
	public String line;
	
	

public Item(String data0, String data1, String data2 , String data3, String data4, String data5) {
	passengerid = data0;
	flightid = data1;
	dest = data2;
	arrive = data3;
	date = unixSeconds(data4);
	time = data5;
			
}

public String  unixSeconds ( String dates) {
//convert seconds to milliseconds
	long unixSeconds = Long.parseLong(dates);
	Date date =  new Date(unixSeconds*1000L); 
	// the format of your date
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); 
//	sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4")); 
	String formattedDate = sdf.format(date);
	return formattedDate;

}
public String toString(){
	return passengerid + " " + dest + " "+ arrive +" "+ date +" "+ time;
}




}