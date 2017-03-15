import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class StatCalculator {
    HashMap<String, Integer> totalRecycled;
    String lastEmptied;
    double totalWeightRecycled;

    public StatCalculator() {
        totalRecycled = new HashMap<String, Integer>();
        lastEmptied = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());
        totalWeightRecycled = 0;
    }

    public void addRecyclable(String type, double weight) {
        totalWeightRecycled += weight;
        if (totalRecycled.containsKey(type))
            totalRecycled.replace(type, totalRecycled.get(type) + 1);
        else
            totalRecycled.put(type, 1);
    }
}
