import java.util.Random;

public class Recyclable {
    String type;
    double pricePerPound;
    double relativeWeight;

    public Recyclable(String type, double pricePerPound, double relativeWeight) {
        this.type = type;
        this.pricePerPound = pricePerPound;
        this.relativeWeight = relativeWeight;
    }

    public Money generate() {
        Random r = new Random();
        double randomVal = 0.5 + r.nextDouble();
        return new Money(0, (int)(randomVal*relativeWeight));
    }
}
