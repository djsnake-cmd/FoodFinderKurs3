import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

public class Filter {

    public ArrayList<Consumable> FilterByTime(FileHandler fileHandler) {
        fileHandler.getConsumableList().sort(Comparator.comparingInt(Consumable::getTimeToPrepare));
        return fileHandler.getConsumableList();
    }

    public ArrayList<Consumable> VeganFilter(FileHandler fileHandler) {
        ArrayList<Consumable> VeganFoodList = new ArrayList<>();
        for (Consumable consumable : fileHandler.getConsumableList()) {
            if (consumable instanceof Food food) {
                if (food.typeOfDiet.equals(Food.TypeOfDiet.VEGAN)) {
                    VeganFoodList.add(food);
                }
            }
        }
        return  VeganFoodList;
    }
}
