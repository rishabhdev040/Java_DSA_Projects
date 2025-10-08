import java.util.*;

// ===== Base Class =====
class Account {
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;
    protected List<String> transactionHistory;

    public Account(String accountNumber, String accountHolder, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Account created with deposit: " + initialDeposit);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    // Deposit
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction("Deposited: " + amount);
        } else {
            System.out.println("⚠️ Invalid deposit amount.");
        }
    }

    // Withdraw
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addTransaction("Withdrew: " + amount);
            return true;
        } else {
            System.out.println("⚠️ Invalid or insufficient funds.");
            return false;
        }
    }

    // Transfer
    public boolean transfer(Account target, double amount) {
        if (withdraw(amount)) {
            target.deposit(amount);
            addTransaction("Transferred " + amount + " to " + target.getAccountNumber());
            return true;
        }
        return false;
    }

    // Add transaction
    protected void addTransaction(String detail) {
        transactionHistory.add(new Date() + ": " + detail);
    }

    // Print statement
    public void printStatement() {
        System.out.println("\nTransaction history for " + accountHolder + " (" + accountNumber + "):");
        for (String transaction : transactionHistory) {
            System.out.println(" - " + transaction);
        }
        System.out.println("Current Balance: " + balance);
    }
}

// ===== Derived Class: Savings Account =====
class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, String accountHolder, double initialDeposit, double interestRate) {
        super(accountNumber, accountHolder, initialDeposit);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        if (balance > 0) {
            double interest = balance * interestRate / 100.0;
            balance += interest;
            addTransaction("Interest added: " + interest);
        }
    }
}

// ===== Derived Class: Loan Account =====
class LoanAccount extends Account {
    private double loanBalance;
    private double loanInterestRate;

    public LoanAccount(String accountNumber, String accountHolder, double initialDeposit, double loanInterestRate) {
        super(accountNumber, accountHolder, initialDeposit);
        this.loanBalance = 0;
        this.loanInterestRate = loanInterestRate;
    }

    // Apply for loan
    public void applyLoan(double amount) {
        if (amount > 0) {
            loanBalance += amount;
            balance += amount; // credited to balance
            addTransaction("Loan approved: " + amount);
        }
    }

    // Repay loan
    public void repayLoan(double amount) {
        if (amount > 0 && amount <= balance) {
            if (amount > loanBalance) {
                amount = loanBalance;
            }
            balance -= amount;
            loanBalance -= amount;
            addTransaction("Loan repayment: " + amount);
        } else {
            System.out.println("⚠️ Invalid repayment or insufficient balance.");
        }
    }

    // Apply loan interest yearly
    public void applyLoanInterest() {
        if (loanBalance > 0) {
            double interest = loanBalance * loanInterestRate / 100.0;
            loanBalance += interest;
            addTransaction("Loan interest added: " + interest);
        }
    }

    @Override
    public void printStatement() {
        super.printStatement();
        System.out.println("Outstanding Loan Balance: " + loanBalance);
    }
}

// ===== Bank Class =====
class Bank {
    private Map<String, Account> accounts;

    public Bank() {
        accounts = new HashMap<>();
    }

    public void createSavingsAccount(String accNum, String holder, double deposit, double interestRate) {
        if (!accounts.containsKey(accNum)) {
            accounts.put(accNum, new SavingsAccount(accNum, holder, deposit, interestRate));
            System.out.println("✅ Savings Account created successfully!");
        } else {
            System.out.println("⚠️ Account number already exists!");
        }
    }

    public void createLoanAccount(String accNum, String holder, double deposit, double loanRate) {
        if (!accounts.containsKey(accNum)) {
            accounts.put(accNum, new LoanAccount(accNum, holder, deposit, loanRate));
            System.out.println("✅ Loan Account created successfully!");
        } else {
            System.out.println("⚠️ Account number already exists!");
        }
    }

    public Account getAccount(String accNum) {
        return accounts.get(accNum);
    }

    public void displayAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
            return;
        }
        System.out.println("\n===== All Accounts =====");
        for (Account acc : accounts.values()) {
            System.out.println("- " + acc.getAccountNumber() + " | " + acc.getAccountHolder() + " | Balance: " + acc.getBalance());
        }
    }

    // Apply interest to all savings accounts
    public void applyInterestAll() {
        for (Account acc : accounts.values()) {
            if (acc instanceof SavingsAccount) {
                ((SavingsAccount) acc).applyInterest();
            }
        }
        System.out.println("✅ Interest applied to all savings accounts.");
    }

    // Apply loan interest to all loan accounts
    public void applyLoanInterestAll() {
        for (Account acc : accounts.values()) {
            if (acc instanceof LoanAccount) {
                ((LoanAccount) acc).applyLoanInterest();
            }
        }
        System.out.println("✅ Loan interest applied to all loan accounts.");
    }
}

