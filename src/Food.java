import java.util.Objects;

public class Food extends Consumable implements Comparable<Food>{
    TypeOfDiet typeOfDiet;
    Object dietType;

    @Override
    public int compareTo(Food other) {
        return Integer.compare(this.timeToPrepare, other.timeToPrepare);
    }

    enum TypeOfDiet {
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

    public String getName() {
        return name;
    }
    @Override
    public String getType() {
        if (dietType == TypeOfDiet.VEGAN) {
            typeOfDiet = Food.TypeOfDiet.VEGAN;
        } else if (dietType == TypeOfDiet.VEGETARIAN) {
            typeOfDiet = TypeOfDiet.VEGETARIAN;
        } else if (dietType == TypeOfDiet.MEAT) {
            typeOfDiet = TypeOfDiet.MEAT;
        }
        return Objects.toString(typeOfDiet);
    }
    public int getTimeToPrepare() {
        return timeToPrepare;
    }

}
