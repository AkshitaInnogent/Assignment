// Implementing SOLID Principles using a Bank Example

// S — Single Responsibility Principle (SRP)
// Each class handles one main job (BankAccount = base, SavingsAccount = savings logic, etc.)
// O — Open/Closed Principle (OCP)
// We can add new account types without changing existing code.
// L — Liskov Substitution Principle (LSP)
// Subclasses (SavingsAccount, CurrentAccount) can replace BankAccount safely.
// I — Interface Segregation Principle (ISP)
// Only interest-paying accounts implement Interest interface.
// D — Dependency Inversion Principle (DIP)
// Both account types depend on the abstraction (BankAccount), not concrete classes.


// Abstraction Layer (used by all account types)
abstract class BankAccount {
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    // Abstract methods — implemented differently by each account type
    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);

    public double getBalance() {
        return balance;
    }

    public void displayAccountInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Balance: $" + balance);
    }
}

// ISP: Only interest-paying accounts implement this interface
interface Interest {
    void calculateInterest();
}

// SavingsAccount — SRP (handles savings logic), OCP (extends without modifying)
// Also uses ISP by implementing Interest interface
class SavingsAccount extends BankAccount implements Interest {
    private double interestRate;

    public SavingsAccount(String accountNumber, String accountHolder, double balance, double interestRate) {
        super(accountNumber, accountHolder, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + ". New balance: $" + balance);
        } else {
            System.out.println("Deposit must be positive.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew $" + amount + ". New balance: $" + balance);
        } else {
            System.out.println("Invalid or insufficient funds.");
        }
    }

    // ISP: Only savings accounts calculate interest
    @Override
    public void calculateInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("Interest of $" + interest + " added. New balance: $" + balance);
    }
}

// CurrentAccount — SRP (handles current account logic), LSP (can replace BankAccount)
class CurrentAccount extends BankAccount {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String accountHolder, double balance, double overdraftLimit) {
        super(accountNumber, accountHolder, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + ". New balance: $" + balance);
        } else {
            System.out.println("Deposit must be positive.");
        }
    }

    // LSP: Uses the same withdraw logic type but allows overdraft
    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance + overdraftLimit) {
            balance -= amount;
            System.out.println("Withdrew $" + amount + ". New balance: $" + balance);
        } else {
            System.out.println("Invalid or exceeds overdraft limit.");
        }
    }
}

// Main class — demonstrates all SOLID principles together
public class SolidPrinciple {
    public static void main(String[] args) {

        // Savings Account (SRP + OCP + ISP)
        SavingsAccount savings = new SavingsAccount("SA123", "Alice", 1000, 0.02);
        savings.displayAccountInfo();
        savings.deposit(500);
        savings.withdraw(200);
        savings.calculateInterest(); // Uses Interest interface
        savings.displayAccountInfo();


        // Current Account (SRP + LSP)
        CurrentAccount current = new CurrentAccount("CA123", "Bob", 2000, 500);
        current.displayAccountInfo();
        current.deposit(300);
        current.withdraw(2500); // Uses overdraft feature
        current.displayAccountInfo();
    }
}
