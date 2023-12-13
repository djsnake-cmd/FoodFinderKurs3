public class Drink extends Consumable{

    boolean alcoholicOrNonAlcoholic;
    public Drink(String name, int timeToPrepare, boolean alcoholicOrNonAlcoholic) {
        super(name, timeToPrepare);
        this.alcoholicOrNonAlcoholic = alcoholicOrNonAlcoholic;
    }
    public boolean getAlcoholOrNot() {
        return alcoholicOrNonAlcoholic;
    }
    public int getTimeToPrepare() {
        return timeToPrepare;
    }
}
