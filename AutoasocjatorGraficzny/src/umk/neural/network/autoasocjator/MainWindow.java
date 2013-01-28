package umk.neural.network.autoasocjator;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import umk.neural.network.perceptrons.Perceptron;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 44202514566339254L;

	private PatternClassifier patternClassifier;

	private JPanel controlPanelContainer = new JPanel();
	private Patterns patterns = new Patterns();
	private JList patternsList = new JList(patterns);
	private JPatternPresenter patternEditor;
	private JPatternPresenter patternMatcher = new JPatternPresenter();

	public static void main(String[] args) {
		new MainWindow();
	}

	private PixelPattern createPattern() {
		return new PixelPattern(10, 10);
	}

	public MainWindow() {
		super("AutoasocjatorGraficzny");
		buildUI();
		pack();
		setSize(640, 480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void buildUI() {
		setLayout(new BorderLayout());
		JPanel presentersPanel = new JPanel();
		presentersPanel.setLayout(new BoxLayout(presentersPanel, BoxLayout.X_AXIS));
		patternEditor = new JPatternPresenter();
		presentersPanel.add(patternEditor);
		presentersPanel.add(Box.createHorizontalStrut(5));
		presentersPanel.add(patternMatcher);
		add(presentersPanel);
		controlPanelContainer.setLayout(new BoxLayout(controlPanelContainer, BoxLayout.Y_AXIS));
		add(controlPanelContainer, BorderLayout.EAST);
		buildControlPanel();
	}

	private void buildControlPanel() {
		JScrollPane patternsScroller = new JScrollPane(patternsList);
		patternsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					PixelPattern pattern = (PixelPattern) patternsList.getSelectedValue();
					if (pattern != null)
						patternEditor.setPattern(pattern);
				} catch (IndexOutOfBoundsException ex) {
				}
			}
		});
		controlPanelContainer.add(patternsScroller);
		JButton newPatern = new JButton("New patern");
		newPatern.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				patternEditor.setPattern(createPattern());
			}
		});
		controlPanelContainer.add(newPatern);
		JButton addPattern = new JButton("Add pattern");
		addPattern.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PixelPattern pattern = patternEditor.getPattern();
				if (patterns.hasPattern(pattern)) {
					JOptionPane.showMessageDialog(MainWindow.this, "Wzorzec już jest dodany");
				} else {
					String name = JOptionPane.showInputDialog(MainWindow.this, "Nazwa wzorca: ");
					if (name == null) {
						return;
					}
					pattern.setName(name);
					patterns.addPattern(pattern);
				}
			}
		});
		controlPanelContainer.add(addPattern);
		JButton delPattern = new JButton("Del pattern");
		delPattern.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = patternsList.getSelectedIndex();
				if (index == -1) {
					return;
				}
				patterns.delPattern(index);
			}
		});
		controlPanelContainer.add(delPattern);

		JButton learn = new JButton("Learn");
		learn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String input = JOptionPane.showInputDialog(MainWindow.this, "Ilosc iteracjii: ");
					if (input == null) {
						return;
					}
					int iterations = Integer.parseInt(input);
					input = JOptionPane.showInputDialog(MainWindow.this,
							"Poziom szumów: ");
					if (input == null) {
						return;
					}
					double noise = Double.parseDouble(input);
					learnAll(iterations, noise);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(MainWindow.this, "Not a number");
				}
			}

		});
		controlPanelContainer.add(learn);
		JButton check = new JButton("Check");
		check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				check();
			}

		});
		controlPanelContainer.add(check);

		JButton swap = new JButton("Swap");
		swap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PixelPattern editorPattern = patternEditor.getPattern();
				PixelPattern matcherPattern = patternMatcher.getPattern();
				patternEditor.setPattern(matcherPattern);
				patternMatcher.setPattern(editorPattern);
			}

		});
		//controlPanelContainer.add(swap);

		JButton swapCheck = new JButton("Check & Swap");
		swapCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				check();
				PixelPattern editorPattern = patternEditor.getPattern();
				PixelPattern matcherPattern = patternMatcher.getPattern();
				patternEditor.setPattern(matcherPattern);
				patternMatcher.setPattern(editorPattern);
			}

		});
		//controlPanelContainer.add(swapCheck);

		JButton swapCheckX = new JButton("Check & Swap 10x");
		swapCheckX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < 10; i++) {
					check();
					PixelPattern editorPattern = patternEditor.getPattern();
					PixelPattern matcherPattern = patternMatcher.getPattern();
					patternEditor.setPattern(matcherPattern);
					patternMatcher.setPattern(editorPattern);
				}
			}

		});
		//controlPanelContainer.add(swapCheckX);

		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					FileDialog saveDialog = new FileDialog(MainWindow.this, "Save patterns", FileDialog.SAVE);
					saveDialog.setVisible(true);
					File file = new File(saveDialog.getFile());
					FileOutputStream fos = new FileOutputStream(file);
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(patterns);
					oos.flush();
					oos.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		//controlPanelContainer.add(save);

		JButton load = new JButton("Load");
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					FileDialog loadDialog = new FileDialog(MainWindow.this, "Save patterns", FileDialog.LOAD);
					loadDialog.setVisible(true);
					File file = new File(loadDialog.getFile());
					FileInputStream fis = new FileInputStream(file);
					ObjectInputStream ois = new ObjectInputStream(fis);
					patterns = (Patterns) ois.readObject();
					patternsList.setModel(patterns);
					ois.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		//controlPanelContainer.add(load);
	}

	private void learnAll(int parseInt, double noise) {
		patternClassifier = new PatternClassifier(100);
		patternClassifier.learn(patterns, parseInt, noise);
	}

	private void check() {
		if (patternClassifier == null) {
			return;
		}
		List<Perceptron> perceptrons = patternClassifier.getPerceptrons();
		if (perceptrons == null) {
			return;
		}

		PixelPattern inputPattern = patternEditor.getPattern();

		PixelPattern outputPattern = new PixelPattern(10, 10);

		for (int i = 0; i < inputPattern.getPixels().size(); i++) {
			List<Double> input = patternClassifier.patternToInput(inputPattern);
			double val = perceptrons.get(i).eval(input);
			if (val > patternClassifier.getThreshold()) {
				outputPattern.setPixel(i, true);
			} else {
				outputPattern.setPixel(i, false);
			}
		}

		patternMatcher.setPattern(outputPattern);
	}
}
