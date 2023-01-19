package danis.homework;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summarizingInt;

public class Storage {
    private final TreeMap<Integer, Integer> cells = new TreeMap<>();

    public Storage() {
        for (Banknotes value : Banknotes.values()) {
            cells.put(value.getNominal(), 0);
        }
    }

    public void addBanknote(int nominal, int quantity) {
        if(!cells.containsKey(nominal)) {
            throw new RuntimeException("Нет такого номинала банкнот! Невозможно положить деньги!");
        } else {
            cells.compute(nominal, (nom, quant) -> quant + quantity);
        }
    }

    private void takeBanknote(int nominal, int quantity) {
        if(cells.containsKey(nominal)) {
            cells.compute(nominal, (nom, quant) -> quant - quantity);
        } else {
            throw new RuntimeException("Нет такого номинала банкнот! Невозможно забрать деньги!");
        }
    }

    public int getBalance() {
        return cells.entrySet().stream().mapToInt(it->it.getKey()*it.getValue()).reduce(0, Integer::sum);
    }

    public boolean take(int sum) {

        if(sum > getBalance()) {
            throw new RuntimeException("В банкомате нет указанной суммы!");
        }

        Map<Integer, Integer> banknotesForIssue = new HashMap<>();
        for (Banknotes value : Banknotes.values()) {
            banknotesForIssue.put(value.getNominal(), 0);
        }

        NavigableSet<Integer> descendingBanknotes = new TreeMap<>(cells).descendingKeySet();
        boolean notEnoughMoney = true;
        while (!descendingBanknotes.isEmpty() && notEnoughMoney){
            int nominalInCell = descendingBanknotes.pollFirst();
            int quantityFromCell = Math.min(sum / nominalInCell, cells.get(nominalInCell));
            banknotesForIssue.put(nominalInCell, quantityFromCell);
            sum-=quantityFromCell*nominalInCell;
            if(sum==0) {
                notEnoughMoney = false;
            }
        }

        if(sum > 0 ) {
            throw new RuntimeException("Невозможно выдать деньги из банкомата! Не хватает купюр!");
        }

        banknotesForIssue.forEach(this::takeBanknote);
        return true;
    }

    public Map<Integer, Integer> getCells() {
        return cells;
    }
}
