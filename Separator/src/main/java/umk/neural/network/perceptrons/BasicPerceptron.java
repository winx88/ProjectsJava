package umk.neural.network.perceptrons;

import java.util.ArrayList;
import java.util.List;

import umk.neural.network.activators.Activator;

public class BasicPerceptron implements Perceptron, Cloneable {

	private Activator activator;

	private ArrayList<Double> weights;

	/**
	 * @return the weights
	 */
	public ArrayList<Double> getWeights() {
		return weights;
	}

	/**
	 * @param weights
	 *            the weights to set
	 */
	public void setWeights(ArrayList<Double> weights) {
		this.weights = weights;
	}

	@Override
	public Activator getActivator() {
		return activator;
	}

	@Override
	public void setActivator(Activator activator) {
		this.activator = activator;
	}

	private double compute(List<Double> in) {
		if (in.size() != weights.size()) {
			throw new IllegalArgumentException(
					"Input data size does not match weights size");
		}
		double sum = 0;
		for (int i = 0; i < in.size(); i++) {
			sum += in.get(i) * weights.get(i);
		}
		return sum;
	}

	@Override
	public double eval(List<Double> in) {
		return activator.eval(compute(in));
	}

	@Override
	public BasicPerceptron clone() {
		BasicPerceptron p = new BasicPerceptron();
		p.setActivator(activator);
		ArrayList<Double> newWeights = new ArrayList<Double>();
		for (int i = 0; i < weights.size(); i++) {
			newWeights.add(weights.get(i));
		}
		p.setWeights(newWeights);
		return p;
	}
}
