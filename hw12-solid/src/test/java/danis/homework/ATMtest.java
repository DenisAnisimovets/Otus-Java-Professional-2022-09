package danis.homework;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ATMtest {

    Storage storage = null;

    @BeforeEach
    void init() {
        storage = new Storage();
        storage.addBanknote(10, 5);
        storage.addBanknote(100, 5);
        storage.addBanknote(50, 10);
        System.out.println("Сейчас в банкомате: " + storage.getCells());
    }

    @AfterEach
    void destroy() {
        System.out.println("В банкомате осталось: " + storage.getCells());
        System.out.println("-----------------------------------------------");
    }

    @Test
    @DisplayName("Выдача корректной суммы")
    void takeCorrect() {
        System.out.println("Пытаемся выдвать 300");
        storage.take(160);
    }

    @Test
    @DisplayName("Выдача больше, чем есть в банкомате")
    void takeToMach() {
        System.out.println("Пытаемся выдать больше, чем есть в банкомате.");
        storage.take(1000000);
    }

    @Test
    @DisplayName("Выдача неразменной суммы, чем есть в банкомате")
    void takeNotCorrect() {
        System.out.println("Пытаемся выдать неразменную сумму.");
        storage.take(102);
    }
}
