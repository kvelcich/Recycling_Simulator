import java.util.ArrayList;

public class RMOS {
    private ArrayList<RCM> RCMList;
    private ArrayList<Recyclable> recyclablesList;

    public RMOS() {
        RCMList = new ArrayList<RCM>();
        recyclablesList = new ArrayList<Recyclable>();
    }

    public void addRCM(RCM RCMMachine) {
        RCMList.add(RCMMachine);
    }

    public void addRecyclable(Recyclable recyclable) {
        recyclablesList.add(recyclable);
    }

    public void deleteRecyclable(String type) {
        for (int i = 0; i < recyclablesList.size(); i++)
            if (recyclablesList.get(i).getType().equals(type))
                recyclablesList.remove(i);
    }

    public void setRecyclablePrice(String type, double pricePerPound) {
        for (Recyclable recyclable : recyclablesList)
            if (recyclable.getType().equals(type))
                recyclable.setPricePerPound(pricePerPound);
    }

    private RCM getRCM(int id) throws Exception {
        for (RCM RCMMachine : RCMList)
            if (RCMMachine.getId() == id)
                return RCMMachine;
        throw new Exception("RCM not found.");
    }

    public String getRCMLocation(int id) throws Exception {
        return getRCM(id).getLocation();
    }

    public Money getRCMMoney(int id) throws Exception {
        return getRCM(id).getMoney();
    }

    public double getCapacity(int id) throws Exception {
        return getRCM(id).getCapacity();
    }

    public double getAvailableCapacity(int id) throws Exception {
        return getRCM(id).getAvailableCapacity();
    }
}
