package umk.neural.network.perceptrons;

import java.util.ArrayList;
import java.util.List;

import umk.neural.network.activators.Activator;

public interface Perceptron {

	double eval(List<Double> in);

	void setWeights(ArrayList<Double> weights);

	ArrayList<Double> getWeights();

	void setActivator(Activator activator);

	Activator getActivator();

	BasicPerceptron clone();

}
