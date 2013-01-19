package umk.natural.network.robot;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;


public class GUIFrame extends JFrame {

    public GUIFrame() {
        this.setTitle("Robot-arm");
        this.setSize(440, 550);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        int[] punktStartowyRamienia = new int[2];
        punktStartowyRamienia[0] = 230;
        punktStartowyRamienia[1] = 210;
        int dlugosc = 60;
        Robot robot = new Robot(punktStartowyRamienia, dlugosc);

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        GUIPanel robocikPanel = new GUIPanel(robot);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        this.add(robocikPanel, c);

        this.setVisible(true);
    }
}