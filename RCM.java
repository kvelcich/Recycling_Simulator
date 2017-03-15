import java.util.ArrayList;

public class RCM {
    private int id;
    private String location;
    private ArrayList<Recyclable> recyclableList;

    private Money totalDue;
    private Money remaining;
    private final Money MAX_MONEY;

    private double capacity;
    private final double MAX_CAPACITY;

    private StatCalculator statCalculator;


    public RCM(int id, String location, double capacity, Money money) {
        this.id = id;
        this.location = location;
        recyclableList = new ArrayList<Recyclable>();

        totalDue = new Money();
        remaining = money;
        MAX_MONEY = money.clone();

        this.capacity = 0;
        this.MAX_CAPACITY = capacity;

        statCalculator = new StatCalculator();
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

    public double getAvailableCapacity() {
        return MAX_CAPACITY - capacity;
    }

    public void setRecyclableList(ArrayList<Recyclable> recyclableList) {
        this.recyclableList = recyclableList;
    }

    public void recycleItem(Recyclable recyclable) {
        double weight = recyclable.generate();
        if (capacity + weight <= MAX_CAPACITY) {
            capacity += weight;
            totalDue.addTo(0, (int) (weight * recyclable.getPricePerPound()));
            statCalculator.addRecyclable(recyclable.getType(), weight);
        } else {
            checkout();
        }
    }

    public Receipt checkout() {
        if (remaining.sufficientFunds(totalDue)) {
            remaining.subtract(totalDue);
            return new Receipt(totalDue, true);
        } else
            return new Receipt(totalDue, false);
    }

    public void empty() {
        capacity = 0;
    }

    public void restockMoney() {
        remaining = MAX_MONEY.clone();
    }
}