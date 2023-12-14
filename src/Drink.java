public class Drink extends Consumable {
    private boolean alcoholicOrNonAlcoholic;

    public Drink(String name, int timeToPrepare, boolean alcoholicOrNonAlcoholic) {
        super(name, timeToPrepare);
        this.alcoholicOrNonAlcoholic = alcoholicOrNonAlcoholic;
    }

    public boolean isAlcoholicOrNonAlcoholic() {
        return alcoholicOrNonAlcoholic;
    }
}
