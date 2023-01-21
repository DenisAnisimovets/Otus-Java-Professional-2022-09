package danis.homework;

public enum Banknote {
    Nom_10(10),
    Nom_50 (50),
    Nom_100(100);

    private int nominal;

    Banknote(int nominal) {
        this.nominal = nominal;
    }

    public int getNominal() {
        return nominal;
    }

    public static Banknote getBanknoteByNominal(int nominal) {
        return Banknote.valueOf("Nom_" + nominal);
    }
}

