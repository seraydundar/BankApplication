package kodlar;

class Notification {
    private String message;
    private boolean isRead;

    public Notification(String message) {
        this.message = message;
        this.isRead = false;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public String getMessage() {
        return message + (isRead ? " (Okundu)" : " (Okunmadı)");
    }
}
