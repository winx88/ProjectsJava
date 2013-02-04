/**
 * 
 */
package umk.neural.network.separator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

import umk.neural.network.activators.BipolarActivator;
import umk.neural.network.perceptrons.BasicPerceptron;

/**
 * @author winx
 * 
 */
public class BoardFrame extends JFrame {

	/**
	 * 
	 */
	private BasicPerceptron perceprton = new BasicPerceptron();
	private BoardPanel boardPanel;
	private static final long serialVersionUID = 1L;

	public BoardFrame() {

		this.setTitle("Separator");
		this.setSize(440, 550);
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		boardPanel = new BoardPanel();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 5.0;
		c.weighty = 5.0;

		this.add(boardPanel, c);

		JButton learnButton = new JButton("Learn");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 15; // make this component tall
		c.gridx = 0;
		c.gridy = 1;
		learnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				learnTuples(boardPanel.getListOfPoint());
			}
		});
		this.add(learnButton, c);
		JButton checkButton = new JButton("Check");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 15; // make this component tall
		c.gridx = 0;
		c.gridy = 2;
		checkButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				check();

			}
		});
		this.add(checkButton, c);

		this.setVisible(true);

	}

	void learnTuples(List<Bpoint> listOfPoint) {

		

		Random r = new Random(System.nanoTime());
		
		
		BipolarActivator activator = new BipolarActivator();
		activator.setThreshold(r.nextDouble());
		perceprton.setActivator(activator);

		int ttl = 0;
		int record = 0;

		ArrayList<Double> saveWeight = new ArrayList<Double>();

		ArrayList<Double> listOfWeight = new ArrayList<Double>();
		listOfWeight.add(rangeRandom(r.nextDouble()));
		listOfWeight.add(rangeRandom(r.nextDouble()));

		perceprton.setWeights(listOfWeight);
		saveWeight = perceprton.getWeights();

		int klas = 0;
		int nklas = 0;
		double prog;

		for (int i = 0; i < 1000000; i++) {

			Bpoint randomPoint = listOfPoint.get(r.nextInt(listOfPoint.size()));

			ArrayList<Double> listOfXY = new ArrayList<Double>();
			listOfXY.add((double) (randomPoint.getP().x));
			listOfXY.add((double) (randomPoint.getP().y));

			double resultEval = perceprton.eval(listOfXY);

			if (resultEval != randomPoint.getButton()) {
				nklas++;
				ttl = 0;
				ArrayList<Double> listOfNewWegiht = new ArrayList<Double>();
				for (int j = 0; j < perceprton.getWeights().size(); j++) {
					double error = randomPoint.getButton() - resultEval;
					/*
					 * System.out.println(error); System.out.println(record);
					 */
					listOfNewWegiht.add(perceprton.getWeights().get(j) + 0.2
							* error * listOfXY.get(j));
					
					activator.setThreshold(r.nextDouble());
					perceprton.setActivator(activator);
				}
				perceprton.setWeights(listOfNewWegiht);
			} else {
				klas++;
				ttl++;
				if (ttl > record) {
					record = ttl;
					saveWeight = perceprton.getWeights();
				}

			}

		}
		
		int mismatched = 0;
		int matched = 0;
		
		for (Bpoint bpoint : listOfPoint) {
			ArrayList<Double> listOfXY = new ArrayList<Double>();
			listOfXY.add((double) (bpoint.getP().x));
			listOfXY.add((double) (bpoint.getP().y));

			double results = perceprton.eval(listOfXY);
			if (results == bpoint.getButton()) {
				matched++;
			} else {
				mismatched++;
			}

		}
		
		System.out.println("****************|STATISTIC|****************");
		System.out.println("mismatched: " + mismatched);
		System.out.println("matched: " + matched);
		System.out.println("*******************************************");
		
		List<Bpoint> listaEmpty = new ArrayList<Bpoint>();
		boardPanel.setListOfPoint(listaEmpty);
		boardPanel.repaint();

		System.err.println("Ilosc sklasyfikowanych: " + klas);
		System.err.println("Ilosc niesklasyfikowanych: " + nklas);

	}

	private double rangeRandom(double d) {
		if ((d * 2) > 1) {
			return d;
		} else {
			return -d;
		}
	}

	public void check() {
		List<Bpoint> listOfPoint = boardPanel.getListOfPoint();
		int mismatched = 0;
		int matched = 0;

		for (Bpoint bpoint : listOfPoint) {
			ArrayList<Double> listOfXY = new ArrayList<Double>();
			listOfXY.add((double) (bpoint.getP().x));
			listOfXY.add((double) (bpoint.getP().y));

			double results = perceprton.eval(listOfXY);
			if (results == bpoint.getButton()) {
				matched++;
			} else {
				mismatched++;
			}

		}

		System.out.println("****************|STATISTIC|****************");
		System.out.println("mismatched: " + mismatched);
		System.out.println("matched: " + matched);
		System.out.println("*******************************************");

	}
}
