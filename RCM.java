public class RCM {
    Money totalAmount;

    public RCM() {
        totalAmount = new Money();
    }

    public void recycleItem(Recyclable recyclable) {
        totalAmount = totalAmount.add(recyclable.generate());
    }
}