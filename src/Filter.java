import javax.swing.*;
import java.util.*;
import java.util.concurrent.Callable;

public class Filter {

    public ArrayList<Consumable> FilterByTime(ArrayList<Consumable> consumableList) {
        consumableList.sort(Comparator.comparingInt(Consumable::getTimeToPrepare));
        return consumableList;
    }

    public ArrayList<Consumable> TypeOfFoodFilter(ArrayList<Consumable> consumableArrayList, Object getType) {
        ArrayList<Consumable> aSpecificTypeOfFood = new ArrayList<>();
        for (Consumable consumable : consumableArrayList) {
             if (Objects.toString(getType).equals(Objects.toString(consumable.getType()))) {
                 aSpecificTypeOfFood.add(consumable);
            }
        }
        return  aSpecificTypeOfFood;
    }
}
