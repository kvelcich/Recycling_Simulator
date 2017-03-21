package recycling.simulation.helper;

import recycling.simulation.rcm.rcmData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class StatCalc {
    public static final int DAY = 10;
    public static final int WEEK = 70;
    public static final int MONTH = 300;
    public static final int YEAR = 3600;

    private ArrayList<rcmData> rcmDataList;
    private ArrayList<String> emptiedList;

    public StatCalc() {
        rcmDataList = new ArrayList<rcmData>();
        emptiedList = new ArrayList<String>();
    }

    public void itemRecycled(Recyclable recyclable, double weight) {
        DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
        Calendar calobj = Calendar.getInstance();
        String timestamp = df.format(calobj.getTime());

        rcmDataList.add(new rcmData(timestamp, recyclable.getType(), weight, recyclable.getPricePerPound()));
    }

    public void empty() {
        DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
        Calendar calobj = Calendar.getInstance();
        String timestamp = df.format(calobj.getTime());

        emptiedList.add(timestamp);
    }

    public int numSpecItemInTimeFrame(String type, int timeFrame) {
        int counter = 0;

        for (rcmData RCMData: rcmDataList) {
            DateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
            Calendar calobj = Calendar.getInstance();
            String timestamp = format.format(calobj.getTime());

            Date d1 = null;
            Date d2 = null;
            try {
                d1 = format.parse(RCMData.getTime());
                d2 = format.parse(timestamp);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long diff = d2.getTime() - d1.getTime();

            //Given in seconds
            long timeDiff = TimeUnit.MILLISECONDS.toSeconds(diff);
            if (timeDiff <= timeFrame && RCMData.getType().equals(type))
                counter++;
        }
        return counter;
    }

    public int numItemInTimeFrame(int timeFrame) {
        int counter = 0;

        for (rcmData RCMData: rcmDataList) {
            DateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
            Calendar calobj = Calendar.getInstance();
            String timestamp = format.format(calobj.getTime());

            Date d1 = null;
            Date d2 = null;
            try {
                d1 = format.parse(RCMData.getTime());
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
        return counter;
    }

    public double getCompleteWeight() {
        double weight = 0;
        for (rcmData RCMData: rcmDataList)
            weight += RCMData.getWeight();
        return weight;
    }

    public double getWeightInTimeFrame(int timeFrame) {
        double weight = 0;

        for (rcmData RCMData: rcmDataList) {
            DateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
            Calendar calobj = Calendar.getInstance();
            String timestamp = format.format(calobj.getTime());

            Date d1 = null;
            Date d2 = null;
            try {
                d1 = format.parse(RCMData.getTime());
                d2 = format.parse(timestamp);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long diff = d2.getTime() - d1.getTime();
            //Given in seconds
            long timeDiff = TimeUnit.MILLISECONDS.toSeconds(diff);
            if (timeDiff <= timeFrame)
                weight += RCMData.getWeight();
        }
        return weight;
    }

    public Money totalMoneyIssued() {
         double total = 0;
        for (rcmData RCMData: rcmDataList)
            total += RCMData.getPrice() * RCMData.getWeight();
        return new Money(0, (int)total);
    }

    public Money getMoneyInTimeFrame(int timeFrame) {
        double total = 0;

        for (rcmData RCMData: rcmDataList) {
            DateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
            Calendar calobj = Calendar.getInstance();
            String timestamp = format.format(calobj.getTime());

            Date d1 = null;
            Date d2 = null;
            try {
                d1 = format.parse(RCMData.getTime());
                d2 = format.parse(timestamp);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long diff = d2.getTime() - d1.getTime();
            //Given in seconds
            long timeDiff = TimeUnit.MILLISECONDS.toSeconds(diff);
            if (timeDiff <= timeFrame)
               total += RCMData.getWeight() * RCMData.getPrice();
        }
        return new Money(0, (int)total);
    }

    public int getEmptyInTimeFrame(int timeFrame) {
        int counter = 0;

        for (String emptyTime: emptiedList) {
            DateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
            Calendar calobj = Calendar.getInstance();
            String timestamp = format.format(calobj.getTime());

            Date d1 = null;
            Date d2 = null;
            try {
                d1 = format.parse(emptyTime);
                d2 = format.parse(timestamp);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long diff = d2.getTime() - d1.getTime();

            long timeDiff = TimeUnit.MILLISECONDS.toSeconds(diff);
            if (timeDiff <= timeFrame)
                counter++;
        }
        return counter;
    }

    public String getLastEmpty() {
        if (emptiedList.size() > 0)
            return emptiedList.get(emptiedList.size() - 1);
        return "";
    }

    public int getNumItems() {
        return rcmDataList.size();
    }
}