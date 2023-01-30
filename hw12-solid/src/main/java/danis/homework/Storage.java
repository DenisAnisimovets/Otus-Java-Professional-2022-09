package danis.homework;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Storage {

    private final TreeMap<Banknote, Integer> banknotsInStorage = new TreeMap<>();

    public Storage() {
        for (Banknote banknote : Banknote.values()) {
            banknotsInStorage.put(banknote, 0);
        }
    }

    public void addMoney(Banknote banknote, int quantity) {
        if(!banknotsInStorage.containsKey(banknote)) {
            throw new RuntimeException("Нет такого номинала банкнот! Невозможно положить деньги!");
        } else {
            banknotsInStorage.compute(banknote, (nom, quant) -> quant + quantity);
        }
    }

    private void takeMoney(Banknote banknote, int quantity) {
        if(banknotsInStorage.containsKey(banknote)) {
            banknotsInStorage.compute(banknote, (nom, quant) -> quant - quantity);
        } else {
            throw new RuntimeException("Нет такого номинала банкнот! Невозможно забрать деньги!");
        }
    }

//    public int getBalance() {
//        return banknotsInStorage.entrySet().stream().mapToInt(it->it.getKey().getNominal()*it.getValue()).reduce(0, Integer::sum);
//    }

    public boolean take(int sum) {

        if(sum > StorageUtil.getBalance(this)) {
            throw new RuntimeException("В банкомате нет указанной суммы!");
        }

        Map<Banknote, Integer> banknotesForIssue = new HashMap<>();
        for (Banknote banknote : Banknote.values()) {
            banknotesForIssue.put(banknote, 0);
        }

        List<Integer> descendingBanknotes =
                banknotsInStorage.keySet().stream().map(Banknote::getNominal).collect(Collectors.toList());
        Collections.reverse(descendingBanknotes);
        boolean notEnoughMoney = true;
        for (int i = 0; i < descendingBanknotes.size() && notEnoughMoney; i ++) {
            int banknoteNominal = descendingBanknotes.get(i);
            int quantityFromATM = Math.min(sum / banknoteNominal, banknotsInStorage.get(Banknote.getBanknoteByNominal(banknoteNominal)));
            banknotesForIssue.put(Banknote.getBanknoteByNominal(banknoteNominal), quantityFromATM);
            sum-=quantityFromATM*banknoteNominal;
            if(sum==0) {
                notEnoughMoney = false;
            }
        }

        if(sum > 0 ) {
            throw new RuntimeException("Невозможно выдать деньги из банкомата! Не хватает купюр!");
        }

        for (Map.Entry<Banknote, Integer> entry : banknotesForIssue.entrySet()) {
            takeMoney(entry.getKey(), entry.getValue());
        }

        return true;
    }

    public Map<Integer, Integer> countBanknotes() {
        Map<Integer, Integer> countedBanknotes= new HashMap<>();
        banknotsInStorage.forEach((banknote, quantity)-> countedBanknotes.put(banknote.getNominal(), quantity));
        return countedBanknotes;
    }

//    public void showBanknotsInATM() {
//        System.out.println(banknotsInStorage);
//    }
}
