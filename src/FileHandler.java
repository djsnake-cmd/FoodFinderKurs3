import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    BufferedReader br;
    BufferedWriter bw;
    String filePath = "src";
    ArrayList<Consumable> consumableList = new ArrayList<>();
    String[] toSetNameAndTime;
    String consumableType;
    Consumable consumableClass;
    FileHandler(String consumable) {
        this.consumableType = consumable;
        try {
            br = new BufferedReader(new FileReader(filePath+"/"+consumable));
            bw = new BufferedWriter(new FileWriter(filePath+"/"+consumable, true));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*ArrayList<Consumable> ReadFromFile() throws IOException {
        ArrayList<String> consumableList = new ArrayList<>();
        String text;
        while ((text = br.readLine()) != null) {
            consumableList.add(text);
        }
        return ;
    }*/
    void WriteToFile(Consumable consumable) throws IOException {
        if (consumableType.equals("Food")) {
            bw.write(consumable.name + "/" + consumable.timeToPrepare + "/" + Food.TypeOfDiet.values());
            bw.close();
        } else if (consumableType.equals("Drink")) {
            bw.write(consumable.name + "/" + consumable.timeToPrepare + "/"
                    + );
        }
    }

    void ReadFile() throws IOException {
        String text;
        toSetNameAndTime = new String[3];
        while ((text = br.readLine()) != null){
            toSetNameAndTime = text.split("/");
            consumableClass = new Food(toSetNameAndTime[0],
                    Integer.parseInt(toSetNameAndTime[1]), toSetNameAndTime[2]);
            consumableList.add(consumableClass);
        }
    }

    ArrayList<Consumable> getConsumableList() {
        return consumableList;
    }

    void RemoveConsumable(Consumable consumable) {

    }

    /*public static void main(String[] args) throws IOException {
        FileHandler fh = new FileHandler("Drink");
        fh.ReadFile();

    }*/
}
