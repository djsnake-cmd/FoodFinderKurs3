import java.util.ArrayList;

public class FoodManager {
    private ArrayList<Food> consumableArrayList;
    private FileHandler fileHandler;

    public FoodManager() {
        fileHandler = FileHandler.getInstance();
        consumableArrayList = fileHandler.readListFromFile();
    }

    public void addFood(Food food){
        consumableArrayList.add(food);
        fileHandler.writeListToFile(consumableArrayList);
    }

    public void removeFood(Food food){
        consumableArrayList.remove(food);
        fileHandler.writeListToFile(consumableArrayList);
    }


}
