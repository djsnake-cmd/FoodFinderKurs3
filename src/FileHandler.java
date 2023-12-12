import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static void writeObjektToFile(Consumable input) {

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("src/Consumable.ser"))) {
            outputStream.writeObject(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Consumable> readObjektFromFile(){
        List<Consumable> consumableList = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/Consumable.ser"))) {
            while (true) {
                try {
                    Consumable c = (Consumable) in.readObject();
                    consumableList.add(c);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return consumableList;
    }
    public static void main(String[] args) {
        Consumable c = new Consumable("carbonara",10);
        //writeObjektToFile(c);
        System.out.println(readObjektFromFile().get(0).name);

    }
}


