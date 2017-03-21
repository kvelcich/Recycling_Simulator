package recycling.simulation.gui;

import recycling.simulation.helper.Money;
import recycling.simulation.rcm.RCM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RCMGraphFrame extends JPanel implements ActionListener {
    private JButton weight, value, item, empty, day, week, month, year;
    private BarChart chart;
    private DataManager datamanager;
    private ArrayList<RCM> rcmList;
    private CardLayout cardLayout;
    private JPanel chartContainer;

    private int type = DataManager.WEIGHT;
    private int time = DataManager.YEAR;
    Container container;


    public RCMGraphFrame(ArrayList<RCM> rcmList) {
        setLayout(new BorderLayout());

        setSize(500, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        this.rcmList = rcmList;

        datamanager = new DataManager();
        datamanager.readData(rcmList, type, time);

        chartContainer = new JPanel();
        cardLayout = new CardLayout();
        chartContainer.setLayout(cardLayout);

        chart = new BarChart(datamanager.getData());
        chartContainer.add(chart, BorderLayout.CENTER);
        add(chartContainer);

        JLabel graphTypeLabel = new JLabel("Change Graph Param:");
        weight = new JButton("Weight");
        weight.setActionCommand("Weight");
        weight.addActionListener(this);
        value = new JButton("Value");
        value.setActionCommand("Value");
        value.addActionListener(this);
        item = new JButton("Items");
        item.setActionCommand("Items");
        item.addActionListener(this);
        empty = new JButton("Empty");
        empty.setActionCommand("Empty");
        empty.addActionListener(this);

        JPanel graphType = new JPanel();
        graphType.setLayout(new GridLayout(0, 1));
        graphType.add(graphTypeLabel);
        graphType.add(weight);
        graphType.add(value);
        graphType.add(item);
        graphType.add(empty);

        add(graphType, BorderLayout.WEST);

        JLabel timeLabel = new JLabel("Change Time Length: ");
        day = new JButton("Day");
        day.setActionCommand("Day");
        day.addActionListener(this);
        week = new JButton("Week");
        week.setActionCommand("Week");
        week.addActionListener(this);
        month = new JButton("Month");
        month.setActionCommand("Month");
        month.addActionListener(this);
        year = new JButton("Year");
        year.setActionCommand("Year");
        year.addActionListener(this);

        JPanel graphTime = new JPanel();
        graphTime.setLayout(new GridLayout(0, 1));
        graphTime.add(timeLabel);
        graphTime.add(day);
        graphTime.add(week);
        graphTime.add(month);
        graphTime.add(year);

        add(graphTime, BorderLayout.EAST);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Weight":
                type = DataManager.WEIGHT;
                break;
            case "Value":
                type = DataManager.VALUE;
                break;
            case "Items":
                type = DataManager.ITEM;
                break;
            case "Empty":
                type = DataManager.EMPTY;
                break;
            case "Day":
                time = DataManager.DAY;
                break;
            case "Week":
                time = DataManager.WEEK;
                break;
            case "Month":
                time = DataManager.MONTH;
                break;
            case "Year":
                time = DataManager.YEAR;
                break;
        }
        datamanager.readData(rcmList, type, time);
        chart = new BarChart(datamanager.getData());
        chartContainer.add(chart, "1");
        cardLayout.show(chartContainer, "1");
    }

    public void update() {
        datamanager.readData(rcmList, type, time);
        chart = new BarChart(datamanager.getData());
        chartContainer.add(chart, "1");
        cardLayout.show(chartContainer, "1");
    }
}