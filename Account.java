public class Account {
    private double balance;
    private String name;

    public Account(String name) {
        this.name = name;
        balance = 200;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public void addBalance(double money) {
        balance +=money;
    }

    public void subtractBalance(double money) {
        balance -=money;
    }


}
