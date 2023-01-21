package danis.homework;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeMap;

public class ATM {
    private final TreeMap<Integer, Integer> banknotsInATM = new TreeMap<>();

    public ATM() {
        for (Banknote banknote : Banknote.values()) {
            banknotsInATM.put(banknote.getNominal(), 0);
        }
    }

    public void addMoney(Banknote banknote, int quantity) {
        if(!banknotsInATM.containsKey(banknote.getNominal())) {
            throw new RuntimeException("Нет такого номинала банкнот! Невозможно положить деньги!");
        } else {
            banknotsInATM.compute(banknote.getNominal(), (nom, quant) -> quant + quantity);
        }
    }

    private void takeMoney(Banknote banknote, int quantity) {
        if(banknotsInATM.containsKey(banknote.getNominal())) {
            banknotsInATM.compute(banknote.getNominal(), (nom, quant) -> quant - quantity);
        } else {
            throw new RuntimeException("Нет такого номинала банкнот! Невозможно забрать деньги!");
        }
    }

    public int getBalance() {
        return banknotsInATM.entrySet().stream().mapToInt(it->it.getKey()*it.getValue()).reduce(0, Integer::sum);
    }

    public boolean take(int sum) {

        if(sum > getBalance()) {
            throw new RuntimeException("В банкомате нет указанной суммы!");
        }

        Map<Banknote, Integer> banknotesForIssue = new HashMap<>();
        for (Banknote banknote : Banknote.values()) {
            banknotesForIssue.put(banknote, 0);
        }

        NavigableSet<Integer> descendingBanknotes = new TreeMap<>(banknotsInATM).descendingKeySet();
        boolean notEnoughMoney = true;
        while (!descendingBanknotes.isEmpty() && notEnoughMoney){
            int banknoteNominal = descendingBanknotes.pollFirst();
            int quantityFromATM = Math.min(sum / banknoteNominal, banknotsInATM.get(banknoteNominal));
            banknotesForIssue.put(Banknote.getBanknoteByNominal(banknoteNominal), quantityFromATM);
            sum-=quantityFromATM*banknoteNominal;
            if(sum==0) {
                notEnoughMoney = false;
            }
        }

        if(sum > 0 ) {
            throw new RuntimeException("Невозможно выдать деньги из банкомата! Не хватает купюр!");
        }

        for (Map.Entry<Banknote, Integer> entry : banknotesForIssue.entrySet())
            takeMoney(entry.getKey(), entry.getValue());
        return true;
    }

    public void showBanknotsInATM() {
        System.out.println(banknotsInATM);
    }
}
