package danis.homework;

public class Starter {

    public static void main(String[] args) {
        ATM atm = new ATM(new Storage());
        System.out.println(StorageUtil.getBalance(atm.getStorage()));
        atm.addMoney(Banknote.Nom_10, 5);
        atm.addMoney(Banknote.Nom_100, 5);
        atm.addMoney(Banknote.Nom_50, 10);
        System.out.println(StorageUtil.getBalance(atm.getStorage()));
        atm.take(20);
        System.out.println(StorageUtil.getBalance(atm.getStorage()));

    }
}
