package umk.neural.network.arm.robot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	public Window() {
		super("RobotArm");
		initGui();
		pack();
		setSize(640, 480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	protected void initGui() {
		JSplitPane sp = new JSplitPane();
		sp.add(new RobotArmPresenter(), JSplitPane.LEFT);
		sp.add(initButtons(), JSplitPane.RIGHT);

		add(sp);
	}

	private JPanel initButtons() {
		JPanel panel = new JPanel();

		JButton learn = new JButton("Ucz");
		learn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String input = JOptionPane.showInputDialog(Window.this, "Enter number of iterations");
					if (input == null) {
						return;
					}
					int iterations = Integer.parseInt(input);

					RobotArm.net.startLearning(iterations);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(Window.this, "Not a number");
				}
			}
		});
		panel.add(learn);

		return panel;
	}
}
