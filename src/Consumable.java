public class Consumable {
    String name;
    int timeToPrepare;

    public Consumable(String name, int timeToPrepare){
        this.name = name;
        this.timeToPrepare = timeToPrepare;

    }

    public int getTimeToPrepare() {
        return timeToPrepare;
    }
}
