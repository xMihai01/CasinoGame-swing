package games;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import system.GUI;
import system.Level;
import system.Profile;
import system.Credit;

public class Slots implements ActionListener {
    
    JFrame slotsFrame;
    JPanel slotsPanel;
    
    Profile p = Profile.getInstance();
    
    Credit bet = new Credit();
    Credit initialBet = new Credit();
    Credit tempBet = new Credit();
    Credit credit = p.getCredit();
    Level level = p.getLevel();

    JButton exitButton, spinButton, takeButton, doubleButton;

    JTextField setBetField, messageField, statusField, creditField, setBetLetterField, creditLetterField;

    JLabel creditLabel, betLabel;

    JLabel[] slotMachine = new JLabel[9];
    ImageIcon cherry, lemon, prune, melon, grape, orange, seven;

    Random random = ThreadLocalRandom.current();
    int coefficient;
    int doublingNumber;
    
    public Slots() {
        
        slotsFrame = new JFrame();
        slotsFrame.setTitle("Loading slots...");
        slotsFrame.setSize(1024, 512);
        slotsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        slotsFrame.setResizable(false);
        slotsFrame.setLocationRelativeTo(null);
        slotsFrame.setLayout(null);

        slotsPanel = new JPanel();
        slotsPanel.setBounds(150, 200, 650, 250);
        slotsPanel.setLayout(new GridLayout(3, 3, 10, 10));

        setBetField = new JTextField();
        messageField = new JTextField("You are currently playing games.Slots! Place a bet and click spin to start!");
        creditField = new JTextField(String.format("%.2f", credit.getNumber()));
        statusField = new JTextField("");
        setBetLetterField = new JTextField(Character.toString(credit.getLetter()));
        creditLetterField = new JTextField(Character.toString(credit.getLetter()));

        exitButton = new JButton("RETURN");
        spinButton = new JButton("Spin");
        takeButton = new JButton("Take");
        doubleButton = new JButton("DOUBLE");
        exitButton.setBounds(900, 0, 105, 100);
        spinButton.setBounds(600, 50, 100, 75);
        takeButton.setBounds(600, 50, 100, 75);
        doubleButton.setBounds(710, 50, 100, 75);
        exitButton.addActionListener(this);
        spinButton.addActionListener(this);
        takeButton.addActionListener(this);
        doubleButton.addActionListener(this);
        takeButton.setVisible(false);
        doubleButton.setVisible(false);

        betLabel = new JLabel("BET: ");
        creditLabel = new JLabel("Available CREDIT: ");
        betLabel.setBounds(350, 100, 50, 25);
        creditLabel.setBounds(300, 50, 125, 25);
        creditField.setBounds(425, 50, 100, 25);
        creditLetterField.setBounds(530, 50, 25, 25);
        setBetField.setBounds(425, 100, 100, 25);
        setBetLetterField.setBounds(530, 100, 25, 25);
        messageField.setBounds(275, 140, 400, 30);
        statusField.setBounds(275, 165, 400, 30);

        creditLetterField.setEditable(false);
        messageField.setEditable(false);
        statusField.setEditable(false);
        creditField.setEditable(false);

        coefficient = 0;
        doublingNumber = 0;

        slotsFrame.add(betLabel);
        slotsFrame.add(setBetField);
        slotsFrame.add(messageField);
        slotsFrame.add(creditLabel);
        slotsFrame.add(creditField);
        slotsFrame.add(exitButton);
        slotsFrame.add(spinButton);
        slotsFrame.add(takeButton);
        slotsFrame.add(doubleButton);
        slotsFrame.add(setBetLetterField);
        slotsFrame.add(creditLetterField);
        slotsFrame.add(slotsPanel);
        slotsFrame.add(statusField);
        slotsFrame.setVisible(true);

        cherry = new ImageIcon(new ImageIcon("images/cherry.jpg").getImage().getScaledInstance(200, 70, Image.SCALE_DEFAULT));
        lemon =  new ImageIcon(new ImageIcon("images/lemon.jpg").getImage().getScaledInstance(200, 70, Image.SCALE_DEFAULT));
        orange =  new ImageIcon(new ImageIcon("images/orange.jpg").getImage().getScaledInstance(200, 70, Image.SCALE_DEFAULT));
        melon =  new ImageIcon(new ImageIcon("images/melon.jpg").getImage().getScaledInstance(200, 70, Image.SCALE_DEFAULT));
        prune =  new ImageIcon(new ImageIcon("images/prune.jpg").getImage().getScaledInstance(200, 70, Image.SCALE_DEFAULT));
        grape =  new ImageIcon(new ImageIcon("images/grape.jpg").getImage().getScaledInstance(200, 70, Image.SCALE_DEFAULT));
        seven =  new ImageIcon(new ImageIcon("images/seven.png").getImage().getScaledInstance(200, 70, Image.SCALE_DEFAULT));


        for (int i = 0; i<9; i++) {

            int randomNumber = random.nextInt(7);
            slotMachine[i] = new JLabel();
            // slotMachine[i].setIcon(new ImageIcon(getSlotImage(randomNumber).getImage().getScaledInstance(200, 70, Image.SCALE_DEFAULT)));
            slotMachine[i].setIcon(getSlotImage(randomNumber));
            slotsPanel.add(slotMachine[i]);

            slotsFrame.setTitle("CasinoGame: games.Slots");
        }


    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exitButton) {
            slotsFrame.dispose();
            new GUI();
            return;
        }

