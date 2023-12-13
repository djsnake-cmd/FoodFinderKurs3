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
    JButton addConsumableButton = new JButton("Lägg till");
    JLabel addNameLabel =  new JLabel("Namn:");
    JLabel addTimeLabel = new JLabel("Tillagningstid:");
    JLabel showDishLabel = new JLabel();
    JLabel showTimeLabel = new JLabel();
    Font textFont = new Font("Verdana",Font.BOLD,18);
    Font smallerFont = new Font("Verdana",Font.PLAIN,13);
    JTextArea addNameTextArea = new JTextArea(2,10);
    JTextArea addTimeTextArea = new JTextArea(2,10);
    JButton searchRecipeButton = new JButton("Sök recept");
    JButton addFoodButton = new JButton("Lägg till");
    JButton backToMainButton = new JButton("Tillbaka");
    JButton removeButton = new JButton("Ta bort maträtt");
    JButton showAllFoodsButton = new JButton("Visa alla rätter");
    //JLabel showAllFoodsLabel = new JLabel();
    Dimension buttonDim = new Dimension(40,40);
    JTextArea showAllFoodsText = new JTextArea();
    JScrollPane showAllFoodsSP = new JScrollPane(showAllFoodsText);
    JButton backFromShowAllButton = new JButton("Tillbaka");
    CardLayout cards;
    Desktop desktop;
    Color backgroundColor = new Color(255,51,51);
    Color secondaryColor = new Color(255,255,153);

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
        mainPanel.setBackground(backgroundColor);
        //STARTPANEL
        JPanel startPanel = new JPanel(new FlowLayout());
        JPanel centerPanel = new JPanel(new GridBagLayout());
        startPanel.setBackground(backgroundColor);
        centerPanel.setBackground(backgroundColor);
        centerPanel.add(startPanel);
        startPanel.add(foodButton);
        startPanel.add(drinkButton);
        foodButton.setFont(textFont);
        drinkButton.setFont(textFont);
        mainPanel.add(centerPanel, "home");

        //Hämta eller lägg till mat
        JPanel secondPanel = new JPanel(new FlowLayout());
        JPanel sndcenterPanel = new JPanel(new GridBagLayout());
        sndcenterPanel.add(secondPanel);
        sndcenterPanel.setBackground(backgroundColor);
        secondPanel.setBackground(backgroundColor);
        secondPanel.add(addConsumableButton);
        secondPanel.add(getConsumableButton);
        addConsumableButton.setFont(textFont);
        getConsumableButton.setFont(textFont);
        mainPanel.add(sndcenterPanel,"addorget");

        //LÄGG TILL MATRÄTT
        JPanel addFoodPanel = new JPanel(new GridBagLayout());
        addFoodPanel.setBackground(backgroundColor);
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
        addFoodPanel.add(addFoodButton,gbc);

        addNameLabel.setFont(textFont);
        addTimeLabel.setFont(textFont);
        addFoodButton.setFont(textFont);
        mainPanel.add(addFoodPanel,"addfood");

        //VISA RECEPT
        JPanel showFoodPanel = new JPanel(new GridBagLayout());
        showFoodPanel.setBackground(backgroundColor);
        gbc.gridy = 0;
        showFoodPanel.add(showDishLabel,gbc);
        gbc.gridy = 1;
        showFoodPanel.add(showTimeLabel,gbc);
        gbc.gridy = 2;
        showFoodPanel.add(refreshConsumableButton,gbc);
        gbc.gridy = 3;
        showFoodPanel.add(searchRecipeButton,gbc);
        gbc.gridy = 4;
        showFoodPanel.add(removeButton,gbc);
        gbc.gridy = 5;
        showFoodPanel.add(showAllFoodsButton,gbc);
        gbc.gridy = 6;
        showFoodPanel.add(backToMainButton,gbc);

        showDishLabel.setFont(textFont);
        showTimeLabel.setFont(textFont);
        refreshConsumableButton.setFont(textFont);
        searchRecipeButton.setFont(textFont);
        removeButton.setFont(textFont);
        showAllFoodsButton.setFont(textFont);
        backToMainButton.setFont(textFont);

        mainPanel.add(showFoodPanel,"showrecipe");

        JPanel showAllFoodsPanel = new JPanel(new BorderLayout());
        showAllFoodsPanel.setBackground(backgroundColor);
        showAllFoodsPanel.add(backFromShowAllButton,BorderLayout.NORTH);
        backFromShowAllButton.setFont(textFont);
        showAllFoodsText.setFont(smallerFont);
        showAllFoodsText.setLineWrap(true);
        showAllFoodsText.setWrapStyleWord(true);
        showAllFoodsText.setBackground(secondaryColor);
        showAllFoodsSP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        showAllFoodsPanel.add(showAllFoodsSP,BorderLayout.CENTER);
        mainPanel.add(showAllFoodsPanel,"showallfoods");
    }

    private void addActionListeners(){
        foodButton.addActionListener(e-> {
            Collections.shuffle(consumableArrayList);
            showDishLabel.setText("Maträtt: " +
                    consumableArrayList.get(0).name);
            showTimeLabel.setText("Tillagningstid: " +
                    consumableArrayList.get(0).timeToPrepare + " minuter");
            cards.show(mainPanel,"addorget");
        });

        addConsumableButton.addActionListener(e -> cards.show(mainPanel,"addfood"));

        addFoodButton.addActionListener(e->{
            Consumable newFood = new Consumable(addNameTextArea.getText(),
                    Integer.parseInt(addTimeTextArea.getText()));
            consumableArrayList.add(newFood);
            FileHandler.writeListToFile(consumableArrayList);
            cards.show(mainPanel,"home");
            addNameTextArea.setText("");
            addTimeTextArea.setText("");
        });

        getConsumableButton.addActionListener(e-> {
            Collections.shuffle(consumableArrayList);
            showDishLabel.setText("Maträtt: "
                    + consumableArrayList.get(0).name);
            showTimeLabel.setText("Tillagningstid: "
                    + consumableArrayList.get(0).timeToPrepare + " minuter");
            cards.show(mainPanel,"showrecipe");
        });

        refreshConsumableButton.addActionListener(e -> {
            Collections.shuffle(consumableArrayList);
            showDishLabel.setText("Maträtt: "
                    + consumableArrayList.get(0).name);
            showTimeLabel.setText("Tillagningstid: "
                    + consumableArrayList.get(0).timeToPrepare + " minuter");
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
            cards.show(mainPanel,"home");
        });

        removeButton.addActionListener(e-> {
            System.out.println("Du tog bort: " + consumableArrayList.get(0).name);
            consumableArrayList.remove(0);
            FileHandler.writeListToFile(consumableArrayList);
            System.out.println(consumableArrayList.get(0).name);
        });

        showAllFoodsButton.addActionListener(e->{
            StringBuilder sb = new StringBuilder();
            for (Consumable consumable: consumableArrayList) {
                showAllFoodsText.setText(String.valueOf(sb.append(consumable.name
                        + "\n" + consumable.timeToPrepare + " minuter" + "\n"+"\n")));
            }
            cards.show(mainPanel,"showallfoods");
        });

        backFromShowAllButton.addActionListener(e -> {
            cards.show(mainPanel,"home");
        });

    }



    public static void main(String[] args) {
        GUI g = new GUI();
    }
}
