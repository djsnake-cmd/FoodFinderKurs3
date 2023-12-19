import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class FoodManager {
    private ArrayList<Food> consumableArrayList;
    private FileHandler fileHandler;
    Filter filter;

    public FoodManager() {
        fileHandler = FileHandler.getInstance();
        consumableArrayList = fileHandler.readListFromFile();
        filter = new Filter();
    }

    public ArrayList<Food> getList(){
        Collections.shuffle(consumableArrayList);
        return consumableArrayList;
    }

    public void refreshList(){
        consumableArrayList = fileHandler.readListFromFile();
        Collections.shuffle(consumableArrayList);
    }

    public ArrayList<Food> refreshAndSortByTime(int maxTime){
        consumableArrayList = fileHandler.readListFromFile();
        return consumableArrayList = filter.filterByMaxTime(consumableArrayList,maxTime);
    }

    public ArrayList<Food> getFilteredFoodByType(Food.TypeOfFood type){
        return filter.TypeOfFoodFilter(consumableArrayList, type);
    }

    public Food getFood(){
        return consumableArrayList.get(0);
    }

    public String getFirstFoodSearchURL() {
        if (!consumableArrayList.isEmpty()) {
            Food firstFood = consumableArrayList.get(0);
            String query = firstFood.getName() + " recept";
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            return "https://www.google.com/search?q=" + encodedQuery;
        }
        return null;
    }

    public String getAllFoods(Food.TypeOfFood type) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Food> filteredList = getFilteredFoodByType(type);
        for (Food food : filteredList) {
            sb.append(food.getName())
                    .append("\nTillagningstid: ")
                    .append(food.getTimeToPrepare())
                    .append(" minuter\n\n");
        }
        return sb.toString();
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
