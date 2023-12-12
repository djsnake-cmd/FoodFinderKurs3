import java.io.*;

public class FileHandler {

    public static void writeObjektToFile(Consumable input) {

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("src/Consumable.ser"))) {
            outputStream.writeObject(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Consumable readObjektFromFile() throws FileNotFoundException {
        Consumable c;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/Consumable.ser"))){
            while ((c = (Consumable) in.readObject()) != null){
                return c;
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static void main(String[] args) throws FileNotFoundException {
        Consumable c = new Consumable("carbonara",10);
        //writeObjektToFile(c);
        System.out.println(readObjektFromFile().name);

    }
}


