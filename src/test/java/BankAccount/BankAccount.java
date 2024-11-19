package BankAccount;

public class BankAccount implements Account {
    private double balance;
    String nameCustomer;
    public static long accountNumber = 14;

    public BankAccount(double initialBalance,String nameCustomer) {
        //new line added
        this.balance = initialBalance;
        this.nameCustomer = nameCustomer;
    }

    @Override
    public void deposit(double amount ) {
        int balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        balance -= amount;
    }

    @Override
    public double getBalance() {
        return balance;
    }
}
