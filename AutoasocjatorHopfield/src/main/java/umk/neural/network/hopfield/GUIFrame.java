package umk.neural.network.hopfield;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 *
 * @author winx
 */
public class GUIFrame extends JFrame {

    public static final int WIDTH = 9;
    public static final int HEIGHT = 11;
    private JComboBox patternsComboBox;
    private GUIPanel editablePanel;
    private GUIPanel outcomePanel;
    private AutoasocjatorHopfielda autoasocjator;

    public GUIFrame() {
        this.setTitle("Hopfield");
        this.setSize(750, 385);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();

        autoasocjator = new AutoasocjatorHopfielda(WIDTH, HEIGHT);

        this.setVisible(true);
    }

    private void initComponents() {

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        editablePanel = new GUIPanel(WIDTH, HEIGHT, true);
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        this.add(editablePanel, c);

        outcomePanel = new GUIPanel(WIDTH, HEIGHT, false);
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        this.add(outcomePanel, c);


        JButton recogniseButton = new JButton("Rozpoznaj");
        recogniseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] t = buildDataForPerceptron(WIDTH, HEIGHT, editablePanel.getMatrix());
                int[] outcome = autoasocjator.recognise(t);
                outcomePanel.setMatrix(buildDataForPanel(WIDTH, HEIGHT, outcome));
                outcomePanel.repaint();
            }
        });
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.insets = new Insets(10, 10, 10, 10);
        this.add(recogniseButton, c);

        String[] patternsList = {"Wybierz rysunek", "buzka", "kwiat", "ludzik", "gwiazda"};
        patternsComboBox = new JComboBox(patternsList);
        patternsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pattern = patternsComboBox.getSelectedIndex() - 1;
                if (pattern >= 0) {
                    editablePanel.setMatrix(buildDataForPanel(WIDTH, HEIGHT, autoasocjator.getPattern(pattern)));
                    editablePanel.repaint();
                }
            }
        });
        c.gridx = 1;
        c.gridy = 0;
        this.add(patternsComboBox, c);

    }

    private int[] buildDataForPerceptron(int w, int h, boolean[][] m) {
        int l = 0;
        int[] t = new int[w * h];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (m[i][j]) {
                    t[l] = 1;

                } else {
                    t[l] = -1;
                }
                l++;
            }
        }
        return t;
    }

    private boolean[][] buildDataForPanel(int w, int h, int[] tab) {
        int l = 0;
        boolean[][] t = new boolean[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (tab[l] > 0) {
                    t[i][j] = true;

                } else {
                    t[i][j] = false;
                }
                l++;
            }
        }
        return t;
    }
}