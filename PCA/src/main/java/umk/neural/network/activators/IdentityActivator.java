package umk.neural.network.activators;

public class IdentityActivator implements Activator {

	@Override
	public double eval(double in) {
		return in;
	}

}
