package recycling.simulation.helper;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class StatCalculator {	
	public static final int DAY = 10;
	public static final int WEEK = 70;
	public static final int MONTH = 300;
	public static final int YEAR = 3600;
	
	public static void itemRecycled(int id, Recyclable recyclable, double weight) {
		DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		Calendar calobj = Calendar.getInstance();
		String timestamp = df.format(calobj.getTime());
		
		//Format: Date Time-Type-Weight-Price
		String line = timestamp + "-"  + recyclable.getType() + "-" + Double.toString(weight) + "-"  + Integer.toString((int)(recyclable.getPricePerPound() * weight)) + System.lineSeparator();
		
		FileWriter fw = null;
		try {
		    fw = new FileWriter("resources/RCM-" + id + ".txt", true);
		    fw.write(line);
		    fw.close();
		} catch (IOException e) {
		    System.out.println(e);
		}
	}
	
	public static int numSpecItemInTimeFrame(int id, String type, int timeFrame) {
		int counter = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader("resources/RCM-" + id + ".txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String[] parts = line.split("-");
		    	
				DateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
				Calendar calobj = Calendar.getInstance();
				String timestamp = format.format(calobj.getTime());
				
		    	Date d1 = null;
		    	Date d2 = null;
		    	try {
		    	    d1 = format.parse(parts[0]);
		    	    d2 = format.parse(timestamp);
		    	} catch (ParseException e) {
		    	    e.printStackTrace();
		    	}    

		    	long diff = d2.getTime() - d1.getTime();
		    	//Given in seconds
		    	long timeDiff = TimeUnit.MILLISECONDS.toSeconds(diff);
		    	if (timeDiff <= timeFrame && parts[1].equals(type))
		    		counter++;
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return counter;
	}
	
	public static int numItemInTimeFrame(int id, int timeFrame) {
		int counter = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader("resources/RCM-" + id + ".txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String[] parts = line.split("-");
		    	
				DateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
				Calendar calobj = Calendar.getInstance();
				String timestamp = format.format(calobj.getTime());
				
		    	Date d1 = null;
		    	Date d2 = null;
		    	try {
		    	    d1 = format.parse(parts[0]);
		    	    d2 = format.parse(timestamp);
		    	} catch (ParseException e) {
		    	    e.printStackTrace();
		    	}    

		    	long diff = d2.getTime() - d1.getTime();
		    	//Given in seconds
		    	long timeDiff = TimeUnit.MILLISECONDS.toSeconds(diff);
		    	if (timeDiff <= timeFrame)
		    		counter++;
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return counter;
	}
	
	public static int getCompleteWeight(int id) {
		int counter = 0;
		try (BufferedReader br = new BufferedReader(new FileReader("resources/RCM-" + id + ".txt"))) {
		    String line;
		    while ((line = br.readLine()) != null)
		    		counter++;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return counter;
	}
	
	public static double getWeightInTimeFrame(int id, int timeFrame) {
		double weight = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader("resources/RCM-" + id + ".txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String[] parts = line.split("-");
		    	
				DateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
				Calendar calobj = Calendar.getInstance();
				String timestamp = format.format(calobj.getTime());
				
		    	Date d1 = null;
		    	Date d2 = null;
		    	try {
		    	    d1 = format.parse(parts[0]);
		    	    d2 = format.parse(timestamp);
		    	} catch (ParseException e) {
		    	    e.printStackTrace();
		    	}    

		    	long diff = d2.getTime() - d1.getTime();
		    	//Given in seconds
		    	long timeDiff = TimeUnit.MILLISECONDS.toSeconds(diff);
		    	if (timeDiff <= timeFrame)
		    		weight += Double.parseDouble(parts[2]);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weight;
	}
	
	public static Money totalMoneyIssued(int id) {
		Money total = new Money();
		
		try (BufferedReader br = new BufferedReader(new FileReader("resources/RCM-" + id + ".txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String[] parts = line.split("-");
		    	total.addTo(0, Integer.parseInt(parts[3]));
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public static Money getMoneyInTimeFrame(int id, int timeFrame) {
		Money total = new Money();
		
		try (BufferedReader br = new BufferedReader(new FileReader("resources/RCM-" + id + ".txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String[] parts = line.split("-");
		    	
				DateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
				Calendar calobj = Calendar.getInstance();
				String timestamp = format.format(calobj.getTime());
				
		    	Date d1 = null;
		    	Date d2 = null;
		    	try {
		    	    d1 = format.parse(parts[0]);
		    	    d2 = format.parse(timestamp);
		    	} catch (ParseException e) {
		    	    e.printStackTrace();
		    	}    

		    	long diff = d2.getTime() - d1.getTime();
		    	//Given in seconds
		    	long timeDiff = TimeUnit.MILLISECONDS.toSeconds(diff);
		    	if (timeDiff <= timeFrame)
		    		total.addTo(0, Integer.parseInt(parts[3]));
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public static void empty(int id) {
		DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		Calendar calobj = Calendar.getInstance();
		String timestamp = df.format(calobj.getTime());
		
		//Format: Date Time-Type-Weight-Price
		String line = timestamp + System.lineSeparator();
		
		FileWriter fw = null;
		try {
		    fw = new FileWriter("resources/RCM-" + id + "Empty.txt", true);
		    fw.write(line);
		    fw.close();
		} catch (IOException e) {
		    System.out.println(e);
		}
	}
	
	public static int getEmptyInTimeFrame(int id, int timeFrame) {
		int counter = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader("resources/RCM-" + id + "Empty.txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
				DateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
				Calendar calobj = Calendar.getInstance();
				String timestamp = format.format(calobj.getTime());
				
		    	Date d1 = null;
		    	Date d2 = null;
		    	try {
		    	    d1 = format.parse(line);
		    	    d2 = format.parse(timestamp);
		    	} catch (ParseException e) {
		    	    e.printStackTrace();
		    	}    

		    	long diff = d2.getTime() - d1.getTime();
		    	//Given in seconds
		    	long timeDiff = TimeUnit.MILLISECONDS.toSeconds(diff);
		    	if (timeDiff <= timeFrame)
		    		counter++;
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return counter;
	}
	
	public static String getLastEmpty(int id) {
		String time = "";
		
		try (BufferedReader br = new BufferedReader(new FileReader("resources/RCM-" + id + "Empty.txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	time = line;
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}
	
	public static void resetData(int id) {
		FileWriter fw1 = null, fw2 = null;
		try {
			fw1 = new FileWriter("resources/RCM-" + id + ".txt", false);
			fw2 = new FileWriter("resources/RCM-" + id + "Empty.txt", false);
		    fw1.write("");
		    fw2.write("");
		    fw1.close();
		    fw2.close();
		} catch (IOException e) {
		    System.out.println(e);
		}
	}

	public static int getNumItems(int id) {
		int counter = 0;

		try (BufferedReader br = new BufferedReader(new FileReader("resources/RCM-" + id + ".txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				counter++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return counter;
	}
}