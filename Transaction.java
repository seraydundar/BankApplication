package kodlar;
class Transaction {
    private String type;
    private double amount;
    private String accountNumber;
    private String timestamp;

    public Transaction(String type, double amount, String accountNumber, String timestamp) {
        this.type = type;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return String.format("İşlem Türü: %s, Hesap: %s, Tutar: %.2f, Zaman: %s", 
                             type, accountNumber, amount, timestamp);
    }
}