        if (e.getSource() == takeButton) {

            messageField.setText("You won " + Double.toString(bet.getNumber()) +  Character.toString(bet.getLetter()) + " credit!");
            statusField.setText("TAKEN!");
            credit.addCredit(bet);

            gameEnd();

        } else if (e.getSource() == doubleButton) {

            int randomNumber = random.nextInt();
            doublingNumber++;

            if (randomNumber%2 == 0) {

                bet.setNumber(bet.getNumber() * 2);
                bet.reviewNumber();
                messageField.setText("You won " + Double.toString(bet.getNumber()) +  Character.toString(bet.getLetter()) + " credit!");
                statusField.setText("DOUBLED! (" + Integer.toString(5-doublingNumber) + " remaining). Take or double?");
            } else {

                credit.removeCredit(initialBet);
                messageField.setText("You lost " + Double.toString(bet.getNumber()) +  Character.toString(bet.getLetter()) + " credit!");
                statusField.setText("The odds were not in your favor while doubling.");
                gameEnd();
            }
            if (doublingNumber >=5) {

                credit.addCredit(bet);
                gameEnd();
                messageField.setText("You won " + Double.toString(bet.getNumber()) +  Character.toString(bet.getLetter()) + " credit!");
                statusField.setText("You reached the maximum number of doublings!");
            }

        }

        if (e.getSource() == spinButton) {
            if (!Credit.verifyBet(setBetField.getText(), setBetLetterField.getText(), credit, bet, messageField))
                return;

            coefficient = 0;
            statusField.setText("");
            generateSlotMachine();

        }

    }

    void generateSlotMachine() {

        for (int i = 0; i<9; i++) {

            slotMachine[i].setIcon(generateSlotResult());

        }

        coefficient = calculateSlotWinCoefficient();

        if (coefficient > 0) {
            initialBet.setLetter(bet.getLetter());
            initialBet.setNumber(bet.getNumber());
            tempBet.setNumber(bet.getNumber());
            tempBet.setLetter(bet.getLetter());

            bet.setNumber(bet.getNumber() * coefficient);
            bet.reviewNumber();
            messageField.setText("You won " + Double.toString(bet.getNumber()) +  Character.toString(bet.getLetter()) + " credit!");
            statusField.setText("TAKE to get the money or DOUBLE to have a 50% chance to double it!");
         //   credit.addCredit(bet);

            spinButton.setVisible(false);
            takeButton.setVisible(true);
            doubleButton.setVisible(true);
        } else {
            credit.removeCredit(bet);
            messageField.setText("You lost " + Double.toString(bet.getNumber()) +  Character.toString(bet.getLetter()) + " credit!");
        }

        creditField.setText(String.format("%.2f", credit.getNumber()));
        creditLetterField.setText(Character.toString(credit.getLetter()));
        p.updateCredit(credit);

    }

    int calculateSlotWinCoefficient() {

        int coefficient = 0;

        if (slotMachine[3].getIcon().equals(slotMachine[4].getIcon()) && slotMachine[4].getIcon().equals(slotMachine[5].getIcon()))
            coefficient+=getCoefficientValue(slotMachine[3]);
        if (slotMachine[0].getIcon().equals(slotMachine[1].getIcon()) && slotMachine[1].getIcon().equals(slotMachine[2].getIcon()))
            coefficient+=getCoefficientValue(slotMachine[0]);
        if (slotMachine[6].getIcon().equals(slotMachine[7].getIcon()) && slotMachine[7].getIcon().equals(slotMachine[8].getIcon()))
            coefficient+=getCoefficientValue(slotMachine[6]);
        if (slotMachine[0].getIcon().equals(slotMachine[4].getIcon()) && slotMachine[4].getIcon().equals(slotMachine[8].getIcon()))
            coefficient+=getCoefficientValue(slotMachine[0]);
        if (slotMachine[2].getIcon().equals(slotMachine[4].getIcon()) && slotMachine[4].getIcon().equals(slotMachine[6].getIcon()))
            coefficient+=getCoefficientValue(slotMachine[2]);

        return coefficient;

    }

    int getCoefficientValue(JLabel machineLabel) {

        if (machineLabel.getIcon().equals(cherry) || machineLabel.getIcon().equals(lemon) || machineLabel.getIcon().equals(prune) || machineLabel.getIcon().equals(orange))
            return 4;
        else if (machineLabel.getIcon().equals(melon) || machineLabel.getIcon().equals(grape))
            return 10;
        else if (machineLabel.getIcon().equals(seven))
            return 20;
        else
            return -1;

    }

    ImageIcon generateSlotResult() {

        int randomNumber = random.nextInt(100) + 1;

        if (randomNumber > 0 && randomNumber <=15)
            return cherry;
        else if (randomNumber <=100 && randomNumber >85)
            return lemon;
        else if (randomNumber > 15 && randomNumber <=30)
            return prune;
        else if (randomNumber <=85 && randomNumber >70)
            return orange;
        else if (randomNumber > 30 && randomNumber <=45)
            return melon;
        else if (randomNumber <=70 && randomNumber > 55)
            return grape;
        else return seven;

    }

    ImageIcon getSlotImage(int number) {

        if (number == 0)
            return cherry;
        else if (number == 1)
            return lemon;
        else if (number == 2)
            return orange;
        else if (number == 3)
            return prune;
        else if (number == 4)
            return grape;
        else if (number == 5)
            return melon;
        else
            return seven;

    }

    void gameEnd() {

        doublingNumber = 0;

        level.addLevel(credit, tempBet);
        p.updateLevel(level);
        p.updateCredit(credit);
        creditField.setText(String.format("%.2f", credit.getNumber()));
        creditLetterField.setText(Character.toString(credit.getLetter()));

        doubleButton.setVisible(false);
        takeButton.setVisible(false);
        spinButton.setVisible(true);

    }
    
}
