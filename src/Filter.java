import javax.swing.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class Filter {


    public ArrayList<Food> filterByMaxTime(ArrayList<Food> consumableList, int maxTime) {
        return consumableList.stream()
                .filter(food -> food.getTimeToPrepare() < maxTime)
                .collect(Collectors.toCollection(ArrayList::new));
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
