package system;

import games.Blackjack;
import games.Lotto;
import games.Roulette;
import games.Slots;
import others.JFrameGraphics;
import system.Credit;
import system.Level;
import system.Profile;
import system.Security;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener{

    JFrame frame;
    JPanel panel;

    JButton rouletteGUI, lottoGUI, slotsGUI, blackjackGUI, shopGUI, tournamentsGUI, settingsGUI;
    JButton exitButton;

    JTextField levelField, requiredExpField, creditField, usernameField;

    Profile p = Profile.getInstance();
    Credit credit = p.getCredit();
    Level level = p.getLevel();

    public GUI() {

        frame = new JFrame();
        frame.setSize(1024, 512);
        //frame.setTitle("CasinoGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        ImageIcon logo = new ImageIcon("images/cglogo.png");
        frame.setIconImage(logo.getImage());
        frame.getContentPane().setBackground(Color.BLACK);

        rouletteGUI = new JButton("Roulette");
        lottoGUI = new JButton("Lotto");
        slotsGUI = new JButton("Slots");
        blackjackGUI = new JButton("Blackjack");
        tournamentsGUI = new JButton("Tournaments");
        shopGUI = new JButton("Shop");
        settingsGUI = new JButton("Log out");

        exitButton = new JButton("EXIT");

        //rouletteGUI.setBounds(200, 300, 200, 100);
        //lottoGUI.setBounds(300, 150, 200, 100);
        //slotsGUI.setBounds(600, 300, 200, 100);
        //blackjackGUI.setBounds(550, 150, 200, 100);
        exitButton.setBounds(5, 390, 105, 90);
        settingsGUI.setBounds(900, 390, 105, 90);

        rouletteGUI.addActionListener(this);
        lottoGUI.addActionListener(this);
        slotsGUI.addActionListener(this);
        blackjackGUI.addActionListener(this);

        tournamentsGUI.addActionListener(this);
        shopGUI.addActionListener(this);
        settingsGUI.addActionListener(this);
        exitButton.addActionListener(this);

        levelField = new JTextField();
        requiredExpField = new JTextField();
        creditField = new JTextField();
        usernameField = new JTextField();
        levelField.setBounds(5, 5, 225, 25);
        requiredExpField.setBounds(5, 40, 225, 25);
        creditField.setBounds(800, 5, 200, 25);
        usernameField.setBounds(400, 50, 200, 25);
        levelField.setEditable(false);
        requiredExpField.setEditable(false);
        creditField.setEditable(false);
        usernameField.setEditable(false);

        levelField.setText("Level: " + level.getLevelNumber() + " | Experience: " + String.format("%.2f", level.getExperienceValue()) + " " + level.getExperienceLetter());
        requiredExpField.setText("Experience required: " + String.format("%.2f", level.getRequiredEXP().getNumber()) + " " + level.getRequiredEXP().getLetter());
        creditField.setText("Credit: " + String.format("%.2f", credit.getNumber()) + " " + credit.getLetter());
        usernameField.setText("Username: " + Security.username);

        panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setBounds(200, 100,600, 200);
        panel.setLayout(new GridLayout(2, 4, 25, 25));

        panel.add(new JLabel());
        panel.add(blackjackGUI);
        panel.add(slotsGUI);
        panel.add(new JLabel());
        panel.add(rouletteGUI);
        panel.add(tournamentsGUI);
        panel.add(shopGUI);
        panel.add(lottoGUI);

        frame.add(panel);
        frame.add(levelField);
        frame.add(requiredExpField);
        //frame.add(rouletteGUI);
        //frame.add(lottoGUI);
        //frame.add(slotsGUI);
        //frame.add(blackjackGUI);

        frame.add(creditField);
        //frame.add(usernameField);

        frame.add(settingsGUI);
        frame.add(exitButton);

        frame.setTitle("CasinoGame: " + Security.username);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exitButton) {
           frame.dispose();
           return;
        } else if (e.getSource() == settingsGUI) {
            frame.dispose();
            Profile.setInstance();
            new Security();
            return;
        }


        if (e.getSource() == rouletteGUI) {
            frame.dispose();
            new Roulette();
        } else if (e.getSource() == lottoGUI) {
            frame.dispose();
            new Lotto();
        } else if (e.getSource() == slotsGUI) {
            frame.dispose();
            new Slots();
        } else if (e.getSource() == blackjackGUI) {
            frame.dispose();
            new Blackjack();
        } else {
            JOptionPane.showMessageDialog(null, "This is coming soon!");
        }

    }
}

