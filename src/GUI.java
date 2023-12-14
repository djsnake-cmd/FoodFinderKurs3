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
    JLabel showTypeLabel = new JLabel("Typ: ");
    JTextArea addNameTextArea = new JTextArea(2,10);
    JTextArea addTimeTextArea = new JTextArea(2,10);
    JComboBox<Food.TypeOfFood> addTypeOfFoodDropBox = new JComboBox<>();
    JComboBox<Food.TypeOfFood> showTypeOfFoodBox = new JComboBox<>();
    JButton searchRecipeButton = new JButton("Sök recept");
    JButton addFoodButton = new JButton("Lägg till");
    JButton backToMainButton = new JButton("Tillbaka");
    CardLayout cards;
    Desktop desktop;

    ArrayList<Food> consumableArrayList;
    ArrayList<Food> sortedFoodList;

    public GUI(){
        ArrayList<Food> sortedFoodList;

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
        showFoodPanel.add(showTypeLabel, gbc);
        gbc.gridy = 4;
        showFoodPanel.add(refreshConsumableButton,gbc);
        gbc.gridy = 5;
        showFoodPanel.add(refreshBySortingTimeButton, gbc);
        gbc.gridy = 6;
        showFoodPanel.add(searchRecipeButton,gbc);
        gbc.gridy = 7;
        showFoodPanel.add(backToMainButton,gbc);
        mainPanel.add(showFoodPanel,"showrecipe");
    }

    private void addDropBox(JPanel panel, GridBagConstraints gbc, Integer gridNum1, Integer gridNum2,
                            JComboBox<Food.TypeOfFood> groupboxes) {
        panel.add(groupboxes, gbc);
        if (gridNum1 == null) {
            gbc.gridy = gridNum2;
        } else if (gridNum2 == null) {
            gbc.gridx = gridNum1;
        } else {
            gbc.gridx = gridNum1;
            gbc.gridy = gridNum2;
        }
        groupboxes.addItem(Food.TypeOfFood.ALLA);
        groupboxes.addItem(Food.TypeOfFood.MEAT);
        groupboxes.addItem(Food.TypeOfFood.VEGETARIAN);
        groupboxes.addItem(Food.TypeOfFood.VEGAN);
        groupboxes.setSelectedIndex(0);
    }

    public void SortingSpace(Object selectedType) {
        //ArrayList<Food> sortedFoodList;
        Filter filter = new Filter();
        sortedFoodList = filter.TypeOfFoodFilter(consumableArrayList,
                selectedType);
        consumableArrayList = sortedFoodList;
        //Collections.shuffle(sortedFoodList);
        showDishLabel.setText(consumableArrayList.get(0).name);
        showTimeLabel.setText("Tillagningstid: " + consumableArrayList.get(0).timeToPrepare + " minuter");
        showTypeLabel.setText("Typ: "+ consumableArrayList.get(0).dietType);
        cards.show(mainPanel,"showrecipe");
    }


    private void addActionListeners(){
        foodButton.addActionListener(e-> {
            Collections.shuffle(consumableArrayList);
            showDishLabel.setText(consumableArrayList.get(0).name);
            showTimeLabel.setText("Tillagningstid: " + consumableArrayList.get(0).timeToPrepare + " minuter");
            showTypeLabel.setText("Typ: " + consumableArrayList.get(0).dietType);
            cards.show(mainPanel,"second");
        });

        addConsumableButton.addActionListener(e -> cards.show(mainPanel,"addfood"));

        addFoodButton.addActionListener(e->{
            Food newFood = new Food(addNameTextArea.getText(),Integer.parseInt(addTimeTextArea.getText()));
            newFood.setType(addTypeOfFoodDropBox.getSelectedItem());
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
            if (showTypeOfFoodBox.getSelectedIndex() == 0) {
                consumableArrayList = FileHandler.readListFromFile();
                Collections.shuffle(consumableArrayList);
                showDishLabel.setText(consumableArrayList.get(0).name);
                showTimeLabel.setText("Tillagningstid: " + consumableArrayList.get(0).timeToPrepare + " minuter");
                showTypeLabel.setText("Typ: " + consumableArrayList.get(0).dietType);
                //System.out.println("Type of object at index 0: " + consumableArrayList.get(0).getClass().getSimpleName());
                cards.show(mainPanel,"showrecipe");
            } else if (showTypeOfFoodBox.getSelectedIndex() == 1) {
                consumableArrayList = FileHandler.readListFromFile();
                Collections.shuffle(consumableArrayList);
                SortingSpace(showTypeOfFoodBox.getSelectedItem());
            } else if (showTypeOfFoodBox.getSelectedIndex() == 2) {
                consumableArrayList = FileHandler.readListFromFile();
                Collections.shuffle(consumableArrayList);
                SortingSpace(showTypeOfFoodBox.getSelectedItem());
            } else if (showTypeOfFoodBox.getSelectedIndex() == 3) {
                consumableArrayList = FileHandler.readListFromFile();
                Collections.shuffle(consumableArrayList);
                SortingSpace(showTypeOfFoodBox.getSelectedItem());
            }

        });

        refreshBySortingTimeButton.addActionListener(e -> {
            Filter filter = new Filter();
            if (showTypeOfFoodBox.getSelectedIndex() == 0)  {
                consumableArrayList = FileHandler.readListFromFile();
                consumableArrayList = filter.FilterByTime(consumableArrayList);
                showDishLabel.setText(consumableArrayList.get(0).name);
                showTimeLabel.setText("Tillagningstid: " + consumableArrayList.get(0).timeToPrepare + " minuter");
                showTypeLabel.setText("Typ: " + consumableArrayList.get(0).dietType);
            } else if (showTypeOfFoodBox.getSelectedIndex() == 1) {
                consumableArrayList = FileHandler.readListFromFile();
                consumableArrayList = filter.FilterByTime(consumableArrayList);
                SortingSpace(showTypeOfFoodBox.getSelectedItem());
            } else if (showTypeOfFoodBox.getSelectedIndex() == 2) {
                consumableArrayList = FileHandler.readListFromFile();
                consumableArrayList = filter.FilterByTime(consumableArrayList);
                SortingSpace(showTypeOfFoodBox.getSelectedItem());
            } else if (showTypeOfFoodBox.getSelectedIndex() == 3) {
                consumableArrayList = FileHandler.readListFromFile();
                consumableArrayList = filter.FilterByTime(consumableArrayList);
                SortingSpace(showTypeOfFoodBox.getSelectedItem());
                cards.show(mainPanel, "showrecipe");
            }
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
