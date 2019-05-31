package com.coursework.cc;

public class Latlong {
	
	double lat1;
	double lng1;
	String  flight1;
	
	
	public Latlong(String flight, String lat, String lng) {
		
		 flight1 = flight; 
		 lat1 = Double.parseDouble(lat);
		 lng1 = Double.parseDouble(lng);
		
		
	}


}
