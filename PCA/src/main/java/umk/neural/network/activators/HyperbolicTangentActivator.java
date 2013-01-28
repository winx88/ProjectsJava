package umk.neural.network.activators;

public class HyperbolicTangentActivator implements Activator {

	private double parameter;

	public double getParameter() {
		return parameter;
	}

	public void setParameter(double parameter) {
		this.parameter = parameter;
	}

	@Override
	public double eval(double in) {
		double x = Math.exp((-parameter) * in);
		return (1 - x) / (1 + x);
	}
}
