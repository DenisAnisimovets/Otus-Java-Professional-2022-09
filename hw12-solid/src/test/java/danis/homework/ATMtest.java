package danis.homework;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ATMtest {

    ATM atm = null;

    @BeforeEach
    void init() {
        atm = new ATM();
        atm.addMoney(Banknote.Nom_10, 5);
        atm.addMoney(Banknote.Nom_100, 5);
        atm.addMoney(Banknote.Nom_50, 10);
        System.out.print("Сейчас в банкомате: ");
        atm.showBanknotsInATM();
    }

    @AfterEach
    void destroy() {
        System.out.print("В банкомате осталось: ");
        atm.showBanknotsInATM();
        System.out.println("-----------------------------------------------");
    }

    @Test
    @DisplayName("Выдача корректной суммы")
    void takeCorrect() {
        System.out.println("Пытаемся выдать 300");
        atm.take(160);
    }

    @Test
    @DisplayName("Выдача больше, чем есть в банкомате")
    void takeToMach() {
        System.out.println("Пытаемся выдать больше, чем есть в банкомате.");
        atm.take(1000000);
    }

    @Test
    @DisplayName("Выдача неразменной суммы, чем есть в банкомате")
    void takeNotCorrect() {
        System.out.println("Пытаемся выдать неразменную сумму.");
        atm.take(102);
    }
}
