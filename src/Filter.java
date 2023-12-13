import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Filter {
    Consumable[] consumableArray;
    Consumable typeOfConsume;
    Consumable consumable;

    public ArrayList<Consumable> FilterByTime(FileHandler fileHandler) {
        fileHandler.getConsumableList().sort(Comparator.comparingInt(Consumable::getTimeToPrepare));
        return fileHandler.getConsumableList();
    }
}
