import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class GUI extends JFrame{

    JPanel mainPanel;
    JButton foodButton = new JButton("Mat");
    JButton drinkButton = new JButton("Dryck");

    JButton getConsumableButton = new JButton("Hämta");
    JButton refreshConsumableButton = new JButton("Sök igen");
    JButton refreshBySortingTimeButton = new JButton("Sök minsta tillagningstid");
    JButton addConsumableButton = new JButton("Lägg till");

    JLabel addNameLabel =  new JLabel("Namn:");
    JLabel addTimeLabel = new JLabel("Tillagningstid:");
    JLabel addTypeOfFood = new JLabel("Typ: ");

    JLabel showDishLabel = new JLabel();
    JLabel showTimeLabel = new JLabel();
    JTextArea addNameTextArea = new JTextArea(2,10);
    JTextArea addTimeTextArea = new JTextArea(2,10);
    JComboBox addTypeOfFoodDropBox = new JComboBox();
    JComboBox showTypeOfFoodBox = new JComboBox();
    JButton searchRecipeButton = new JButton("Sök recept");
    JButton addFoodButton = new JButton("Lägg till");
    JButton backToMainButton = new JButton("Tillbaka");
    CardLayout cards;
    Desktop desktop;

    ArrayList<Consumable> consumableArrayList;


    public GUI(){

        cards = new CardLayout();
        mainPanel = new JPanel(cards);

        consumableArrayList = FileHandler.readListFromFile();
        createPanels();
        addActionListeners();
        desktop = Desktop.getDesktop();
        this.add(mainPanel);


        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400,400);
    }

    private void createPanels() {
        //STARTPANEL
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new GridLayout(2,1));

        startPanel.add(foodButton);
        startPanel.add(drinkButton);
        mainPanel.add(startPanel, "first");

        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new GridLayout(2,1));

        secondPanel.add(addConsumableButton);
        secondPanel.add(getConsumableButton);
        mainPanel.add(secondPanel,"second");

        //LÄGG TILL MATRÄTT
        JPanel addFoodPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        addFoodPanel.add(addNameLabel,gbc);
        gbc.gridx = 1;
        addFoodPanel.add(addNameTextArea,gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        addFoodPanel.add(addTimeLabel,gbc);
        gbc.gridx = 1;
        addFoodPanel.add(addTimeTextArea,gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        addFoodPanel.add(addTypeOfFood, gbc);
        gbc.gridx = 1;
        addDropBox(addFoodPanel, gbc, 0, 3, addTypeOfFoodDropBox);
        addTypeOfFoodDropBox.removeItemAt(0);
        addFoodPanel.add(addFoodButton,gbc);
        mainPanel.add(addFoodPanel,"addfood");

        //VISA RECEPT
        JPanel showFoodPanel = new JPanel(new GridBagLayout());
        gbc.gridy = 0;
        addDropBox(showFoodPanel, gbc, null,1, showTypeOfFoodBox);
        showFoodPanel.add(showDishLabel,gbc);
        gbc.gridy = 2;
        showFoodPanel.add(showTimeLabel,gbc);
        gbc.gridy = 3;
        showFoodPanel.add(refreshConsumableButton,gbc);
        gbc.gridy = 4;
        showFoodPanel.add(refreshBySortingTimeButton, gbc);
        gbc.gridy = 5;
        showFoodPanel.add(searchRecipeButton,gbc);
        gbc.gridy = 6;
        showFoodPanel.add(backToMainButton,gbc);
        mainPanel.add(showFoodPanel,"showrecipe");
    }

    private void addDropBox(JPanel panel, GridBagConstraints gbc, Integer gridNum1, Integer gridNum2,
                            JComboBox groupboxes) {
        panel.add(groupboxes, gbc);
        gbc.gridy = gridNum2;
        groupboxes.addItem("Alla");
        groupboxes.addItem(Food.TypeOfDiet.MEAT);
        groupboxes.addItem(Food.TypeOfDiet.VEGETARIAN);
        groupboxes.addItem(Food.TypeOfDiet.VEGAN);
    }

    private void addActionListeners(){
        foodButton.addActionListener(e-> {
            Collections.shuffle(consumableArrayList);
            showDishLabel.setText(consumableArrayList.get(0).name);
            showTimeLabel.setText("Tillagningstid: " + consumableArrayList.get(0).timeToPrepare + " minuter");
            cards.show(mainPanel,"second");
        });

        addConsumableButton.addActionListener(e -> cards.show(mainPanel,"addfood"));

        addFoodButton.addActionListener(e->{
            Consumable newFood = new Food(addNameTextArea.getText(),Integer.parseInt(addTimeTextArea.getText()),
                    addTypeOfFoodDropBox.getSelectedItem());
            consumableArrayList.add(newFood);
            FileHandler.writeListToFile(consumableArrayList);
            cards.show(mainPanel,"first");
            addNameTextArea.setText("");
            addTimeTextArea.setText("");

        });

        getConsumableButton.addActionListener(e-> {
            //consumableArrayList = FileHandler.readObjektFromFile();
            Collections.shuffle(consumableArrayList);
            cards.show(mainPanel,"showrecipe");

        });

        refreshConsumableButton.addActionListener(e -> {
            Collections.shuffle(consumableArrayList);
            showDishLabel.setText(consumableArrayList.get(0).name);
            showTimeLabel.setText("Tillagningstid: " + consumableArrayList.get(0).timeToPrepare + " minuter");
            cards.show(mainPanel,"showrecipe");
        });

        refreshBySortingTimeButton.addActionListener(e -> {
            Filter filter = new Filter();
            ArrayList<Consumable> filteredList = filter.FilterByTime(consumableArrayList);
            showDishLabel.setText(filteredList.get(0).name);
            showTimeLabel.setText("Tillagningstid: " + filteredList.get(0).timeToPrepare + " minuter");
            cards.show(mainPanel,"showrecipe");
        });

        searchRecipeButton.addActionListener(e->{
            String encodedQuery = URLEncoder.encode(consumableArrayList.get(0).name + " recept", StandardCharsets.UTF_8);
            String searchURL = "https://www.google.com/search?q=" + encodedQuery;
            try {
                desktop.browse(new URI(searchURL));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        backToMainButton.addActionListener(e->{
            cards.show(mainPanel,"first");
        });

    }



    public static void main(String[] args) {
        GUI g = new GUI();
    }
}
