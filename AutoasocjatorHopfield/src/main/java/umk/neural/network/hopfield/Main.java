package umk.neural.network.hopfield;

import javax.swing.SwingUtilities;

/**
 *
 * @author winx
 */
public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUIFrame();
            }
        });
    }
}
