package system;

import javax.swing.*;

public class Credit {

    double number;
    char letter;

    public Credit() {
        this.number = 1000;
        this.letter = 'a';
    }
    public Credit(double num, char let) {
        this.number = num;
        this.letter = let;
    }

    public double getNumber() {
        return number;
    }
    public char getLetter() {
        return letter;
    }
    public void setNumber(double number) {
        this.number = number;
    }
    public void setLetter(char letter) {
        this.letter = letter;
    }

    private void updateNumber(int diff) {

        if (diff > 0)
            for (int i = 0; i < diff; i++)
                this.number /= 1000;
        if (diff < 0)
            for (int i = 0; i > diff; i--)
                this.number *= 1000;

    }

    public void reviewNumber() {

        if (this.number < 0) {
            this.number = 0;
            this.letter = 'a';
            return;
        }

        while (this.number >= 1000000 && this.letter != 'z') {
            this.letter++;
            this.number /= 1000;
        }
        while (this.number < 1000 && this.letter != 'a') {
            this.letter--;
            this.number *= 1000;
        }
        
    }
    
    public void addCredit(Credit add) {

        Credit num = new Credit();
        Credit added = new Credit();
        added = add;

        if (this.letter > added.letter) {
            int diff = this.letter - added.letter;
            added.updateNumber(diff);
            num.letter = this.letter;
            num.number = this.number + added.number;
            num.reviewNumber();

        }
	else if (this.letter < added.letter) {
            int diff = added.letter - this.letter;
            this.updateNumber(diff);
            num.letter = added.letter;
            num.number = this.number + added.number;
            num.reviewNumber();

        }
	else {
            num.number = this.number + added.number;
            num.letter = this.letter;
            num.reviewNumber();

        }

        this.number = num.number;
        this.letter = num.letter;
        this.reviewNumber();

    }

    public void removeCredit(Credit remove) {

        Credit num = new Credit();
        Credit removed = new Credit();
        removed= remove;

        if (removed.letter > this.letter) {
            int diff = this.letter - removed.letter;
            removed.updateNumber(diff);
            num.letter = this.letter;
            num.number = this.number - removed.number;
            num.reviewNumber();

        }
	else if (removed.letter < this.letter) {
            int diff = removed.letter - this.letter;
            this.updateNumber(diff);
            num.letter = removed.letter;
            num.number = this.number - removed.number;
            num.reviewNumber();

        }
	else {
            num.letter = removed.letter;
            num.number = this.number - removed.number;
            num.reviewNumber();

        }

        this.number = num.number;
        this.letter = num.letter;
        this.reviewNumber();
    }

    public boolean compareCredit(Credit a, Credit b) {


       if (a.getLetter() > b.getLetter()) {
           return true;
        }  else if (a.getLetter() < b.getLetter()) {
            int diff = a.getLetter() - b.getLetter();
            b.updateNumber(diff);
            b.setLetter(a.getLetter());
        }

        if (a.getNumber() >= b.getNumber())
            return true;
        else
            return false;

    }

    public static boolean verifyBet(String str, String strLet, Credit credit, Credit bet, JTextField messageField) {

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

    }

    public Credit getPercentageOf(int number) {

        Credit perc = new Credit();

        perc.setNumber(this.number*0.01);
        perc.setLetter(this.letter);
        perc.reviewNumber();

        return perc;
    }

}
