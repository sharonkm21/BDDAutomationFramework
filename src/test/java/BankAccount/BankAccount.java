package BankAccount;

public class BankAccount implements Account {
    private double balance;

    public BankAccount(double initialBalance) {
        //new line added
        this.balance = initialBalance;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
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
