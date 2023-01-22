package danis.homework;

public class ATM {

    private final Storage storage = new Storage();

    public void addMoney(Banknote banknote, int quantity) {
        storage.addMoney(banknote, quantity);
    }

    public int getBalance() {
        return storage.getBalance();
    }

    public boolean take(int sum) {
        return storage.take(sum);
    }

    public void showBanknotsInATM() {
        storage.showBanknotsInATM();
    }
}
