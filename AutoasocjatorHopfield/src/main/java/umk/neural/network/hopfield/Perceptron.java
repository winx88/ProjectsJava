package umk.neural.network.hopfield;

/**
 *
 * @author winx
 */
public class Perceptron {

    private double[] waga;
    private int numerPerceptrona;
    private int sigma;

    public Perceptron(int numer, int size) {
        numerPerceptrona = numer;
        waga = new double[size];
    }

    public int getSigma() {
        return sigma;
    }

    public void setSigma(int sigma) {
        this.sigma = sigma;
    }

    public double[] getWaga() {
        return waga;
    }

    public void setWaga(double[] w) {
        for (int i = 0; i < w.length; i++) {
            waga[i] = w[i];
        }
    }

    public void funkcjaAktywacji(Perceptron[] perceptrony) {
        double suma = 0;
        for (int j = 0; j < waga.length; j++) {
            if (j != numerPerceptrona) {
                suma += perceptrony[j].getSigma() * waga[j];
            }
        }
        if (suma >= 0) {
            sigma = 1;
        } else if (suma < 0) {
            sigma = -1;
        }
    }
}
