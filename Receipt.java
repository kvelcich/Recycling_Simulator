public class Receipt {
    private Money total;
    private boolean paid;

    public Receipt(Money money, boolean paid) {
        this.total = money;
        this.paid = paid;
    }
}
