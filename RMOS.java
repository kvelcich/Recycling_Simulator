import java.util.ArrayList;

public class RMOS {
    ArrayList<RCM> RCMList;
    ArrayList<Recyclable> recyclablesList;

    public RMOS() {
        RCMList = new ArrayList<RCM>();
        recyclablesList = new ArrayList<Recyclable>();
    }

    public void addRecyclable(Recyclable recyclable) {
        recyclablesList.add(recyclable);
    }
}
