public class Food extends Consumable implements Comparable<Food>{

    @Override
    public int compareTo(Food other) {
        return Integer.compare(this.timeToPrepare, other.timeToPrepare);
    }

    enum typeOfDiet {
        VEGETARIAN,
        MEAT,
        VEGAN
    }
    public Food(String name, int timeToPrepare) {
        super(name, timeToPrepare);
    }
    public int getTimeToPrepare() {
        return timeToPrepare;
    }

}
