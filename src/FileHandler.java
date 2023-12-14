import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FileHandler {

    private static FileHandler instance;

    static Gson gson;

    private FileHandler(){
        gson = new Gson();
    }

    public static void writeListToFile(ArrayList<Consumable> consumableList) {
        try (FileWriter writer = new FileWriter("src/Consumable.ser")) {
            gson.toJson(consumableList,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Consumable> readListFromFile(){
        try(FileReader reader = new FileReader("src/Consumable.ser")){
            return gson.fromJson(reader, new TypeToken<List<Consumable>>(){}.getType());
        }catch (IOException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static FileHandler getInstance(){
        if(instance == null){
            return instance = new FileHandler();
        }
        return instance;
    }
    /*public static void main(String[] args) {
        Consumable c = new Consumable("carbonara",10);
        ArrayList<Consumable> list = new ArrayList<>();
        list.add(c);
        writeListToFile(list);
        //System.out.println(readObjektFromFile().get(1).name);

    }*/
}


