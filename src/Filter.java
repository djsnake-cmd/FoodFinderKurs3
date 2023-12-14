import javax.swing.*;
import java.util.*;
import java.util.concurrent.Callable;

public class Filter {

    public ArrayList<Food> FilterByTime(ArrayList<Food> consumableList) {
        consumableList.sort(Comparator.comparingInt(Consumable::getTimeToPrepare));
        return consumableList;
    }

    public ArrayList<Food> TypeOfFoodFilter(ArrayList<Food> consumableArrayList, Object getType) {
        ArrayList<Food> aSpecificTypeOfFood = new ArrayList<>();
        for (Food consumable : consumableArrayList) {
             if (Objects.toString(getType).equals(Objects.toString(consumable.dietType))) {
                 aSpecificTypeOfFood.add(consumable);
            }
        }
        return  aSpecificTypeOfFood;
    }
}
