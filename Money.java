public class Money {
    private int dollars;
    private int cents;

    /* Default constructor for Money. */
    Money() {
        dollars = 0;
        cents = 0;
    }

    /* Constructor for Money, where dollars and cents are given */
    Money(int dollars, int cents) {
        /* Converting cents to dollars */
        dollars += cents / 100;
        cents %= 100;
        this.dollars = dollars;
        this.cents = cents;
    }

    /* Sets dollars of object */
    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    /* Sets cents of object */
    public void setCents(int cents) {
        /* Converting cents to dollars if cents exceeds 100 cents */
        this.cents = cents;
        dollars += this.cents / 100;
        this.cents %= 100;
    }

    /* Returns the amount of dollars in object */
    public int getDollars() {
        return dollars;
    }

    /* Returns the amount of cents in object */
    public int getCents() {
        return cents;
    }

    /**
     *
     * @param dollars the amount of dollars to add to the Money object
     * @param cents the amount of cents to add to the Money object
     */
    public void addTo(int dollars, int cents) {
        this.cents += cents;
        this.dollars += this.cents / 100 + dollars;
        this.cents %= 100;
    }

    /**
     *
     * @param money Money object to be added with the called on object
     * @return a new Money object containing the sum of the called on object and the parameter object
     */
    public Money add(Money money) {
        return new Money(money.dollars + dollars, money.cents + cents);
    }

    /**
     *
     * @param money Money object to be subtracted with the called on object
     * @return a new Money object containing the difference of the called on object and the parameter object
     */
    public Money subtract (Money money) {
    	int cents = this.getDollars()*100 + this.getCents();
    	int subCents = money.getDollars() * 100 + money.getCents();
    	
    	return new Money(0, cents - subCents);
    }

    /* Returns a clone of the money object. */
    public Money clone() {
        return new Money(this.getDollars(), this.getCents());
    }

    /* Converts the Money object to a string, in order to print out */
    public String toString() {
        return "$" + dollars + "." + String.format("%02d", cents);
    }

    /* Checks if there is sufficient funds for passed in amount of money. */
    public boolean sufficientFunds(Money amount) {
        if (this.getDollars() > amount.getDollars())
            return true;
        else if (this.getDollars() == amount.getDollars() && this.getCents() >= amount.getCents())
            return true;
        return false;
    }
}

