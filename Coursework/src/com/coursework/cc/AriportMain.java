package com.coursework.cc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import com.google.gson.*;


import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import com.coursework.cc.Item;


public class AriportMain {

	static FileWriter fstream;
	static BufferedWriter out;
	static String passangerdata;
	static String airport_latlong;
	static String writerfile;

	public static void main(String[] args) throws Exception {
		try {

			// two data file imported into Eclipse using command line parameters.

			passangerdata = args[0];
			airport_latlong = args[1];
			// output written file
			writerfile = args[2];
			fstream = new FileWriter(writerfile);
			out = new BufferedWriter(fstream);

			// Error Catching for incorrect file inputs.
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("Invalid Input, Please Try Again");
			JOptionPane.showMessageDialog(null, "Invalid Input, Please Try Again!", "Airpot Dat Analysis ",
					JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		//User Selects the type of Task execution 
		System.out.println("Select a Task to execute");
		System.out.println("A: Determine Number of Flights");
		System.out.println("B: List of Flight Data");
		System.out.println("C: Number of Passangers on Each Flight");
		System.out.println("D: Calculate Passangers distance travelled in miles");

		String input = in.readLine();
		grade = input.charAt(0);
		switch (grade) {
		case 'A':
			task1();
			break;
		case 'B':
			task2();
			break;
		case 'C':
			task3();
			break;
		case 'D':
			task2();
			task4(null);
			break;
		default:
			System.out.println("Invalid Selection, Please Try Again! ");

			JOptionPane.showMessageDialog(null, "Invalid Selection, Please Try Again!", "Task Options",
					JOptionPane.WARNING_MESSAGE);

		}
	}

	private static void task1() throws IOException {
		System.out.println(grade + ": This task Determines the number of Flights from Each airport");
		// HashMap cannot sort data.
		TreeMap<String, Integer> dataset = new TreeMap<String, Integer>();
		// Read the values in.
		BufferedReader br = new BufferedReader(new FileReader(passangerdata));
		HashMap<String, Integer> invalid_dataset = new HashMap<String, Integer>();
		try {

			String line;
			while ((line = br.readLine()) != null) {
				String[] departure_airport = line.split("\\s*,\\s*");
				// error checking for invalid data.
				if (departure_airport[2].length() == 3 && 
						Pattern.matches("[A-Z]{3}", departure_airport[2])) {
					// System.out.println("Valid Data ");
					// check if the hash table has a new key or existing key
					if (dataset.containsKey(departure_airport[2])) {
						dataset.put(departure_airport[2], dataset.get(departure_airport[2]) + 1);
					} else {
						dataset.put(departure_airport[2], 1);
					}
				} else {
					//store invalid and unused airports in a new HashMap
					invalid_dataset.put(departure_airport[2], null );
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		br.close();

		// Reduce and Sort of the MapReduce HashTable
		fstream.write("This section displays the number of flights from each airport."  + "\n");
		fstream.write( "\n"+ "****************************" + "\n");
		for (Entry<String, Integer> entry : dataset.entrySet()) {
			System.out.println("Airport = " + entry.getKey() + ", Flights = " + entry.getValue());
			// this statement prints out my keys and values
			out.write(entry.getKey() + " =" + "\t" + entry.getValue() + "\n");
			// System.out.println("Done");
			out.flush(); // Flush the buffer and write all changes to the disk

		}
		
		//Reduce for Unused airports 
		fstream.write("Below is a list of all the invalid and unused airports" + "\n");
		fstream.write("****************************" + "\n");
		for (Entry<String, Integer> entry : invalid_dataset.entrySet()) {
			System.out.println("Airport = " + entry.getKey() + ", Flights = " + entry.getValue());
			// this statement prints out my keys and values
			out.write("Airport = " + entry.getKey()  + "\n");			
			out.flush(); // Flush the buffer and write all changes to the disk

		}

	}

	static String jsonString;
	static java.lang.reflect.Type type;
	static char grade;
	static Gson gson;
	static TreeMap<String, List<Item>> dataset = new TreeMap<String, List<Item>>();

	private static void task2() throws IOException {

		System.out.println(grade + ": This task creates a list of all the flight data: ");

		HashMap<String, Integer> invalid_dataset = new HashMap<String, Integer>();

		// Read the values in.
		BufferedReader br = new BufferedReader(new FileReader(passangerdata));
		try {

			String line;
			while ((line = br.readLine()) != null) {
				String[] data = line.split("\\s*,\\s*");
				// error checking for invalid data.
				if (data[1].length() == 8 && Pattern.matches("[A-Z]{3}[0-9]{4}[A-Z]{1}", data[1])
						&& Pattern.matches("[A-Z]{3}[0-9]{4}[A-Z]{2}[0-9]$", data[0])
						&& Pattern.matches("[A-Z]{3}$", data[2]) && Pattern.matches("[A-Z]{3}$", data[3])
						&& Pattern.matches("\\d{10}$", data[4]) && Pattern.matches("\\d{1,4}$", data[5])) {
					// System.out.println(dataset);
					if (dataset.get(data[1]) == null) {
						dataset.put(data[1], new ArrayList<Item>());
						// create a new array list with a key.
					}
					/// add the following data from the corresponding flight id.
					dataset.get(data[1]).add(new Item(data[0], data[1], data[2], data[3], data[4], data[5]));
					// System.out.println(dataset);
				} else {
					// if the dataset doesn't match the format requirements
					if (invalid_dataset.containsKey(data[1])) {
						invalid_dataset.put(data[1], invalid_dataset.get(data[2]) + 1);
					} else {
						invalid_dataset.put(data[2], 1);
						// System.out.println(invalid_dataset);
					}
				}

//				 gson = new Gson);
//				 jsonString = gson.toJson(dataset);
//				 type = new TypeToken<TreeMap<Integer, List<Item>>>(){}.getType();
//		
			}

			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(grade + ": This task creates numbers of passangers on each flight");
		fstream.write("FlightID: "  );
		fstream.write("Details of Passengers" + "\t"+"\n"+ "Passenger Id, Departure, Arrival, Data, Time(Minutes) " + "\n");
		for (Entry<String, List<Item>> entry : dataset.entrySet()) {
			// this statement prints out my keys and values
			out.write("\n" + entry.getKey() + "\n");
			out.write("*******************************************" + "\n");
			System.out.println("\n" + entry.getKey());
			System.out.println("*******************************************");
			for (Item key_array : entry.getValue()) {
				out.write(key_array.toString() + "\n");
				System.out.println(key_array.toString());
			}
			out.flush(); // Flush the buffer and write all changes to the disk

		}
	}

	private static void task3() throws IOException {
		TreeMap<String, Integer> dataset = new TreeMap<String, Integer>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(passangerdata));
			String line;

			while ((line = br.readLine()) != null) {
				String data[] = line.split("\\s*,\\s*");
				if (data[1].length() == 8 && Pattern.matches("[A-Z]{3}[0-9]{4}[A-Z]{1}$", data[1])) {

					if (dataset.containsKey(data[1])) {
						dataset.put(data[1], dataset.get(data[1]) + 1);
					} else {
						dataset.put(data[1], 1);
					}
				} else {
					// System.out.println("invalid flightid " + data[1] );
				}
			}
			System.out.println(dataset);
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Reduce and Soft of the MapReduce HashTable
		System.out.println(grade + ": This task creates numbers of passangers on each flight");
		fstream.write("PassengerID" + " = " + "\t" + "No of Passengers on Each Flight" + "\n" + "\n");
		fstream.write("*************************************************" + "\n");
		for (Entry<String, Integer> entry : dataset.entrySet()) {
			System.out.println("Flight ID = " + entry.getKey() + ", Passengers = " + entry.getValue());
			// this statement prints out my keys and values
			out.write(entry.getKey() + " =" + "\t" + entry.getValue() + "\n");
			// System.out.println("Done");
			out.flush(); // Flush the buffer and write all changes to the disk

		}

		// TODO Auto-generated method stub

	}

	private static DecimalFormat df2 = new DecimalFormat("#.##");

//	static HashMap<Integer, List<Item>> clonedMap = gson.fromJson(jsonString, type);
	private static void task4(TreeMap<String, Integer> passengerDistance) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(airport_latlong));
		HashMap<String, Latlong> airport_latlng = new HashMap<String, Latlong>();

		HashMap<String, Double> air_miles = new HashMap<String, Double>();

		String line;
		// Create a list of Flight and its Latitude and Longitude Value in a HashMap
		try {
			while ((line = br.readLine()) != null) {
				try {

					String[] data = line.split("\\s*,\\s*");
					if (data[1].length() == 3 && Pattern.matches("[A-Z]{3}$", data[1])
							&& Pattern.matches("(\\+|-)?([0-9]+(\\.[0-9]+))", data[2])
							&& Pattern.matches("(\\+|-)?([0-9]+(\\.[0-9]+))", data[3])) {
						if (airport_latlng.get(data[1]) == null) {
							airport_latlng.put(data[1], new Latlong(data[1], data[2], data[3]));
						}
					}
				} catch (Exception e) {
					System.out.println("Error on Airport");
				}

			}
			System.out.println(airport_latlng);
			br.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Map.Entry<String, List<Item>> entry : dataset.entrySet()) {
			
			for (Item nauticalmile : entry.getValue()) {

				double lat1;
				double lat2;
				double long1;
				double long2;
				try {
					lat1 = ((Latlong) airport_latlng.get(nauticalmile.dest)).lat1;
					long1 = ((Latlong) airport_latlng.get(nauticalmile.dest)).lat1;
					lat2 = ((Latlong) airport_latlng.get(nauticalmile.arrive)).lng1;
					long2 = ((Latlong) airport_latlng.get(nauticalmile.arrive)).lng1;

					double miles = distance(lat1, lat2, long1, long2);

					double miles1 = Double.parseDouble(df2.format(miles));
					air_miles.put(entry.getKey(), miles1);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// System.out.println(air_miles);
			}

		}

		// Reduce and Sort of the MapReduce HashTable
		System.out.println(grade + ": This calculates the air miles for each Flight ID" + "\n" + "\n");
		fstream.write("*************************************************" + "\n");
		fstream.write("\n" + "This calculates the air miles for each Flight ID" + "\n" + "\n");

		for (Entry<String, Double> entry : air_miles.entrySet()) {
			System.out.println("Flight_ID:" + entry.getKey() + ", Air_Miles = " + entry.getValue());
			// this statement prints out my keys and values
			out.write(entry.getKey() + " =" + "\t" + entry.getValue() + " miles" +"\n");
			// System.out.println("Done");
			out.flush(); // Flush the buffer and write all changes to the disk

		}
		//Reduce and Sort of Passenger Air Miles
		TreeMap<String, Double> passenger_airmiles = new TreeMap<String, Double>();
		for (Map.Entry<String, List<Item>> entry : dataset.entrySet()) {
			//
			for (Item entry1 : entry.getValue()) {
				//add a new Passenger entry if Passenger ID key is null.
				if (passenger_airmiles.get(entry1.passengerid) == null) {
					passenger_airmiles.put(entry1.passengerid, air_miles.get(entry1.flightid));
				} else {
					//else add the distance to an existing Passsenger ID key.
					double new_dist = passenger_airmiles.get(entry1.passengerid);
					passenger_airmiles.put(entry1.passengerid, air_miles.get(entry1.flightid) + new_dist);
				}
			}
		}

		System.out.println(grade + ": This calculates the air miles for each Flight ID" + "\n" + "\n");
		fstream.write("*************************************************" + "\n");
		fstream.write("\n" + "This calculates the air miles for each Passenger" + "\n" + "\n");
		for (Entry<String, Double> entry : passenger_airmiles.entrySet()) {
			System.out.println("Passenger = " + entry.getKey() + ", Miles = " + entry.getValue());
			// this statement prints out my keys and values

			String entry1 = df2.format(entry.getValue());

			out.write(entry.getKey() + " =" + "\t" + entry1 + " miles" +"\n");
			// System.out.println("Done");
			out.flush(); // Flush the buffer and write all changes to the disk

		}
		

		System.out.println(grade + ": This task calculats the p assangers distance travelled in miles");

	}

	public static double distance(double lat1, double lat2, double lon1, double lon2) {

		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance_meters = R * c * 1000; // convert to meters
		double distance_miles = distance_meters * 0.00062137119;

		return Math.sqrt(distance_miles);
	}



}
