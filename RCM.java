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


    public RCM(int id, String location, double capacity, Money money) {
        this.id = id;
        this.location = location;
        recyclableList = new ArrayList<Recyclable>();

        totalDue = new Money();
        remaining = money;
        MAX_MONEY = money.clone();

        this.capacity = 0;
        this.MAX_CAPACITY = capacity;
    }

    public void setRecyclableList(ArrayList<Recyclable> recyclableList) {
        this.recyclableList = recyclableList;
    }

    public void recycleItem(Recyclable recyclable) {
        double weight = recyclable.generate();
        if (capacity + weight <= MAX_CAPACITY) {
            capacity += weight;
            totalDue.addTo(0, (int) (weight * recyclable.getPricePerPound()));
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
}