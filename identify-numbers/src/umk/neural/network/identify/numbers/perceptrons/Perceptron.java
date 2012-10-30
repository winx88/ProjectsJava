package umk.neural.network.identify.numbers.perceptrons;

import umk.neural.network.identify.numbers.activators.Activator;

public interface Perceptron {

	double eval(double[] in);

	void setWeights(double[] weights);

	double[] getWeights();

	void setActivator(Activator activator);

	Activator getActivator();

}
