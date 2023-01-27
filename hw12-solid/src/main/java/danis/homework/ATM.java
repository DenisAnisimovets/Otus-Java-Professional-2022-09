package danis.homework;

public class ATM {

    private final Storage storage;

    public ATM(Storage storage) {
        this.storage = storage;
    }

    public void addMoney(Banknote banknote, int quantity) {
        storage.addMoney(banknote, quantity);
    }

    public int getBalance() {
        return StorageUtil.getBalance(storage);
    }

    public boolean take(int sum) {
        return storage.take(sum);
    }

    public Storage getStorage() {
        return storage;
    }
}
