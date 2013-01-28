package umk.neural.network.autoasocjator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import umk.neural.network.activators.Activator;
import umk.neural.network.activators.IdentityActivator;
import umk.neural.network.perceptrons.BasicPerceptron;
import umk.neural.network.perceptrons.Perceptron;

public class PatternClassifier {

	private List<Perceptron> perceptrons = new ArrayList<Perceptron>();

	private Random random = new Random();

	private double threshold = 0;
	private double onValue = 1;
	private double offValue = -1;

	public PatternClassifier(int inputCount) {
		Activator activator = new IdentityActivator();

		// inicjalizacja (perceptronCount) perceptronów z (inputCount) wagami
		for (int i = 0; i < inputCount; i++) {
			Perceptron perceptron = new BasicPerceptron();
			perceptron.setActivator(activator);
			List<Double> weights = new ArrayList<Double>();
			for (int j = 0; j < inputCount; j++) {
				weights.add(0d);
			}
			perceptron.setWeights(weights);
			perceptrons.add(perceptron);
		}
	}

	public void learn(Patterns patterns, int iterations, double noise) {
		int currentSuccessCount = 0;
		for (int j = 0; j < perceptrons.size(); j++) {
			// store i-th perceptron for manipulation
			Perceptron tempPerceptron = perceptrons.get(j).clone();
			int bestSuccessCount = 0;

			currentSuccessCount = 0;
			for (int i = 0; i < iterations; i++) {
				int patternIndex = random.nextInt(patterns.getSize());
				PixelPattern orginalPattern = patterns.get(patternIndex);
				PixelPattern craftedPattern = craftPattern(orginalPattern, noise);

				double val = tempPerceptron.eval(patternToInput(craftedPattern));
				Boolean pixel = val > threshold ? true : false;
				if (pixel.equals(orginalPattern.getPixel(j))) {
					currentSuccessCount++;
				} else {
					currentSuccessCount = 0;
					// get the best so far
					tempPerceptron = perceptrons.get(j).clone();
					for (int w = 0; w < tempPerceptron.getWeights().size(); w++) {
						double newWeight = tempPerceptron.getWeights().get(w) + 0.01;
						newWeight *= orginalPattern.getPixel(w).equals(orginalPattern.getPixel(j)) ? 1 : -1;
						tempPerceptron.getWeights().set(w, newWeight);
					}
				}

				if (currentSuccessCount >= bestSuccessCount) {
					bestSuccessCount = currentSuccessCount;
					// store the best so far
					perceptrons.set(j, tempPerceptron);
				}
			}
		}
	}

	/**
	 * Converts boolean-based pattern to a list of doubles
	 * 
	 * @param pixelPattern
	 * @return
	 */
	public List<Double> patternToInput(PixelPattern pixelPattern) {
		List<Double> input = new ArrayList<Double>();
		for (int i = 0; i < pixelPattern.getPixels().size(); i++) {
			if (pixelPattern.getPixel(i)) {
				input.add(onValue);
			} else {
				input.add(offValue);
			}
		}
		return input;
	}

	/**
	 * Converts list of doubles to boolean-based pattern
	 * 
	 * @param output
	 * @return
	 */
	public PixelPattern inputToPattern(List<Double> output) {
		PixelPattern pixelPattern = new PixelPattern(10, 10);
		for (int i = 0; i < output.size(); i++) {
			pixelPattern.setPixel(i, output.get(i) > threshold ? true : false);
		}
		return pixelPattern;
	}

	/**
	 * Creates a copy of a pattern with randomly changed pixels
	 * 
	 * @param pixelPattern
	 * @param pixelsToDestroy
	 *            0 to not modify
	 * @return
	 */
	private PixelPattern craftPattern(PixelPattern pixelPattern, double noise) {
		PixelPattern newPattern = pixelPattern.copy();
		if (noise <= 0 ) {
			return newPattern;
		}
		for (int i = 0; i < newPattern.getPixels().size(); i++) {
			if (random.nextDouble() < noise) {
				newPattern.setPixel(i, random.nextBoolean());
			}
		}
		return newPattern;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public void setOffValue(double offValue) {
		this.offValue = offValue;
	}

	public void setOnValue(double onValue) {
		this.onValue = onValue;
	}

	public double getOffValue() {
		return offValue;
	}

	public double getOnValue() {
		return onValue;
	}

	public List<Perceptron> getPerceptrons() {
		return perceptrons;
	}

	public void setPerceptrons(List<Perceptron> perceptrons) {
		this.perceptrons = perceptrons;
	}
}
