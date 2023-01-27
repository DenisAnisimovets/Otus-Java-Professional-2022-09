package danis.homework;

public final class StorageUtil {
    private StorageUtil() {
    }

    public static int getBalance(Storage storage) {
        final int[] balance = {0};
        storage.countBanknotes().forEach((nominal, quantity) -> balance[0] = balance[0] + nominal * quantity);
        return balance[0];
    }

    public static void showBanknotsInStorage(Storage storage) {
        System.out.println(storage.countBanknotes());
    }
}
