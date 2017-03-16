import java.util.Random;

public class Recyclable {
    private String type;
    private double pricePerPound;
    private double relativeWeight;

    public Recyclable(String type, double pricePerPound, double relativeWeight) {
        this.type = type;
        this.pricePerPound = pricePerPound;
        this.relativeWeight = relativeWeight;
    }

    public double generateWeight() {
        Random r = new Random();
        double randomVal = 0.5 + r.nextDouble();
        return randomVal*relativeWeight;
    }

    public double getPricePerPound() {
        return pricePerPound;
    }

    public void setPricePerPound(double pricePerPound) {
        this.pricePerPound = pricePerPound;
    }

    public String getType() {
        return type;
    }
}