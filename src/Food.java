public class Food extends Consumable implements Comparable<Food>{
    TypeOfDiet typeOfDiet;
    Object object;

    @Override
    public int compareTo(Food other) {
        return Integer.compare(this.timeToPrepare, other.timeToPrepare);
    }

    enum TypeOfDiet {
        VEGETARIAN,
        MEAT,
        VEGAN
    }
    public Food(String name, int timeToPrepare, Object object) {
        super(name, timeToPrepare);
        this.object = object;
    }
    public void getObject() {
        if (object == TypeOfDiet.VEGAN) {
            typeOfDiet = Food.TypeOfDiet.VEGAN;
        } else if (object == TypeOfDiet.VEGETARIAN) {
            typeOfDiet = TypeOfDiet.VEGETARIAN;
        } else if (object == TypeOfDiet.MEAT) {
            typeOfDiet = TypeOfDiet.MEAT;
        }
    }
    public int getTimeToPrepare() {
        return timeToPrepare;
    }

}
