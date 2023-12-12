import java.io.Serializable;

public class Consumable implements Serializable {
    String name;
    int timeToPrepare;

    public Consumable(String name, int timeToPrepare){
        this.name = name;
        this.timeToPrepare = timeToPrepare;

    }
    public String getName() {
        return name;
    }

    public int getTimeToPrepare() {
        return timeToPrepare;
    }

}
