package recycling.simulation.gui;

import recycling.simulation.helper.StatCalculator;
import recycling.simulation.rcm.RCM;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.*;

public class RCMGraph {
	private double data;
	private RCM rcm;
	public RCMGraph(double data, RCM rcm) {
		this.data = data;
		this.rcm = rcm;
	}
	public RCM getRCM() {
		return rcm;
	}
	public double getData() {
		return data;
	}
}


class DataManager {
	public static final int WEIGHT = 0;
	public static final int VALUE = 1;
	public static final int ITEM = 2;
	public static final int EMPTY = 3;
	
	public static final int DAY = StatCalculator.DAY;
	public static final int WEEK = StatCalculator.WEEK;
	public static final int MONTH = StatCalculator.MONTH;
	public static final int YEAR = StatCalculator.YEAR;
	
	private Map<Color, RCMGraph> values = new LinkedHashMap<Color, RCMGraph>();
	
	private Random randomGenerator = new Random();
	private Color[] salesColors;

	public void readDataFromFile(ArrayList<RCM> rcmList, int val, int timeFrame) {
		values.clear();
		for (RCM rcm: rcmList) {
			// the String to int conversion happens here
			double data = 0;
			switch (val) {
				case WEIGHT:
					data = StatCalculator.getWeightInTimeFrame(rcm.getId(), timeFrame);
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
		double max = Integer.MIN_VALUE;
		for (RCMGraph value : bars.values()) {
			max = Math.max(max, value.getData());
		}

		// paint bars

		int height = (getHeight() / bars.size()) - 2;
		int y = 1;
		int i = 0;
        int ny = 0;

		for (Map.Entry<Color, RCMGraph> entry : bars.entrySet()) {
            double value = entry.getValue().getData();
            int width = (int)((getWidth() - 5) * ((double)value / max));
            g.setColor(entry.getKey());
            g.fillRect(0, y, width, height);
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.setColor(Color.BLACK);
            g.drawString("RCM - " + entry.getValue().getRCM().getId(), 10, y + height/2);
            y += (height + 2);
            i++;
        }
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(bars.size() * 10 + 2, 50);
	}
}