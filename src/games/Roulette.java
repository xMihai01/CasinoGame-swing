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

public class Roulette implements ActionListener {

    JFrame rouletteFrame;

    Profile p = Profile.getInstance();

    JButton red, black, green, exitButton;
    JTextField setBetField, messageField, creditField, setBetLetterField, creditLetterField;
    Credit bet = new Credit();
    Level level = p.getLevel();
    Credit credit = p.getCredit();
  //  double credit = p.getCredit();

    Random random = ThreadLocalRandom.current();

    public Roulette() {

        rouletteFrame = new JFrame();
        rouletteFrame.setSize(1024, 512);
        rouletteFrame.setTitle("CasinoGame: games.Roulette");
        rouletteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        rouletteFrame.setResizable(false);
        rouletteFrame.setLocationRelativeTo(null);
        rouletteFrame.setLayout(null);

        setBetField = new JTextField();
        messageField = new JTextField("You are currently playing roulette! Place a bet and choose a color to start!");
        creditField = new JTextField(String.format("%.2f", credit.getNumber()));
        setBetLetterField = new JTextField(Character.toString(credit.getLetter()));
        creditLetterField = new JTextField(Character.toString(credit.getLetter()));

        creditLetterField.setEditable(false);
        messageField.setEditable(false);
        creditField.setEditable(false);

        red = new JButton("");
        black = new JButton("");
        green = new JButton("");
        exitButton = new JButton("RETURN");

        red.setBounds(225, 300, 100, 100);
        red.setBackground(Color.RED);
        green.setBounds(425, 300, 100, 100);
        green.setBackground(Color.GREEN);
        black.setBounds(625, 300, 100, 100);
        black.setBackground(Color.BLACK);
        exitButton.setBounds(900, 0, 105, 100);

        JLabel betLabel = new JLabel("BET: ");
        JLabel creditLabel = new JLabel("Available CREDIT: ");
        betLabel.setBounds(350, 100, 50, 25);
        creditLabel.setBounds(300, 50, 125, 25);
        creditField.setBounds(425, 50, 100, 25);
        creditLetterField.setBounds(530, 50, 25, 25);
        setBetField.setBounds(425, 100, 100, 25);
        setBetLetterField.setBounds(530, 100, 25, 25);
        messageField.setBounds(300, 200, 400, 50);

        red.addActionListener(this);
        black.addActionListener(this);
        green.addActionListener(this);
        exitButton.addActionListener(this);

        rouletteFrame.add(red);
        rouletteFrame.add(green);
        rouletteFrame.add(black);
        rouletteFrame.add(betLabel);
        rouletteFrame.add(setBetField);
        rouletteFrame.add(messageField);
        rouletteFrame.add(creditLabel);
        rouletteFrame.add(creditField);
        rouletteFrame.add(exitButton);
        rouletteFrame.add(setBetLetterField);
        rouletteFrame.add(creditLetterField);

        rouletteFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exitButton) {
            rouletteFrame.dispose();
            new GUI();
            return;
        }

        int randomNumber = random.nextInt(36);
        if (!Credit.verifyBet(setBetField.getText(), setBetLetterField.getText(), credit, bet, messageField)) {
            return;
        }
        int chosenColor = -1;

        if (e.getSource() == red) {
            chosenColor = 0;
        } else if (e.getSource() == black) {
            chosenColor = 1;
        } else if (e.getSource() == green) {
            chosenColor = 2;
        }

        if (checkRouletteWin(randomNumber, chosenColor)) {
            messageField.setText("You have won!");
            //credit = credit + bet;
            credit.addCredit(bet);
            if (chosenColor == 2) {
                bet.setNumber(bet.getNumber()*13);
                credit.addCredit(bet);
                //credit = credit + bet*13;
            }
        } else {
            messageField.setText("You have lost!");
          //  credit = credit - bet;
            credit.removeCredit(bet);
        }

        messageField.setText(messageField.getText() + " COLOR: " + getColor(randomNumber) + " (" + randomNumber + ")");

        level.addLevel(credit, bet);
        p.updateLevel(level);
        p.updateCredit(credit);
        creditField.setText(String.format("%.2f", credit.getNumber()));
        creditLetterField.setText(Character.toString(credit.getLetter()));


    }

    boolean checkRouletteWin(int color, int chosenColor) {

        if ((color%2 == 0 && color!= 0) && chosenColor == 0) {
            return true;
        } else if (color%2 == 1 && chosenColor == 1) {
            return true;
        } else return color == 0 && chosenColor == 2;
    }

    String getColor(int color) {
        if (color%2 == 0 && color != 0)
            return "RED";
        else if (color%2 == 1)
            return "BLACK";
        else
            return "GREEN";

    }

    /*boolean verifyBet(String str, String strLet) {

        try {
            bet.setNumber(Double.parseDouble(str));
            bet.setLetter(strLet.charAt(0));
            if (!credit.compareCredit(credit, bet)) {
                messageField.setText("You don't have enough credits or bet is invalid!");
                return false;
            } else if (bet.getLetter() < credit.getLetter()) {
                return true;
            }
            else {
                if (bet.getNumber() > 0 && credit.getNumber() >= bet.getNumber()) {
                    return true;
                } else {
                    messageField.setText("You don't have enough credits or bet is invalid!");
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            messageField.setText("Invalid bet! Please try again with a numerical value!");
            return false;
        }

    }*/

}
