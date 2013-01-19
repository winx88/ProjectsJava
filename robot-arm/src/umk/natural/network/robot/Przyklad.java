package umk.natural.network.robot;


public class Przyklad {

    private double[] punkt;
    private double[] kat;

    public Przyklad(double[] p, double[] k) {
        punkt = new double[p.length];
        kat = new double[k.length];
        for (int i = 0; i < k.length; i++) {
            kat[i] = k[i];
        }
        for (int i = 0; i < p.length; i++) {
            punkt[i] = p[i];
        }
    }

    public Przyklad(double x, double y, double[] k) {
        punkt = new double[2];
        punkt[0] = x;
        punkt[1] = y;
        kat = new double[k.length];
        for (int i = 0; i < k.length; i++) {
            kat[i] = k[i];
        }
    }

    /**
     * @return the punkt
     */
    public double[] getPunkt() {
        return punkt;
    }

    /**
     * @return the kat
     */
    public double[] getKat() {
        return kat;
    }
}
