package danis.homework;

public class Starter {

    public static void main(String[] args) {
        Storage storage = new Storage();
        System.out.println(storage.getCells());
        storage.addBanknote(10, 5);
        storage.addBanknote(100, 5);
        storage.addBanknote(50, 10);
        System.out.println(storage.getCells());
        storage.take(20);
        System.out.println(storage.getCells());

    }
}
