import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FileHandler {

    public static void writeListToFile(ArrayList<Consumable> consumableList) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("src/Consumable.ser")) {
            gson.toJson(consumableList,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Consumable> readListFromFile(){
        Gson gson = new Gson();
        try(FileReader reader = new FileReader("src/Consumable.ser")){
            return gson.fromJson(reader, new TypeToken<List<Consumable>>(){}.getType());
        }catch (IOException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    public static void main(String[] args) {
        Consumable c = new Consumable("carbonara",10);
        ArrayList<Consumable> list = new ArrayList<>();
        list.add(c);
        writeListToFile(list);
        //System.out.println(readObjektFromFile().get(1).name);

    }
}


