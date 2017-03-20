package recycling.simulation.helper;

public class dataMember {
    private int id;
    private String time;
    private String type;
    private double weight;
    private double price;

    public dataMember(int id, String time, String type, double weight, double price) {
        this.id = id;
        this.time = time;
        this.type = type;
        this.weight = weight;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public double getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }
}
