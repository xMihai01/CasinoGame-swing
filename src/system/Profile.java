package system;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Profile {

    //public double credit;
    Credit credit = new Credit();
    Level level = new Level();
    public static String username = "";

    int rouletteGames, lottoGames, slotsGames, blackjackGames;

    private static Profile instance = null;

    private Profile() {

        username = Security.username;
        credit.setNumber(1000);
        credit.setLetter('a');

        try {
            File myObj = new File("userdata/" + username + "/" + username + "_credit.txt");
            if (myObj.createNewFile()) {
                FileWriter writer = new FileWriter("userdata/" + username + "/" + username + "_credit.txt");
                writer.write("1000\n");
                writer.write("a");
            } else {

                Scanner scanner = new Scanner(myObj);
                if (scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    credit.setNumber(Double.parseDouble(data));
                }
                if (scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    credit.setLetter(data.charAt(0));
                    scanner.close();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        rouletteGames = 0; slotsGames = 0; lottoGames = 0; blackjackGames = 0;
    }

    public static Profile getInstance() {
        if (instance == null) {
            instance = new Profile();
        }
        return instance;
    }

    public void updateCredit(Credit cr) {

        try {

            PrintWriter writer = new PrintWriter("userdata/" + username + "/" + username + "_credit.txt");
            writer.write(Double.toString(cr.getNumber()) + "\n");
            writer.write(Character.toString(cr.getLetter()));
            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }

        if (credit.getNumber() == 0 && credit.getLetter() == 'a') {
            credit.addCredit(new Credit(100, 'a'));
            updateCredit(credit);
        }
    }

    public Credit getCredit() {

        File file = new File("userdata/" + username + "/" + username + "_credit.txt");

        try {
            Scanner scanner = new Scanner(file);
            if (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                credit.setNumber(Double.parseDouble(data));
            }
            if (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                credit.setLetter(data.charAt(0));
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("An error occurred during getCredit scanning");
            e.printStackTrace();
        }

        return credit;

    }

    public void updateLevel(Level lvl) {

        try {

            PrintWriter writer = new PrintWriter("userdata/" + Profile.username + "/" + Profile.username + "_level.txt");
            writer.write(Integer.toString(lvl.getLevelNumber()) + "\n" + Double.toString(lvl.getExperienceValue()) + "\n");
            writer.write(Character.toString(lvl.getExperienceLetter()));
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while updating credit", "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public Level getLevel() {

        File file = new File("userdata/" + Profile.username + "/" + Profile.username + "_level.txt");

        try {
            Scanner scanner = new Scanner(file);
            level.setLevelNumber(Integer.parseInt(scanner.nextLine()));
            level.setExperienceValue(Double.parseDouble(scanner.nextLine()));
            level.setExperienceLetter(scanner.nextLine().charAt(0));
            scanner.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred during system.Profile.getLevel scanning!", "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return level;
    }

    public static void setInstance() {
        instance = null;
    }
}
