import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileHandler {
    BufferedReader br;
    BufferedWriter bw;
    String filePath = "src";
    ArrayList<Consumable> consumableList = new ArrayList<>();
    String[] toSetNameAndTime;
    String consumable;

    Consumable consumableClass;
    FileHandler(String consumable) {
        this.consumable = consumable;
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
        bw.write(consumable.name + "/" + consumable.timeToPrepare);
        bw.close();
    }

    void ReadFile() throws IOException {
        String text;
        toSetNameAndTime = new String[2];
        while ((text = br.readLine()) != null){
            toSetNameAndTime = text.split("/");
            consumableClass = new Food(toSetNameAndTime[0],
                    Integer.parseInt(toSetNameAndTime[1]));
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
