package danis.homework;

public class Starter {

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.showBanknotsInATM();
        atm.addMoney(Banknote.Nom_10, 5);
        atm.addMoney(Banknote.Nom_100, 5);
        atm.addMoney(Banknote.Nom_50, 10);
        atm.showBanknotsInATM();
        atm.take(20);
        atm.showBanknotsInATM();

    }
}
