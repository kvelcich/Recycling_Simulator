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

public class RCMGraph {
	private int data;
	private RCM rcm;
	public RCMGraph(int data, RCM rcm) {
		this.data = data;
		this.rcm = rcm;
	}
	public RCM getRCM() {
		return rcm;
	}
	public int getData() {
		return data;
	}
}


class DataManager {
	public final int WEIGHT = 0;
	public final int VALUE = 1;
	public final int ITEM = 2;
	public final int EMPTY = 3;
	
	public final int DAY = StatCalculator.DAY;
	public final int WEEK = StatCalculator.WEEK;
	public final int MONTH = StatCalculator.MONTH;
	public final int YEAR = StatCalculator.YEAR;
	
	private Map<Color, RCMGraph> values = new LinkedHashMap<Color, RCMGraph>();
	
	private Random randomGenerator = new Random();
	private Color[] salesColors;

	public void readDataFromFile(ArrayList<RCM> rcmList, int val, int timeFrame) {
		values.clear();
		for (RCM rcm: rcmList) {
			// the String to int conversion happens here
			int data = 0;
			switch (val) {
				case WEIGHT:
					data = (int)StatCalculator.getWeightInTimeFrame(rcm.getId(), timeFrame);
					break;
				case VALUE:
					data = StatCalculator.getMoneyInTimeFrame(rcm.getId(), timeFrame).toCents();
					break;
				case ITEM:
					data = StatCalculator.numItemInTimeFrame(rcm.getId(), timeFrame);
					break;
				case EMPTY:
					data = StatCalculator.getEmptyInTimeFrame(rcm.getId(), timeFrame);
					break;
			}
			
			int red = randomGenerator.nextInt(256);
			int green = randomGenerator.nextInt(256);
			int blue = randomGenerator.nextInt(256);
			Color randomColor = new Color(red, green, blue);
			
			values.put(randomColor, new RCMGraph(data, rcm));
		}
    }

	public Map<Color, RCMGraph> getData() {
		return values;
	}
}

/**
 * BarChart, creates a chart with sales data, and labels their year
 * @author Kevin Velcich
 */
class BarChart extends JPanel {
	//Setting up arrays of values
	private Map<Color, RCMGraph> bars = new LinkedHashMap<Color, RCMGraph>();
	private ArrayList<Integer> year;

	public BarChart(Map<Color, RCMGraph> data) {
		bars = data;
	}

	@Override
	protected void paintComponent(Graphics gp) {
		super.paintComponent(gp);
		// Cast the graphics objects to Graphics2D
		Graphics2D g = (Graphics2D) gp;
		// determine longest bar
		int max = Integer.MIN_VALUE;
		for (RCMGraph value : bars.values()) {
			max = Math.max(max, value.getData());
		}
		
		
		//WORK UP TO HERE
		
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

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(bars.size() * 10 + 2, 50);
	}
}
