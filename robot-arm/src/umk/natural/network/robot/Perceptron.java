package umk.natural.network.robot;


public class Perceptron {

    public double[] waga;
    public double[] wejscie;
    public double suma;

    public Perceptron(Przyklad p, double[] waga) {
        this.waga = new double[p.getPunkt().length];
        wejscie = new double[p.getPunkt().length];
        for (int i = 0; i < p.getPunkt().length; i++) {
            wejscie[i] = p.getPunkt()[i];
            this.waga[i] = waga[i];
        }
    }

    public double funkcjaAktywacji() {
        double wynik;
        double suma = 0;
        for (int i = 0; i < waga.length; i++) {
            suma += waga[i] * wejscie[i];
        }
        this.suma = suma;
        wynik = 1 / (1 + Math.exp(-suma));
        return wynik;
    }
}
