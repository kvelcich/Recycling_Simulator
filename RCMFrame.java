import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RCMFrame extends JFrame {
    JButton weight, value, item, empty, day, week, month, year;

    public RCMFrame() {
        super("RCM Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        setLocationRelativeTo(null);
        setSize(500, 500);
        setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        RCM r1 = new RCM(1, "SJ", 10, new Money(50, 0));
        RCM r2 = new RCM(2, "SJ", 10, new Money(50, 0));
        RCM r3 = new RCM(3, "SJ", 10, new Money(50, 0));
        RCM r4 = new RCM(4, "SJ", 10, new Money(50, 0));
        RCM r5 = new RCM(5, "SJ", 10, new Money(50, 0));

        ArrayList<RCM> rcmList = new ArrayList<RCM>();
        rcmList.add(r1);
        rcmList.add(r2);
        rcmList.add(r3);
        rcmList.add(r4);
        rcmList.add(r5);

        DataManager datamanager = new DataManager();
        datamanager.readDataFromFile(rcmList, DataManager.ITEM, DataManager.YEAR);

        BarChart chart = new BarChart(datamanager.getData());
        chart.setVisible(true);
        container.add(chart, BorderLayout.CENTER);

        weight = new JButton("Weight");
        value = new JButton("Value");
        item = new JButton("Item");
        empty = new JButton("Empty");

        JPanel graphType = new JPanel();
        graphType.setLayout(new BoxLayout(graphType, BoxLayout.Y_AXIS));
        graphType.add(weight);
        graphType.add(value);
        graphType.add(item);
        graphType.add(empty);

        container.add(graphType, BorderLayout.WEST);

        day = new JButton("Day");
        week = new JButton("Week");
        month = new JButton("Month");
        year = new JButton("Year");

        JPanel graphTime = new JPanel();
        graphTime.setLayout(new BoxLayout(graphTime, BoxLayout.Y_AXIS));
        graphTime.add(day);
        graphTime.add(week);
        graphTime.add(month);
        graphTime.add(year);

        container.add(graphTime, BorderLayout.EAST);

        setVisible(true);
    }

    public static void main(String args[]) {
        new RCMFrame();
    }

 //   public void
}


class hello {
    public hello() {
        RCM r1 = new RCM(1, "SJ", 10, new Money(50, 0));
        RCM r2 = new RCM(2, "SJ", 10, new Money(50, 0));
        RCM r3 = new RCM(3, "SJ", 10, new Money(50, 0));
        RCM r4 = new RCM(4, "SJ", 10, new Money(50, 0));
        RCM r5 = new RCM(5, "SJ", 10, new Money(50, 0));


        ArrayList<RCM> rcmList = new ArrayList<RCM>();
        rcmList.add(r1);
        rcmList.add(r2);
        rcmList.add(r3);
        rcmList.add(r4);
        rcmList.add(r5);

        JFrame frame = new JFrame("Bar Chart");
        DataManager datamanager = new DataManager();
        datamanager.readDataFromFile(rcmList, DataManager.ITEM, DataManager.YEAR);

        BarChart chart = new BarChart(datamanager.getData());
        chart.setSize(500, 400);

        frame.setSize(600, 400);
        frame.getContentPane().add(chart);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}