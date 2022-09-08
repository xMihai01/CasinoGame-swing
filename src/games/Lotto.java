package games;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import system.GUI;
import system.Level;
import system.Profile;
import system.Credit;

public class Lotto implements ActionListener{

    JFrame lottoFrame;
    JPanel lottoPanel;

    Profile p = Profile.getInstance();

    Credit bet = new Credit();
    Credit credit = p.getCredit();
    Level level = p.getLevel();

    JButton[] numbers = new JButton[49];
    JButton exitButton, playButton, cancelButton;

    JTextField setBetField, messageField, statusField, creditField, setBetLetterField, creditLetterField;

    JLabel creditLabel, betLabel;

    Random random = ThreadLocalRandom.current();
    boolean isGameRunning;
    int[] choices = new int[6];
    int[] winningChoices = new int[6];
    int numberOfChosen;

    public Lotto() {

        lottoFrame = new JFrame();
        lottoFrame.setTitle("CasinoGame: games.Lotto");
        lottoFrame.setSize(1024, 512);
        lottoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        lottoFrame.setResizable(false);
        lottoFrame.setLocationRelativeTo(null);
        lottoFrame.setLayout(null);

        lottoPanel = new JPanel();
        lottoPanel.setBounds(150, 200, 650, 250);
        lottoPanel.setLayout(new GridLayout(7,7, 10,10));

        setBetField = new JTextField();
        messageField = new JTextField("You are currently playing lotto! Place a bet and click play to start!");
        creditField = new JTextField(String.format("%.2f", credit.getNumber()));
        statusField = new JTextField("");
        setBetLetterField = new JTextField(Character.toString(credit.getLetter()));
        creditLetterField = new JTextField(Character.toString(credit.getLetter()));

        exitButton = new JButton("RETURN");
        playButton = new JButton("Play");
        cancelButton = new JButton("Cancel");
        exitButton.setBounds(900, 0, 105, 100);
        playButton.setBounds(600, 50, 100, 75);
        cancelButton.setBounds(600, 50, 100, 75);
        exitButton.addActionListener(this);
        playButton.addActionListener(this);
        cancelButton.addActionListener(this);
        cancelButton.setVisible(false);

        betLabel = new JLabel("BET: ");
        creditLabel = new JLabel("Available CREDIT: ");
        betLabel.setBounds(350, 100, 50, 25);
        creditLabel.setBounds(300, 50, 125, 25);
        creditField.setBounds(425, 50, 100, 25);
        creditLetterField.setBounds(530, 50, 25, 25);
        setBetField.setBounds(425, 100, 100, 25);
        setBetLetterField.setBounds(530, 100, 25, 25);
        messageField.setBounds(275, 132, 400, 30);
        statusField.setBounds(275, 165, 400, 30);

        creditLetterField.setEditable(false);
        messageField.setEditable(false);
        statusField.setEditable(false);
        creditField.setEditable(false);
        
        for (int i = 0; i<49; i++) {

            numbers[i] = new JButton(String.valueOf(i+1));
            numbers[i].addActionListener(this);
            numbers[i].setFocusable(false);
            lottoPanel.add(numbers[i]);

        }

        lottoFrame.add(lottoPanel);
        lottoFrame.add(betLabel);
        lottoFrame.add(setBetField);
        lottoFrame.add(messageField);
        lottoFrame.add(creditLabel);
        lottoFrame.add(creditField);
        lottoFrame.add(exitButton);
        lottoFrame.add(playButton);
        lottoFrame.add(cancelButton);
        lottoFrame.add(setBetLetterField);
        lottoFrame.add(creditLetterField);
        lottoFrame.add(statusField);
        lottoFrame.setVisible(true);

        isGameRunning = false;
        numberOfChosen = 0;
        for (int i = 0; i<6; i++) {
            choices[i] = 0;
            winningChoices[i] = 0;
        }

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exitButton) {
            lottoFrame.dispose();
            new GUI();
            return;
        } else if (e.getSource() == cancelButton) {
            resetLottoGame();
            messageField.setText("The game has been cancelled");
            return;
        }

        if (e.getSource() == playButton) {
            if (Credit.verifyBet(setBetField.getText(), setBetLetterField.getText(), credit, bet, messageField)) {
                playButton.setVisible(false);
                cancelButton.setVisible(true);
                isGameRunning = true;
                messageField.setText("Bet accepted, please select 6 numbers!");
                statusField.setText("");
                System.out.println("games.Lotto has started");
            }
            return;
        }

        if (isGameRunning) {

            if (numberOfChosen == 0)
                messageField.setText("Choices: ");

            for (int i = 0; i<49; i++) {

                if (e.getSource() == numbers[i]) {

                    choices[numberOfChosen] = i+1;
                    numbers[i].setVisible(false);
                    numberOfChosen++;
                    messageField.setText(messageField.getText() + " " + Integer.toString(i+1));

                }

            }

            if (numberOfChosen == 6) {

                generateLottoWinningNumbers();
                checkLottoWin(checkFoundNumbers());
                resetLottoGame();

            }

        } else {
            messageField.setText("Press play button before choosing a number!");
        }


    }

    void checkLottoWin(int foundNumbers) {

        String temp = messageField.getText();
        Credit initialBet = new Credit(bet.getNumber(), bet.getLetter());

        if (foundNumbers <1) {
            messageField.setText("You lost " + bet.getNumber() + bet.getLetter() + " CREDIT! " + temp);
            credit.removeCredit(bet);
        } else {
            bet.setNumber(bet.getNumber() * Math.pow(4, foundNumbers));
            bet.reviewNumber();
            credit.addCredit(bet);

            messageField.setText("You won " + String.format("%.1f", bet.getNumber())+ bet.getLetter() + " CREDIT! " + temp);
        }

        level.addLevel(credit, initialBet);
        p.updateLevel(level);
        p.updateCredit(credit);
        creditField.setText(String.format("%.2f", credit.getNumber()));
        creditLetterField.setText(Character.toString(credit.getLetter()));


    }

    void resetLottoGame() {

        isGameRunning = false;
        cancelButton.setVisible(false);
        playButton.setVisible(true);
        for (int i = 0; i<49; i++) {
            numbers[i].setVisible(true);
            numberOfChosen = 0;
        }
        for (int i = 0; i<6; i++) {
            winningChoices[i] = 0;
            choices[i] = 0;
        }

    }

    int checkFoundNumbers() {

        int foundNumbers = 0;
        statusField.setText("Winning numbers: ");

        for (int i = 0; i<6; i++) {
            statusField.setText(statusField.getText() + " " + Integer.toString(winningChoices[i]));
            for (int j = 0; j < 6; j++)
                if (winningChoices[i] == choices[j])
                    foundNumbers++;
        }
        statusField.setText(statusField.getText() + " (" + Integer.toString(foundNumbers) + " found numbers)");
        return foundNumbers;

    }

    void generateLottoWinningNumbers() {

        int randomNumber = random.nextInt(48) +1;

        for (int i = 0; i< 6; i++) {
            if (checkIfDuplicateChoice(winningChoices, randomNumber))
                i--;
            else
                winningChoices[i] = randomNumber;
            randomNumber = random.nextInt(48) + 1;
        }

    }

    boolean checkIfDuplicateChoice(int[] array, int choice) {

        for (int i = 0; i<6; i++) {
            if (array[i] == choice)
                return true;
        }
        return false;

    }

}
