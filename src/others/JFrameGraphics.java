package others;



import javax.swing.*;
import java.awt.*;

import system.Security;
public class JFrameGraphics extends JPanel {

    public JFrameGraphics() {

        

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        System.out.println("D");
        Graphics2D g2 = (Graphics2D) g;

        g.setColor(Color.RED);
        g.drawString(Security.username , 400, 20);

    }

}
