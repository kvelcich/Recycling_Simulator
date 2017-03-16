import java.util.ArrayList;

public class RCM {
    private int id;
    private String location;
    private ArrayList<Recyclable> recyclableList;

    private Money totalOwed;
    private Money remaining;
    private final Money MAX_MONEY;

    private double capacity;
    private final double MAX_CAPACITY;
    
    //Session Variables
    private Recyclable lastRecycled;
    private double lastWeight;
    
    public RCM(int id, String location, double capacity, Money money) {
        this.id = id;
        this.location = location;
        recyclableList = new ArrayList<Recyclable>();

        totalOwed = new Money();
        remaining = money;
        MAX_MONEY = money.clone();

        this.capacity = 0;
        this.MAX_CAPACITY = capacity;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public Money getMoney() {
        return remaining;
    }

    public double getCapacity() {
        return capacity;
    }
    
    public ArrayList<Recyclable> getRecyclableList() {
    	return recyclableList;
    }

    public double getAvailableCapacity() {
        return MAX_CAPACITY - capacity;
    }

    public String getLastType() {
    	return lastRecycled.getType();
    }
    
    public double getLastWeight() {
    	return lastWeight;
    }
    
    public Money getLastPrice() {
    	return new Money(0, (int)(lastWeight * lastRecycled.getPricePerPound()));
    }
    
    public Money getTotalOwed() {
    	return totalOwed;
    }
    
    public void addRecyclableItem(Recyclable recyclable) {
    	recyclableList.add(recyclable);
    }
    
    public void setRecyclableList(ArrayList<Recyclable> recyclableList) {
        this.recyclableList = recyclableList;
    }

    public void recycleItem(Recyclable recyclable) {
    	//TODO: CHECK IF RECYCLABLE IS ACCEPTED
        double weight = recyclable.generateWeight();
        if (capacity + weight <= MAX_CAPACITY) {
        	lastRecycled = recyclable;
        	lastWeight = weight;
            capacity += weight;
            totalOwed.addTo(0, (int) (weight * recyclable.getPricePerPound()));
            StatCalculator.itemRecycled(id, recyclable, weight);
        } else {
            checkout();
        }
    }

    public boolean sufficientCapacity(double weight) {
    	return (weight + capacity <= MAX_CAPACITY);
    }
    
    public Receipt checkout() {
        if (remaining.sufficientFunds(totalOwed)) {
            remaining.subtract(totalOwed);
            return new Receipt(totalOwed, true);
        } else
            return new Receipt(totalOwed, false);
    }

    public void empty() {
        capacity = 0;
        StatCalculator.Empty(id);
    }

    public void restockMoney() {
        remaining = MAX_MONEY.clone();
    }
    
    /* Stat Calculator Functions */
    public int numItemPerTimeFrame(Recyclable recyclable, int timeFrame) {
    	return StatCalculator.numItemInTimeFrame(id, recyclable.getType(), timeFrame);
    }
    
    public double getCompleteWeight() {
    	return StatCalculator.getCompleteWeight(id);
    }
    
    public double getWeightInTimeFrame(int timeFrame) {
    	return StatCalculator.getWeightInTimeFrame(id, timeFrame);
    }

    public Money getTotalMoneyIssued() {
    	return StatCalculator.totalMoneyIssued(id);
    }
    
    public int getEmptyInTimeFrame(int timeFrame) {
    	return StatCalculator.getEmptyInTimeFrame(id, timeFrame);
    }
    
    public String getLastEmpty() {
    	return StatCalculator.getLastEmpty(id);
    }
}