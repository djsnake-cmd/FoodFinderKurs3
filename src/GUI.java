import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame{

    JPanel mainPanel;
    JButton foodButton = new JButton("Mat");
    JButton drinkButton = new JButton("Dryck");

    JButton getConsumableButton = new JButton("Hämta");
    JButton addConsumableButton = new JButton("Lägg till");
    CardLayout cards;


    public GUI(){

        cards = new CardLayout();
        mainPanel = new JPanel(cards);

        createPanels();
        addActionListeners();
        this.add(mainPanel);


        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400,400);
    }

    private void createPanels() {
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
    }

    private void addActionListeners(){
        foodButton.addActionListener(e-> cards.show(mainPanel,"second"));
    }



    public static void main(String[] args) {
        GUI g = new GUI();
    }
}
