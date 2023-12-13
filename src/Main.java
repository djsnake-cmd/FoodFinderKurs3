import java.io.IOException;
import java.util.*;

public class Main {
    static Filter filter;
    static FileHandler fileHandler;
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        Main m = new Main();
        Consumable consumable;

        System.out.println("CHOOSE\n");
        System.out.println("1. Food");
        System.out.println("2. Drink");

        int alt = scan.nextInt();

        if (alt == 1) {
            fileHandler = new FileHandler("Food");
            fileHandler.ReadFile();
            m.ShowList(fileHandler);
            System.out.println("1. Sort");
            System.out.println("2. Shuffle");
            scan.nextLine();
            String alt2 = scan.nextLine();
            if (alt2.equalsIgnoreCase("1")) {
                Filter filters = new Filter();
                filters.FilterByTime(fileHandler);
                m.ShowList(fileHandler);

            } else if (alt2.equalsIgnoreCase("2")) {
                Collections.shuffle(fileHandler.getConsumableList());
                m.ShowList(fileHandler);
            } else {
                System.out.println("...");
            }
        } else if (alt == 2) {
            fileHandler = new FileHandler("Drink");
        } else {
            System.exit(0);
        }

    }

    void ShowList(FileHandler fileHandler) {
        for (Consumable consumable : fileHandler.getConsumableList()) {
            System.out.println("Name: "+consumable.name);
            System.out.println("Time: " + consumable.timeToPrepare);
        }
    }
}
