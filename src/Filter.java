import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Filter {
    private ArrayList<Consumable> consumableList;

    public Filter(ArrayList<Consumable> consumableList) {
        this.consumableList = consumableList;
    }

    public void filterByTime(int maxTime) {
        if (consumableList.isEmpty()) {
            System.out.println("Inga förbrukningsvaror att filtrera.");
            return;
        }


        Collections.sort(consumableList, (c1, c2) -> Integer.compare(c1.getTimeToPrepare(), c2.getTimeToPrepare()));


        System.out.println("Förbrukningsvaror med tid <= " + maxTime + " minuter:");
        for (Consumable consumable : consumableList) {
            if (consumable.getTimeToPrepare() <= maxTime) {
                System.out.println(consumable.getName() + " - Tid: " + consumable.getTimeToPrepare() + " minuter");
            }
        }

        // Flytta det valda objektet längst bak i listan
        if (!consumableList.isEmpty()) {
            Consumable selectedConsumable = consumableList.remove(0);
            consumableList.add(selectedConsumable);
            System.out.println("Vald förbrukningsvara flyttad längst bak: " + selectedConsumable.getName());
        }
    }
}