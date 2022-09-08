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

public class Blackjack implements ActionListener {

    JFrame blackjackFrame;
    JPanel blackjackPlayerCardPanel, blackjackDealerCardPanel;

    Profile p = Profile.getInstance();
    Credit credit = p.getCredit();
    Credit bet = new Credit();
    Level level = p.getLevel();
    
    JButton exitButton, playButton, hitButton, standButton;

    JTextField setBetField, messageField, statusField, creditField, setBetLetterField, creditLetterField;

    JLabel creditLabel, betLabel;

    ImageIcon[][] cardsImages = new ImageIcon[14][4];
    JLabel[] playerCards = new JLabel[5];
    JLabel[] dealerCards = new JLabel[5];


    Random random = ThreadLocalRandom.current();
    int playerPos, dealerPos, playerPoints, dealerPoints, aceP, aceD;
    boolean isGameRunning;

    public Blackjack(){

        blackjackFrame = new JFrame();
        blackjackFrame.setTitle("Loading blackjack...");
        blackjackFrame.setSize(1024, 512);
        blackjackFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        blackjackFrame.setResizable(false);
        blackjackFrame.setLocationRelativeTo(null);
        blackjackFrame.setLayout(null);

        blackjackPlayerCardPanel = new JPanel();
        blackjackDealerCardPanel = new JPanel();
        blackjackPlayerCardPanel.setBounds(100, 200, 750, 125);
        blackjackDealerCardPanel.setBounds(100, 340, 750, 125);
        blackjackPlayerCardPanel.setLayout(new GridLayout(1, 5, 25, 25));
        blackjackDealerCardPanel.setLayout(new GridLayout(1, 5, 25, 25));

        setBetField = new JTextField();
        messageField = new JTextField("You are currently playing games.Blackjack! Place a bet and click play to start!");
        creditField = new JTextField(String.format("%.2f", credit.getNumber()));
        statusField = new JTextField("");
        setBetLetterField = new JTextField(Character.toString(credit.getLetter()));
        creditLetterField = new JTextField(Character.toString(credit.getLetter()));


        exitButton = new JButton("RETURN");
        playButton = new JButton("Play");
        hitButton = new JButton("HIT");
        standButton = new JButton("STAND");
        exitButton.setBounds(900, 0, 105, 100);
        playButton.setBounds(600, 50, 100, 75);
        hitButton.setBounds(600, 50, 100, 75);
        standButton.setBounds(710, 50, 100, 75);
        exitButton.addActionListener(this);
        playButton.addActionListener(this);
        hitButton.addActionListener(this);
        standButton.addActionListener(this);
        standButton.setVisible(false);
        hitButton.setVisible(false);

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

        blackjackFrame.add(blackjackPlayerCardPanel);
        blackjackFrame.add(blackjackDealerCardPanel);
        blackjackFrame.add(betLabel);
        blackjackFrame.add(setBetField);
        blackjackFrame.add(messageField);
        blackjackFrame.add(creditLabel);
        blackjackFrame.add(creditField);
        blackjackFrame.add(exitButton);
        blackjackFrame.add(playButton);
        blackjackFrame.add(hitButton);
        blackjackFrame.add(standButton);
        blackjackFrame.add(setBetLetterField);
        blackjackFrame.add(creditLetterField);
        blackjackFrame.add(statusField);

        blackjackFrame.setVisible(true);
        playerPos = 0; playerPoints = 0; dealerPoints = 0; dealerPos = 0; aceP = 0; aceD = 0;
        isGameRunning = false;


        for (int i = 1; i<14; i++) {

            cardsImages[i][0] = new ImageIcon(new ImageIcon("images/" + Integer.toString(i+1) + "_of_clubs.png").getImage().getScaledInstance(100, 125, Image.SCALE_DEFAULT));
            cardsImages[i][1] = new ImageIcon(new ImageIcon("images/" + Integer.toString(i+1) + "_of_diamonds.png").getImage().getScaledInstance(100, 125, Image.SCALE_DEFAULT));
            cardsImages[i][2] = new ImageIcon(new ImageIcon("images/" + Integer.toString(i+1) + "_of_hearts.png").getImage().getScaledInstance(100, 125, Image.SCALE_DEFAULT));
            cardsImages[i][3] = new ImageIcon(new ImageIcon("images/" + Integer.toString(i+1) + "_of_spades.png").getImage().getScaledInstance(100, 125, Image.SCALE_DEFAULT));

        }

        for (int i = 0; i<5; i++) {

            int randomNumber = random.nextInt(12) +2;
            int randomnr = random.nextInt(4);
            int randomNumber2 = random.nextInt(12) +2;
            int randomnr2 = random.nextInt(4);

            playerCards[i] = new JLabel();
            playerCards[i].setIcon(cardsImages[randomNumber][randomnr]);
            dealerCards[i] = new JLabel();
            dealerCards[i].setIcon(cardsImages[randomNumber2][randomnr2]);
            blackjackPlayerCardPanel.add(playerCards[i]);
            blackjackDealerCardPanel.add(dealerCards[i]);
        }

        blackjackFrame.setTitle("CasinoGame: games.Blackjack");

    }
    
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exitButton) {
            blackjackFrame.dispose();
            new GUI();
            return;
        }
        if (e.getSource() == playButton) {
            if (Credit.verifyBet(setBetField.getText(), setBetLetterField.getText(), credit, bet, messageField)) {
                //blackjackDealerCardPanel.removeAll();
                for (int i = 0; i<5; i++) {
                    dealerCards[i].setIcon(null);
                  //  playerCards[i].setIcon(null);
                }
                blackjackDealerCardPanel.updateUI();
                generatePlayerCards();
                isGameRunning = true;
                hitButton.setVisible(true);
                standButton.setVisible(true);
                playButton.setVisible(false);
                statusField.setText("");
            }
        }
        if (e.getSource() == hitButton && isGameRunning) {
            generateNewPlayerCard();
            if (playerPoints >= 21) {
                generateDealerCards();
                checkWin();
                resetGame();
                return;
            }

            if (playerPos == 5) {
                generateDealerCards();
                checkWin();
                resetGame();
            }
        } else if (e.getSource() == standButton) {
            generateDealerCards();
            checkWin();
            resetGame();
        }

    }


    void generatePlayerCards() {

        //blackjackPlayerCardPanel.removeAll();

        for (int i = 0; i<5; i++) {
          //  dealerCards[i].setIcon(null);
            playerCards[i].setIcon(null);
        }

        for (int i = 0; i<2; i++) {
            int randomNumber = random.nextInt(13) +1;
            int randomColor = random.nextInt(2) +2;
            playerCards[i].setIcon(cardsImages[randomNumber][randomColor]);
            playerPoints += checkCard(randomNumber + 1, "player");
            playerPos++;
           // blackjackPlayerCardPanel.add(playerCards[i]);
        }
        blackjackPlayerCardPanel.updateUI();
        messageField.setText("Your Points: " + Integer.toString(playerPoints));

        if (playerPoints == 21) {

            generateDealerCards();
            checkWin();
            resetGame();

        }
    }

    void checkWin() {

        if ((playerPoints == dealerPoints) || (playerPoints >= 21 && dealerPoints >= 21)) {
            messageField.setText(messageField.getText() + "       Dealer Points: " + Integer.toString(dealerPoints));
            statusField.setText("TIED! The credit returned to your account");
        } else if (((playerPoints > dealerPoints) || dealerPoints > 21) && (playerPoints <= 21)) {
            messageField.setText(messageField.getText() + "       Dealer Points: " + Integer.toString(dealerPoints));
            statusField.setText("WIN! You won " + String.format("%.2f", bet.getNumber()) + bet.getLetter() + " CREDIT!");
            credit.addCredit(bet);
        } else if (((playerPoints < dealerPoints) || playerPoints > 21) && (dealerPoints <=21) ) {
            messageField.setText(messageField.getText() + "       Dealer Points: " + Integer.toString(dealerPoints));
            statusField.setText("LOSE! You lost " + String.format("%.2f", bet.getNumber()) + bet.getLetter() + " CREDIT!");
            credit.removeCredit(bet);
        } else {
            messageField.setText(messageField.getText() + "       Dealer Points: " + Integer.toString(dealerPoints));
            statusField.setText("An error has occurred!");
        }

        level.addLevel(credit, bet);
        p.updateLevel(level);
        p.updateCredit(credit);
        creditField.setText(String.format("%.2f", credit.getNumber()));
        creditLetterField.setText(Character.toString(credit.getLetter()));

    }

    void generateNewPlayerCard() {

        if (playerPoints <= 20 && aceP >= 1) {
            playerPoints-=10;
            aceP--;
        }

        int randomNumber = random.nextInt(13) +1;
        int randomColor = random.nextInt(2) +2;
        playerCards[playerPos].setIcon(cardsImages[randomNumber][randomColor]);
        playerPoints += checkCard(randomNumber + 1, "player");
        //blackjackPlayerCardPanel.add(playerCards[playerPos]);
        playerPos++;

        blackjackPlayerCardPanel.updateUI();
        messageField.setText("Your Points: " + playerPoints);


    }

    void generateDealerCards() {

        for (int i = 0; i < 2; i++) 
            generateNewDealerCard();
        

        while (dealerPoints <= 11 && dealerPos < 5) {

            if (dealerPoints <= 20 && aceD >= 1) {
                dealerPoints -= 10;
                aceD--;
            }

            generateNewDealerCard();

        }
        int randomAction = random.nextInt(6) + 11;

        if (dealerPos < 5 && dealerPoints < randomAction)
            generateNewDealerCard();

        blackjackDealerCardPanel.updateUI();
    }

    void generateNewDealerCard() {

        int randomNumber = random.nextInt(13) + 1;
        int randomColor = random.nextInt(2) + 2;
        dealerCards[dealerPos].setIcon(cardsImages[randomNumber][randomColor]);
        dealerPoints += checkCard(randomNumber + 1, "dealer");
       // blackjackDealerCardPanel.add(dealerCards[dealerPos]);
        dealerPos++;


    }
    int checkCard(int number, String playing) {
        if (number >= 2 && number <= 10)
            return number;
        else if (number > 10 && number != 11)
            return 10;
        else if (number == 11) {
            if (playing.equals("player")) {
                if (playerPoints < 11) {
                    aceP++;
                    return 11;
                } else return 1;
            } else {
                if (dealerPoints < 11) {
                    aceD++;
                    return 11;
                } else return 1;
            }

        }
        return -1000;
    }

    void resetGame() {

        dealerPoints = 0; playerPoints = 0; playerPos = 0; dealerPos = 0; aceP = 0; aceD = 0;
        isGameRunning = false;
        playButton.setVisible(true);
        standButton.setVisible(false);
        hitButton.setVisible(false);

    }

}
