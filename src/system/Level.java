package system;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Level {

    int levelNumber;
    Credit experience;


    Level() {

        levelNumber = 1;
        experience = new Credit(0, 'a');

        try {
            File file = new File("userdata/" + Security.username + "/" + Security.username + "_level.txt");
            if (file.createNewFile()) {
                FileWriter writer = new FileWriter("userdata/" + Security.username + "/" + Security.username + "_level.txt");
                writer.write("1\n0\na");
                writer.close();
            } else {
                Scanner scanner = new Scanner(file);
                levelNumber = Integer.parseInt(scanner.nextLine());
                experience.setNumber(Double.parseDouble(scanner.nextLine()));
                experience.setLetter(scanner.nextLine().charAt(0));
                scanner.close();
            }


        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while reading level!", "ERROR" ,JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    Level(int number, Credit exp) {

        this.levelNumber = number;
        this.experience =  new Credit(exp.getNumber(), exp.getLetter());

    }

    int getLevelNumber() {
        return this.levelNumber;
    }
    Credit getExperience() {
        return this.experience;
    }
    double getExperienceValue() {
        return this.experience.getNumber();
    }
    char getExperienceLetter() {
        return this.experience.getLetter();
    }
    void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }
    void setExperienceValue(double expValue) {
        this.experience.setNumber(expValue);
    }
    void setExperienceLetter(char ltr) {
        this.experience.setLetter(ltr);
    }

    public void addLevel(Credit credit, Credit bet) {

        Credit addXP = bet.getPercentageOf(1);
        experience.addCredit(addXP);

        Credit reqXP = getRequiredEXP();
        int a = 0;
        if (experience.compareCredit(experience, reqXP)) {
            levelNumber++;
            experience.removeCredit(reqXP);
            a++;
         //   levelRewards(credit, 1);
        }

        reqXP = getRequiredEXP();
        while (experience.compareCredit(experience, reqXP)) {
            levelNumber++;
            a++;
            experience.removeCredit(reqXP);
            reqXP = getRequiredEXP();
        }
        //if (a > 0)
           // levelRewards(credit, a);

    }

    /*void levelRewards(system.Credit credit, int ups) {

        system.Credit rewards = new system.Credit(0, 'a');
        for (int i = 0; i<ups; i++) {

            rewards.addCredit(new system.Credit((levelNumber-1) * Math.pow(2, (levelNumber-1) / 100), 'a'));
            rewards.reviewNumber();
        }
        credit.addCredit(rewards);
        if (ups < 10 || rewards.getNumber() < 999999)
            JOptionPane.showMessageDialog(null, "You leveled up " + ups + " times! You won " + String.format("%.2f", rewards.getNumber()) + " " + rewards.getLetter() + " CREDIT!");
        else JOptionPane.showMessageDialog(null, "You level up "+ ups + " times! A number of CREDITS were added to your account!");
    }*/
    public Credit getRequiredEXP() {

        if (levelNumber > 36340)
            return new Credit(1000, 'z');
        Credit base = new Credit(10, 'a');

        base.setNumber(base.getNumber()*Math.pow(1.05, (levelNumber)/10));
        base.reviewNumber();
        return base;
    }

}
