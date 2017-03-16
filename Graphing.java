import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.*;

public class Graphing extends JPanel {

	JPanel panel;
	
   public Graphing() {
	   JFrame frame = new JFrame ("Bar Chart");
	   setSize(800, 1000);
	   frame.setLocationRelativeTo(null);
	   frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

	   Container container = frame.getContentPane();
	   container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
	   //Adding the panel for label and graph
	   panel = new JPanel();
	   panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	   panel.setSize(700, 100);
		
	   //Adding graph label
	   JLabel label = new JLabel("Title of Graph");
	   label.setHorizontalAlignment(JLabel.CENTER);
	   panel.add(label);
		
	   //Adding graph
	   DataManager datamanager = new DataManager();
	   datamanager.readDataFromFile(".//src//sales//sales.txt");
	   chart = new BarChart(datamanager.getData());
	   chart.setSize(600, 800);
	   panel.add(chart);
	   panel.setVisible(false);
		
	   //Adding panel
	   container.add(panel);
	   setVisible(true);
   }
   
   public static void main (String[] args) {
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	           new Graphing();
	        }
	    });
   }
}

interface DataGetter {
	public void readDataFromFile(String fileName);
}

class DataManager implements DataGetter {
	private Map<Color, Sales> sales = new LinkedHashMap<Color, Sales>();
	
	private Random randomGenerator = new Random();
	private Color[] salesColors;

	public void readDataFromFile(int id) {
		try (BufferedReader br = new BufferedReader(new FileReader("src/data/RCM-" + id + ".txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	// split each line into tokens
				String[] fields = line.split("-");

				// the String to int conversion happens here
				int quarter = Integer.parseInt(fields[0].trim());
				int salesAmount = Integer.parseInt(fields[1].trim());
				
				//Create new random color
				saleByQ = new Sales(quarter, salesAmount);
				int red = randomGenerator.nextInt(256);
				int green = randomGenerator.nextInt(256);
				int blue = randomGenerator.nextInt(256);

				Color randomColor = new Color(red, green, blue);
				sales.put(randomColor, saleByQ);
				++lineCnt;
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}

		//For every line in the file
		BufferedReader reader = null;
		int lineCnt = 0;
		try {
			File inFile = new File(fileName);
			reader = new BufferedReader(new FileReader(inFile));

			// ... Loop as long as there are input lines.
			String line = null;
			
			//Try to read the line
			try {
				while ((line = reader.readLine()) != null) {

					// split each line into tokens
					String[] fields = line.split(":");

					// the String to int conversion happens here
					int quarter = Integer.parseInt(fields[0].trim());
					int salesAmount = Integer.parseInt(fields[1].trim());
					
					//Create new random color
					saleByQ = new Sales(quarter, salesAmount);
					int red = randomGenerator.nextInt(256);
					int green = randomGenerator.nextInt(256);
					int blue = randomGenerator.nextInt(256);

					Color randomColor = new Color(red, green, blue);
					sales.put(randomColor, saleByQ);
					++lineCnt;
				}
			} finally {
				reader.close();
			}
		} catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		} catch (NumberFormatException e) {
			System.out.println("NumberFormatException: ");
			System.exit(1);
		}
	}

	/**
	 * Map, returns the map sales
	 * @return Map of colors and sales.
	 */
	public Map<Color, Sales> getData() {
		return sales;
	}
}

/**
 * BarChart, creates a chart with sales data, and labels their year
 * @author Kevin Velcich
 */
class BarChart extends JPanel {
	//Setting up arrays of values
	private Map<Color, Integer> bars = new LinkedHashMap<Color, Integer>();
	private ArrayList<Integer> year;
	/**
	 * BarChart, initializes the arrays and adds their values
	 * @param data
	 */
	public BarChart(Map<Color, Sales> data) {
		year = new ArrayList<Integer>();
		for (Color keyColor : data.keySet()) {
			Sales sale = data.get(keyColor);
			bars.put(keyColor, new Integer(sale.salesInK));
			//Adding the years to the year array list.
			year.add(sale.year);
		}
	}

	/**
	 * paintComponent, creates the bargraph
	 */
	@Override
	protected void paintComponent(Graphics gp) {
		super.paintComponent(gp);
		// Cast the graphics objects to Graphics2D
		Graphics2D g = (Graphics2D) gp;
		// determine longest bar
		int max = Integer.MIN_VALUE;
		for (Integer value : bars.values()) {
			max = Math.max(max, value);
		}
		// paint bars

		int width = (getWidth() / bars.size()) - 2;
		int x = 1;
		int i = 0;
		for (Color color : bars.keySet()) {
			//Adding the rectangle for the bar graph
			int value = bars.get(color);
			int height = (int) ((getHeight() - 5) * ((double) value / max));
			g.setColor(color);
			g.fillRect(x, getHeight() - height, width, height);
			g.setColor(Color.black);
			g.drawRect(x, getHeight() - height, width, height);
			
			//Adding the text label for the year
			g.drawString(year.get(i).toString(), x + width/2, getHeight());
			x += (width + 2);
			i++;
		}
	}

	/**
	 * getPreferredSize, returns the preferred size of the chart
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(bars.size() * 10 + 2, 50);
	}
}
