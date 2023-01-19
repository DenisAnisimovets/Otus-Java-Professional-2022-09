package danis.homework;

public enum Banknotes {
    Nom_10(10),
    Nom_50 (50),
    Nom_100(100);

    private int nominal;

    Banknotes(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }
}