// ===== Driver Class =====
public class BankingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank();

        while (true) {
            System.out.println("\n===== Banking System Menu =====");
            System.out.println("1. Create Savings Account");
            System.out.println("2. Create Loan Account");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Transfer Money");
            System.out.println("6. Apply Loan");
            System.out.println("7. Repay Loan");
            System.out.println("8. Apply Interest (Savings)");
            System.out.println("9. Apply Loan Interest");
            System.out.println("10. Print Statement");
            System.out.println("11. Display All Accounts");
            System.out.println("12. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    String accNum = sc.nextLine();
                    System.out.print("Enter account holder: ");
                    String holder = sc.nextLine();
                    System.out.print("Enter initial deposit: ");
                    double deposit = sc.nextDouble();
                    System.out.print("Enter interest rate (%): ");
                    double intRate = sc.nextDouble();
                    bank.createSavingsAccount(accNum, holder, deposit, intRate);
                    break;

                case 2:
                    System.out.print("Enter account number: ");
                    accNum = sc.nextLine();
                    System.out.print("Enter account holder: ");
                    holder = sc.nextLine();
                    System.out.print("Enter initial deposit: ");
                    deposit = sc.nextDouble();
                    System.out.print("Enter loan interest rate (%): ");
                    double loanRate = sc.nextDouble();
                    bank.createLoanAccount(accNum, holder, deposit, loanRate);
                    break;

                case 3:
                    System.out.print("Enter account number: ");
                    accNum = sc.nextLine();
                    Account acc1 = bank.getAccount(accNum);
                    if (acc1 != null) {
                        System.out.print("Enter amount to deposit: ");
                        acc1.deposit(sc.nextDouble());
                    } else {
                        System.out.println("⚠️ Account not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter account number: ");
                    accNum = sc.nextLine();
                    Account acc2 = bank.getAccount(accNum);
                    if (acc2 != null) {
                        System.out.print("Enter amount to withdraw: ");
                        acc2.withdraw(sc.nextDouble());
                    } else {
                        System.out.println("⚠️ Account not found.");
                    }
                    break;

                case 5:
                    System.out.print("Enter your account number: ");
                    String fromAcc = sc.nextLine();
                    System.out.print("Enter recipient account number: ");
                    String toAcc = sc.nextLine();
                    Account sender = bank.getAccount(fromAcc);
                    Account receiver = bank.getAccount(toAcc);
                    if (sender != null && receiver != null) {
                        System.out.print("Enter amount to transfer: ");
                        sender.transfer(receiver, sc.nextDouble());
                    } else {
                        System.out.println("⚠️ Invalid account(s).");
                    }
                    break;

                case 6:
                    System.out.print("Enter Loan Account number: ");
                    accNum = sc.nextLine();
                    Account acc3 = bank.getAccount(accNum);
                    if (acc3 instanceof LoanAccount) {
                        System.out.print("Enter loan amount: ");
                        ((LoanAccount) acc3).applyLoan(sc.nextDouble());
                    } else {
                        System.out.println("⚠️ Not a Loan Account.");
                    }
                    break;

                case 7:
                    System.out.print("Enter Loan Account number: ");
                    accNum = sc.nextLine();
                    Account acc4 = bank.getAccount(accNum);
                    if (acc4 instanceof LoanAccount) {
                        System.out.print("Enter repayment amount: ");
                        ((LoanAccount) acc4).repayLoan(sc.nextDouble());
                    } else {
                        System.out.println("⚠️ Not a Loan Account.");
                    }
                    break;

                case 8:
                    bank.applyInterestAll();
                    break;

                case 9:
                    bank.applyLoanInterestAll();
                    break;

                case 10:
                    System.out.print("Enter account number: ");
                    accNum = sc.nextLine();
                    Account acc5 = bank.getAccount(accNum);
                    if (acc5 != null) {
                        acc5.printStatement();
                    } else {
                        System.out.println("⚠️ Account not found.");
                    }
                    break;

                case 11:
                    bank.displayAccounts();
                    break;

                case 12:
                    System.out.println("✅ Exiting Banking System. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("⚠️ Invalid choice, try again.");
            }
        }
    }
}
