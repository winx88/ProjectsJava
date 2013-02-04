/**
 * 
 */
package umk.neural.network.separator;

import javax.swing.SwingUtilities;

/**
 * @author winx
 * 
 */
public class MainWindow {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new BoardFrame();
			}
		});

	}

}
