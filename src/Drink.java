public class Drink extends Consumable{

    boolean alcoholOrNot;
    public Drink(String name, int timeToPrepare, boolean alcoholOrNot) {
        super(name, timeToPrepare);
        this.alcoholOrNot = alcoholOrNot;
    }

    public boolean getAlcoholOrNot() {
        return alcoholOrNot;
    }

    public int getTimeToPrepare() {
        return timeToPrepare;
    }
}
