package umk.neural.network.perceptron;

import java.util.List;

import umk.neural.network.activators.Activator;

public interface Perceptron {

	double eval(List<Double> in);

	void setWeights(List<Double> weights);

	List<Double> getWeights();

	void setActivator(Activator activator);

	Activator getActivator();

	BasicPerceptron clone();

}
