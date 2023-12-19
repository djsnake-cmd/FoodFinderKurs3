import java.util.Objects;

public class Food extends Consumable implements Comparable<Food>{
    TypeOfFood typeOfFood;
    Object dietType;

    @Override
    public int compareTo(Food other) {
        return Integer.compare(this.timeToPrepare, other.timeToPrepare);
    }

    enum TypeOfFood {
        ALLA,
        VEGETARIAN,
        MEAT,
        VEGAN
    }
    public Food(String name, int timeToPrepare) {
        super(name, timeToPrepare);
    }

    @Override
    public void setType(Object type) {
        super.setType(type);
        dietType = type;
    }
    public int getTimeToPrepare() {
        return timeToPrepare;
    }

}
