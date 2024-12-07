package kodlar;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

class User {
    private String username;
    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;
    private ArrayList<Notification> notifications;

    public User(String username) {
        this.username = username;
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void createAccount(String accountNumber) {
        accounts.add(new Account(accountNumber, 0));
    }

    public Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        System.out.println("Hesap bulunamadı!");
        return null;
    }

    public synchronized void deposit(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            Transaction transaction = new Transaction("Yatırma", amount, accountNumber, java.time.LocalDateTime.now().toString());
            transactions.add(transaction);
            notifications.add(new Notification(transaction.getDetails()));
        }
    }

    public synchronized void withdraw(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.withdraw(amount);
            Transaction transaction = new Transaction("Çekme", amount, accountNumber, java.time.LocalDateTime.now().toString());
            transactions.add(transaction);
            notifications.add(new Notification(transaction.getDetails()));
        }
    }

    public synchronized void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = findAccount(fromAccountNumber);
        Account toAccount = findAccount(toAccountNumber);

        if (fromAccount != null && toAccount != null && fromAccount.getBalance() >= amount) {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            Transaction transaction = new Transaction("Transfer", amount, fromAccountNumber, java.time.LocalDateTime.now().toString());
            transactions.add(transaction);
            notifications.add(new Notification(transaction.getDetails()));
        } else {
            System.out.println("Transfer başarısız! Yetersiz bakiye veya geçersiz hesap numarası.");
        }
    }

    public void displayAllBalances() {
        for (Account account : accounts) {
            System.out.println(account.getAccountNumber() + " numaralı hesabın bakiyesi: " + account.getBalance() + " TL");
        }
    }

    public void displayTransactionHistory() {
        if (transactions.isEmpty()) {
            System.out.println("Henüz işlem yapılmadı.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction.getDetails());
            }
        }
    }

    public void displayNotifications() {
        for (Notification notification : notifications) {
            System.out.println(notification.getMessage());
        }
    }
}
