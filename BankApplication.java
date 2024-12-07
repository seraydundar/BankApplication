package kodlar;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class BankApplication {
    private static final Logger logger = Logger.getLogger(BankApplication.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<User> users = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10); // Thread pool tanımlandı

        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Banka Uygulaması ---");
            System.out.println("1. Yeni Kullanıcı Oluştur");
            System.out.println("2. Mevcut Kullanıcı ile Giriş Yap");
            System.out.println("3. Çıkış");
            System.out.print("Bir seçenek seçiniz: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Kullanıcı adını giriniz: ");
                    String username = scanner.next();
                    users.add(new User(username));
                    System.out.println(username + " adlı kullanıcı oluşturuldu.");
                    break;

                case 2:
                    System.out.print("Kullanıcı adını giriniz: ");
                    String userLogin = scanner.next();
                    User currentUser = findUser(users, userLogin);
                    if (currentUser != null) {
                        userMenu(scanner, currentUser, executorService);
                    } else {
                        System.out.println("Kullanıcı bulunamadı!");
                    }
                    break;

                case 3:
                    exit = true;
                    System.out.println("Çıkış yapılıyor...");
                    executorService.shutdown(); // Executor'ı kapat
                    break;

                default:
                    System.out.println("Geçersiz seçenek! Lütfen tekrar deneyin.");
            }
        }
        scanner.close();
    }

    private static User findUser(ArrayList<User> users, String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private static void userMenu(Scanner scanner, User user, ExecutorService executorService) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- " + user.getUsername() + " için Menü ---");
            System.out.println("1. Yeni Hesap Oluştur");
            System.out.println("2. Para Yatırma");
            System.out.println("3. Para Çekme");
            System.out.println("4. Para Transferi");
            System.out.println("5. Bakiye Görüntüleme");
            System.out.println("6. İşlem Geçmişini Görüntüle");
            System.out.println("7. Bildirimleri Görüntüle");
            System.out.println("8. Çıkış");
            System.out.print("Bir seçenek seçiniz: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Yeni hesap numarasını giriniz: ");
                    String accountNumber = scanner.next();
                    user.createAccount(accountNumber);
                    System.out.println(accountNumber + " numaralı hesap oluşturuldu.");
                    break;

                case 2:
                    System.out.print("Para yatırmak istediğiniz hesap numarasını giriniz: ");
                    String depositAccount = scanner.next();
                    System.out.print("Yatırılacak tutar: ");
                    double depositAmount = scanner.nextDouble();
                    executorService.execute(() -> user.deposit(depositAccount, depositAmount));
                    break;

                case 3:
                    System.out.print("Para çekmek istediğiniz hesap numarasını giriniz: ");
                    String withdrawAccount = scanner.next();
                    System.out.print("Çekilecek tutar: ");
                    double withdrawAmount = scanner.nextDouble();
                    executorService.execute(() -> user.withdraw(withdrawAccount, withdrawAmount));
                    break;

                case 4:
                    System.out.print("Gönderen hesap numarasını giriniz: ");
                    String fromAccount = scanner.next();
                    System.out.print("Alıcı hesap numarasını giriniz: ");
                    String toAccount = scanner.next();
                    System.out.print("Transfer edilecek tutar: ");
                    double transferAmount = scanner.nextDouble();
                    executorService.execute(() -> user.transfer(fromAccount, toAccount, transferAmount));
                    break;

                case 5:
                    user.displayAllBalances();
                    break;

                case 6:
                    user.displayTransactionHistory();
                    break;

                case 7:
                    user.displayNotifications();
                    break;

                case 8:
                    back = true;
                    break;

                default:
                    System.out.println("Geçersiz seçenek! Lütfen tekrar deneyin.");
            }
        }
    }
}

