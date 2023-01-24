package danis.homework;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ATMtest {

    ATM atm = null;

    @BeforeEach
    void init() {
        atm = new ATM(new Storage());
        atm.addMoney(Banknote.Nom_10, 5);
        atm.addMoney(Banknote.Nom_100, 5);
        atm.addMoney(Banknote.Nom_50, 10);
        System.out.print("Сейчас в банкомате: ");
        System.out.println(StorageUtil.getBalance(atm.getStorage()));
    }

    @AfterEach
    void destroy() {
        System.out.print("В банкомате осталось: ");
        System.out.println(StorageUtil.getBalance(atm.getStorage()));
        System.out.println("-----------------------------------------------");
    }

    @Test
    @DisplayName("Выдача корректной суммы")
    void takeCorrect() {
        System.out.println("Пытаемся выдать 160");
        atm.take(160);
    }

    @Test
    @DisplayName("Выдача больше, чем есть в банкомате. Выбрасывается исключение.")
    void takeToMach() {
        System.out.println("Пытаемся выдать больше, чем есть в банкомате. Выбрасывается исключение.");
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() ->
                atm.take(1000000));
    }

    @Test
    @DisplayName("Выдача неразменной суммы, чем есть в банкомате. Выбрасывается исключение.")
    void takeNotCorrect() {
        System.out.println("Пытаемся выдать неразменную сумму. Выбрасывается исключение.");
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() ->
                atm.take(102));

    }
}
