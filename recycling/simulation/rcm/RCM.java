package recycling.simulation.rcm;

import recycling.simulation.helper.Money;
import recycling.simulation.helper.Recyclable;
import recycling.simulation.helper.StatCalc;

import java.util.ArrayList;

public class RCM {
    StatCalc statCalc;

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
        statCalc = new StatCalc();

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

    public double getMAX_CAPACITY() {
        return MAX_CAPACITY;
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

    public Recyclable getRecyclable(String type) {
        for (Recyclable recyclable: recyclableList)
            if (recyclable.getType().equals(type))
                return recyclable;
        return null;
    }

    public void pay() {
        if (getMoney().sufficientFunds(totalOwed)) {
            remaining = remaining.subtract(totalOwed);
        }
        totalOwed.setDollars(0);
        totalOwed.setCents(0);
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

    public boolean recycleItem(Recyclable recyclable) {
        double weight = recyclable.generateWeight();
        if (sufficientCapacity(weight)) {
        	lastRecycled = recyclable;
        	lastWeight = weight;
            addCapacity(weight);
            totalOwed.addTo(0, (int) (weight * recyclable.getPricePerPound()));
            statCalc.itemRecycled(recyclable, weight);
            return true;
        } else {
            return false;
        }
    }

    public boolean sufficientCapacity(double weight) {
    	return (weight + capacity <= MAX_CAPACITY);
    }
    
    public void addCapacity(double weight) {
    	if (sufficientCapacity(weight))
    		capacity += weight;
    }
    
    public void checkout() {
        totalOwed.setDollars(0);
        totalOwed.setCents(0);
    }

    public void empty() {
        capacity = 0;
        statCalc.empty();
    }

    public void restockMoney() {
        remaining = MAX_MONEY.clone();
    }

    public StatCalc stats() {
        return statCalc;
    }
}