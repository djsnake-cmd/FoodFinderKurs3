import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GUI2 extends JFrame {
    // Constants
    private static final String HOME_CARD = "home";
    private static final String ADD_OR_GET_CARD = "addorget";
    private static final String ADD_FOOD_CARD = "addfood";
    private static final String SHOW_RECIPE_CARD = "showrecipe";
    private static final String SHOW_ALL_FOODS_CARD = "showallfoods";
    private static final Font TEXT_FONT = new Font("Verdana", Font.BOLD, 16);
    private static final Font SMALLER_FONT = new Font("Verdana", Font.BOLD, 12);
    private static final Color BACKGROUND_COLOR = new Color(255, 51, 51);
    private static final Color SECONDARY_COLOR = new Color(255, 255, 153);
    private JLabel emptyStyleLabel = new JLabel();

    // UI Components
    private JPanel mainPanel;
    JComboBox<Food.TypeOfFood> addTypeOfFoodDropBox = new JComboBox<>();
    JComboBox<Food.TypeOfFood> showTypeOfFoodBox = new JComboBox<>();
    JLabel showDishLabel = new JLabel("");
    JLabel showTimeLabel = new JLabel("");
    private JLabel addNameLabel = new JLabel("Maträtt:");
    private JLabel addTimeLabel = new JLabel("Tillagningstid:");
    JLabel addTypeOfFood = new JLabel("Typ: ");
    JLabel showTypeLabel = new JLabel("Typ: ");
    private JTextArea showAllFoodsText = new JTextArea();
    private JTextArea addNameTextArea = new JTextArea(2,10);
    private JTextArea addTimeTextArea = new JTextArea(2,10);
    JScrollPane showAllFoodsSP = new JScrollPane(showAllFoodsText);
    private CardLayout cards;
    private Desktop desktop;
    private ArrayList<Food> consumableArrayList;
    private JTextField setTimeTextArea = new JTextField(5);

    GridBagConstraints gbc = new GridBagConstraints();

    private FoodManager foodManager;

    public GUI2() {
        foodManager = new FoodManager();
        FileHandler.getInstance();
        initializeFrame();
        initializeComponents();

    }

    private void initializeFrame() {
        cards = new CardLayout();
        mainPanel = new JPanel(cards);
        consumableArrayList = foodManager.getList();
        desktop = Desktop.getDesktop();
        this.add(mainPanel);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(350, 500);
    }

    private void initializeComponents() {
        createHomePanel();
        createAddOrGetPanel();
        createAddFoodPanel();
        createShowRecipePanel();
        createShowAllFoodsPanel();
    }

    private void getFood() {
        foodManager.refreshList();
        updateFoodDisplay(foodManager.getFood());
        cards.show(mainPanel,"showrecipe");
    }

    private void addFood() {
        Food newFood = new Food(addNameTextArea.getText(),
                Integer.parseInt(addTimeTextArea.getText()));
        newFood.setType(addTypeOfFoodDropBox.getSelectedItem());

        foodManager.addFood(newFood);

        cards.show(mainPanel,"home");
        addNameTextArea.setText("");
        addTimeTextArea.setText("");
    }

    private void searchRecipe() {
        String searchURL = foodManager.getFirstFoodSearchURL();
        try {
            desktop.browse(new URI(searchURL));
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void backToStart() {
        cards.show(mainPanel,HOME_CARD);
    }

    private void showAllFoods() {
        Food.TypeOfFood selectedType = (Food.TypeOfFood) showTypeOfFoodBox.getSelectedItem();
        String allFoodsFormatted = foodManager.getAllFoods(selectedType);
        showAllFoodsText.setText(allFoodsFormatted);
        cards.show(mainPanel, SHOW_ALL_FOODS_CARD);
    }

    private void removeFood() {
        foodManager.removeFood(consumableArrayList.remove(0));
        showDishLabel.setText("");
        showTimeLabel.setText("");
    }

    private void refreshFood(){
        foodManager.refreshList();
        Food firstFood = null;

        switch (showTypeOfFoodBox.getSelectedIndex()) {
            case 0: // ALL Foods
                firstFood = foodManager.getFood();
                break;
            case 1: // MEAT
                ArrayList<Food> meatFoods = foodManager.getFilteredFoodByType(Food.TypeOfFood.MEAT);
                if (!meatFoods.isEmpty()) {
                    firstFood = meatFoods.get(0);
                }
                break;
            case 2: // VEGETARIAN
                ArrayList<Food> vegetarianFoods = foodManager.getFilteredFoodByType(Food.TypeOfFood.VEGETARIAN);
                if (!vegetarianFoods.isEmpty()) {
                    firstFood = vegetarianFoods.get(0);
                }
                break;
            case 3: // VEGAN
                ArrayList<Food> veganFoods = foodManager.getFilteredFoodByType(Food.TypeOfFood.VEGAN);
                if (!veganFoods.isEmpty()) {
                    firstFood = veganFoods.get(0);
                }
                break;
            default:
                break;

        }
        updateFoodDisplay(firstFood);
        System.out.println(firstFood.name);
    }
    public void updateFoodDisplay(Food food){
        showDishLabel.setText("Maträtt: " + food.getName());
        showTimeLabel.setText("Tillagningstid: " + food.getTimeToPrepare());
        showTypeLabel.setText("Typ: " + food.getType());
    }

    private void createShowRecipePanel() {
        JPanel showFoodPanel = new JPanel(new GridBagLayout());
        showFoodPanel.setBackground(BACKGROUND_COLOR);
        gbc.gridy = 0;
        addDropBox(showFoodPanel, showTypeOfFoodBox,gbc);
        gbc.gridy = 1;
        showFoodPanel.add(showDishLabel,gbc);
        showDishLabel.setFont(TEXT_FONT);
        gbc.gridy = 2;
        showFoodPanel.add(showTimeLabel,gbc);
        showTimeLabel.setFont(TEXT_FONT);
        gbc.gridy = 3;
        showFoodPanel.add(createButton("Sök igen",TEXT_FONT,e->refreshFood()),gbc);
        gbc.gridy = 4;
        showFoodPanel.add(createButton("Sök recept",TEXT_FONT,e->searchRecipe()),gbc);
        gbc.gridy = 5;
        showFoodPanel.add(createButton("Ta bort maträtt",TEXT_FONT,e->removeFood()),gbc);
        gbc.gridy = 6;
        showFoodPanel.add(createButton("Visa alla maträtter",TEXT_FONT,e->showAllFoods()),gbc);
        gbc.gridy = 7;
        showFoodPanel.add(createButton("Tillbaka",TEXT_FONT,e->backToStart()),gbc);
        gbc.gridy = 8;
        showFoodPanel.add(createButton("Sortera efter tid",TEXT_FONT,e->searchByTime()),gbc);
        gbc.gridy = 9;
        showFoodPanel.add(setTimeTextArea,gbc);
        gbc.gridy = 10;
        showFoodPanel.add(createButton("Ta bort allt",TEXT_FONT,e->removeAllFood()),gbc);

        mainPanel.add(showFoodPanel,"showrecipe");
    }

    private void removeAllFood() {
        foodManager.removeAllFood();

    }

    private void searchByTime() {
        consumableArrayList = foodManager.refreshAndSortByTime(Integer.parseInt(setTimeTextArea.getText()));
        foodManager.refreshList();
        Random random = new Random();
        int randomIndex = random.nextInt(consumableArrayList.size());
        Food firstFood = consumableArrayList.get(randomIndex);
        updateFoodDisplay(firstFood);
    }

    private void addDropBox(JPanel panel,JComboBox<Food.TypeOfFood> groupboxes,GridBagConstraints gbc) {
        panel.add(groupboxes, gbc);
        groupboxes.addItem(Food.TypeOfFood.ALLA);
        groupboxes.addItem(Food.TypeOfFood.MEAT);
        groupboxes.addItem(Food.TypeOfFood.VEGETARIAN);
        groupboxes.addItem(Food.TypeOfFood.VEGAN);
        groupboxes.setSelectedIndex(0);
    }

    private void createAddFoodPanel() {
        JPanel addFoodPanel = new JPanel(new GridBagLayout());
        addFoodPanel.setBackground(BACKGROUND_COLOR);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        addFoodPanel.add(addNameLabel,gbc);
        addNameLabel.setFont(TEXT_FONT);
        gbc.gridx = 1;
        addFoodPanel.add(addNameTextArea,gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        addFoodPanel.add(addTimeLabel,gbc);
        addTimeLabel.setFont(TEXT_FONT);
        gbc.gridx = 1;
        addFoodPanel.add(addTimeTextArea,gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        addFoodPanel.add(addTypeOfFood, gbc);
        addTypeOfFood.setFont(TEXT_FONT);
        gbc.gridx = 1;
        addDropBox(addFoodPanel,addTypeOfFoodDropBox, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        addTypeOfFoodDropBox.removeItemAt(0);
        addFoodPanel.add(createButton("Lägg till maträtt",TEXT_FONT,e->addFood()),gbc);
        mainPanel.add(addFoodPanel,ADD_FOOD_CARD);
    }

    private void createShowAllFoodsPanel() {
        JPanel showAllFoodsPanel = new JPanel(new BorderLayout());
        showAllFoodsPanel.setBackground(BACKGROUND_COLOR);
        showAllFoodsPanel.add(createButton("Tillbaka",TEXT_FONT,e->backToStart()),BorderLayout.NORTH);
        showAllFoodsText.setFont(SMALLER_FONT);
        showAllFoodsText.setLineWrap(true);
        showAllFoodsText.setWrapStyleWord(true);
        showAllFoodsText.setBackground(SECONDARY_COLOR);
        showAllFoodsSP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        showAllFoodsPanel.add(showAllFoodsSP,BorderLayout.CENTER);
        mainPanel.add(showAllFoodsPanel,SHOW_ALL_FOODS_CARD);
    }

    private void showRandomFood() {

        foodManager.refreshList();
        updateFoodDisplay(foodManager.getFood());
        cards.show(mainPanel,ADD_OR_GET_CARD);
    }

    private JButton createButton(String text, Font font, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(SECONDARY_COLOR);
        button.addActionListener(actionListener);
        return button;
    }
    private void createHomePanel() {
        JPanel startPanel = new JPanel(new FlowLayout());
        startPanel.setBackground(BACKGROUND_COLOR);
        startPanel.add(createButton("Mat", TEXT_FONT, e -> showRandomFood()));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.add(startPanel);
        mainPanel.add(centerPanel, HOME_CARD);
    }
    private void createAddOrGetPanel() {
        JPanel secondPanel = new JPanel(new FlowLayout());
        JPanel sndcenterPanel = new JPanel(new GridBagLayout());
        sndcenterPanel.add(secondPanel);
        sndcenterPanel.setBackground(BACKGROUND_COLOR);
        secondPanel.setBackground(BACKGROUND_COLOR);
        secondPanel.add(createButton("Lägg till",TEXT_FONT,e -> cards.show(mainPanel,"addfood")));
        secondPanel.add(createButton("Hitta maträtt",TEXT_FONT,e-> getFood()));
        mainPanel.add(sndcenterPanel,"addorget");
    }

    // Main method
    public static void main(String[] args) {
        new GUI2();
    }
}

