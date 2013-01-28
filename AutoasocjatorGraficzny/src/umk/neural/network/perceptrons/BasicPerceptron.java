package umk.neural.network.perceptrons;

import java.util.ArrayList;
import java.util.List;

import umk.neural.network.activators.Activator;

public class BasicPerceptron implements Perceptron, Cloneable {

	private Activator activator;

	private List<Double> weights;

	@Override
	public Activator getActivator() {
		return activator;
	}

	@Override
	public void setActivator(Activator activator) {
		this.activator = activator;
	}

	@Override
	public List<Double> getWeights() {
		return weights;
	}

	@Override
	public void setWeights(List<Double> weights) {
		this.weights = weights;
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
		List<Double> newWeights = new ArrayList<Double>();
		for (int i = 0; i < weights.size(); i++) {
			newWeights.add(weights.get(i));
		}
		p.setWeights(newWeights);
		return p;
	}
}
