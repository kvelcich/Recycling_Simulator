package recycling.simulation.rcm;

public class rcmData {
    private String time;
    private String type;
    private double weight;
    private double price;

    public rcmData(String time, String type, double weight, double price) {
        this.time = time;
        this.type = type;
        this.weight = weight;
        this.price = price;
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
