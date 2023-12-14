import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static void writeObjektToFile(List<Consumable> consumables) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("src/Consumables.ser"))) {
            for (Consumable c : consumables) {
                outputStream.writeObject(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Consumable> readObjektFromFile() {
        List<Consumable> consumableList = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/Consumables.ser"))) {
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

        Consumable carbonara = new Consumable("Carbonara", 10);
        Consumable shirleyTemple = new Drink("Shirley Temple", 5, false);


        List<Consumable> consumables = new ArrayList<>();
        consumables.add(carbonara);
        consumables.add(shirleyTemple);


        writeObjektToFile(consumables);


        List<Consumable> readConsumables = readObjektFromFile();
        System.out.println(readConsumables.get(0).getName());
    }
}
