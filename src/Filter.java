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

    public ArrayList<Food> VeganFilter(FileHandler fileHandler) {
        ArrayList<Food> VeganFoodList = new ArrayList<>();

        for (Consumable food : fileHandler.getConsumableList()) {
            if ()
        }
        return  VeganFoodList;
    }
}
