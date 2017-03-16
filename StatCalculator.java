import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StatCalculator {
	
	
	public StatCalculator() {

	}
	
	public void itemRecycled(int id, Recyclable recyclable, double weight) {
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Calendar calobj = Calendar.getInstance();
		String timestamp = df.format(calobj.getTime());
		
		String line = timestamp + "-"  + recyclable.getType() + "-" + Double.toString(weight) + "-"  + Integer.toString((int)(recyclable.getPricePerPound() * weight));
		try {
		    Files.write(Paths.get("RCM.txt"), line.getBytes(), StandardOpenOption.APPEND);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void main(String args[]) {
		System.out.println("h3e");
		StatCalculator sc = new StatCalculator();
		sc.itemRecycled(0, new Recyclable("Paper", 0.5, 0.1), 1);
		sc.itemRecycled(0, new Recyclable("Paper", 0.5, 0.1), 3);
		sc.itemRecycled(0, new Recyclable("Glass", 1, 0.5), 2);
		System.out.println("he");
	}
}
