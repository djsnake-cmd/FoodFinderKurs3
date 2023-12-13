public class Food extends Consumable implements Comparable<Food>{
    String type;
    TypeOfDiet typeOfDiet;

    @Override
    public int compareTo(Food other) {
        return Integer.compare(this.timeToPrepare, other.timeToPrepare);
    }

    enum TypeOfDiet {
        VEGETARIAN,
        MEAT,
        VEGAN
    }
    public Food(String name, int timeToPrepare, String type) {
        super(name, timeToPrepare);
        this.type = type;
        if (type.equals("VEGAN")) {
            typeOfDiet = Food.TypeOfDiet.VEGAN;
        } else if (type.equals("VEGETARIAN")) {
            typeOfDiet = TypeOfDiet.VEGETARIAN;
        } else if (type.equals("MEAT")) {
            typeOfDiet = TypeOfDiet.MEAT;
        }
    }
    public int getTimeToPrepare() {
        return timeToPrepare;
    }

}
