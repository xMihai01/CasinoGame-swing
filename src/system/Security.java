package system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Security implements ActionListener {

    JFrame loginFrame;
    JPasswordField passwordField;
    JTextField usernameField;
    JButton loginButton, exitButton;
    JLabel usernameLabel, passwordLabel;

    String password;
    public static boolean loggedIn;
    public static String username;

    public Security() {

        loginFrame = new JFrame();
        loginFrame.setTitle("Login to continue to the CasinoGame");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 160);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setResizable(false);
        loginFrame.setLayout(null);


        usernameField = new JTextField("");
        passwordField = new JPasswordField("");
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");

        usernameLabel.setBounds(70, 5, 60, 25);
        usernameField.setBounds(135, 5, 125, 25);
        passwordLabel.setBounds(70,40, 60, 25);
        passwordField.setBounds(135, 40, 125, 25);

        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");
        loginButton.setBounds(40, 85, 125, 30);
        exitButton.setBounds(215, 85, 125, 30);
        loginButton.addActionListener(this);
        exitButton.addActionListener(this);

        loginFrame.add(usernameLabel);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(usernameField);
        loginFrame.add(loginButton);
        loginFrame.add(exitButton);

        loadGame();
        loggedIn = false;
        loginFrame.setVisible(true);

    }


    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exitButton) {
            loginFrame.dispose();
            return;
        }

        if (usernameField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please insert an username!");
            return;
        } else if (passwordField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Password can not be empty!");
            return;
        }

        if (e.getSource() == loginButton && !usernameField.getText().equals(""))
            if(!new File("userdata/" + usernameField.getText()).mkdirs()) {
                // Login
                try{
                    File setPassword = new File("userdata/" + usernameField.getText()+ "/" + usernameField.getText() + "_password.txt");
                    Scanner scanner = new Scanner(setPassword);
                    if (scanner.hasNextLine()) {
                        password = scanner.nextLine();
                        if (password.equals(passwordField.getText())) {
                            loggedIn = true;
                            username = usernameField.getText();
                            loginFrame.dispose();
                            new GUI();
                            return;
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect password!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (IOException error) {
                    JOptionPane.showMessageDialog(null, "An error occurred while logging in!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    error.printStackTrace();
                }
            } else {
                // Register
               try {
                   File setPassword = new File("userdata/" + usernameField.getText()+ "/" + usernameField.getText() + "_password.txt");
                   if (setPassword.createNewFile()) {
                       FileWriter writePassword = new FileWriter("userdata/"+ usernameField.getText() + "/" + usernameField.getText() + "_password.txt");
                       password = passwordField.getText();
                       writePassword.write(password);
                       writePassword.close();
                       loggedIn = true;
                       username = usernameField.getText();
                       loginFrame.dispose();
                       new GUI();
                       return;
                   }
               } catch (IOException error) {
                   JOptionPane.showMessageDialog(null, "An error occurred while registering!", "ERROR", JOptionPane.ERROR_MESSAGE);
               }
            }


    }

    void loadGame() {

        boolean userdataFolder =  new File("userdata").mkdirs();
        //boolean cacheFolder = new File("cache").mkdirs();


    }

}
