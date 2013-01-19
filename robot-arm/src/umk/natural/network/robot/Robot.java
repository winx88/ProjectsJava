package umk.natural.network.robot;

import java.util.Random;


public class Robot {

    private int[] punktStartowyRamienia;
    private int dlugosc;
    private Przyklad[] przyklad;
    private Perceptron[] perceptron;
    private double[][] waga;
    private double wspolczynnikUczenia;

    public Robot(int[] punktStartowyRamienia, int dlugosc) {
        this.punktStartowyRamienia = punktStartowyRamienia;
        this.dlugosc = dlugosc;

        wspolczynnikUczenia = 0.3;

        this.tworzPrzyklady();
        this.ucz();
    }

    private void tworzPrzyklady() {
        double[] kat = new double[2];
        double[] punkt;

        przyklad = new Przyklad[10000];
        for (int i = 0; i < przyklad.length; i++) {
            kat[0] = new Random().nextInt(180);
            kat[1] = new Random().nextInt(180);
            punkt = obliczPunktyDlaPrzykladu(kat[0], kat[1]);

            przyklad[i] = new Przyklad(punkt[2], punkt[3], kat);
            normalizujPunkt(przyklad[i].getPunkt(), punktStartowyRamienia, dlugosc);
            normalizujKaty(przyklad[i].getKat(), 180, 180);
        }
    }

    public double[] obliczPunktyDlaPrzykladu(double alfa, double beta) {
        double[] punkt = new double[4];
        double gamma = (180 - beta) / 2;
        double delta = (90 - alfa - gamma);
        punkt[0] = punktStartowyRamienia[0] + dlugosc * Math.cos(Math.toRadians(delta + gamma));
        punkt[1] = punktStartowyRamienia[1] + dlugosc * Math.sin(Math.toRadians(delta + gamma));
        punkt[2] = punktStartowyRamienia[0] + 2 * dlugosc * Math.sin(Math.toRadians(beta / 2)) * Math.cos(Math.toRadians(delta));
        punkt[3] = punktStartowyRamienia[1] + 2 * dlugosc * Math.sin(Math.toRadians(beta / 2)) * Math.sin(Math.toRadians(delta));

        return punkt;
    }

    private void ucz() {
        int los;
        double[] u = new double[2];
        double[] u2 = new double[3];
        double[] delta = new double[7];
        double bladKwadratowy;
        perceptron = new Perceptron[7];

        waga = new double[7][];
        for (int i = 0; i < 7; i++) {
            if (i < 5) {
                waga[i] = new double[2];
            } else {
                waga[i] = new double[3];
            }
            for (int j = 0; j < waga[i].length; j++) {
                waga[i][j] = new Random().nextDouble();
            }
        }

        while (true) {
            los = new Random().nextInt(przyklad.length);

            perceptron[0] = new Perceptron(przyklad[los], waga[0]);
            perceptron[1] = new Perceptron(przyklad[los], waga[1]);
            u[0] = perceptron[0].funkcjaAktywacji();
            u[1] = perceptron[1].funkcjaAktywacji();
            perceptron[2] = new Perceptron(new Przyklad(u, przyklad[los].getKat()), waga[2]);
            perceptron[3] = new Perceptron(new Przyklad(u, przyklad[los].getKat()), waga[3]);
            perceptron[4] = new Perceptron(new Przyklad(u, przyklad[los].getKat()), waga[4]);
            u2[0] = perceptron[2].funkcjaAktywacji();
            u2[1] = perceptron[3].funkcjaAktywacji();
            u2[2] = perceptron[4].funkcjaAktywacji();
            perceptron[5] = new Perceptron(new Przyklad(u2, przyklad[los].getKat()), waga[5]);
            perceptron[6] = new Perceptron(new Przyklad(u2, przyklad[los].getKat()), waga[6]);

            delta[6] = (przyklad[los].getKat()[1] - perceptron[6].funkcjaAktywacji()) * perceptron[6].funkcjaAktywacji() * (1 - perceptron[6].funkcjaAktywacji());
            delta[5] = (przyklad[los].getKat()[0] - perceptron[5].funkcjaAktywacji()) * perceptron[5].funkcjaAktywacji() * (1 - perceptron[5].funkcjaAktywacji());
            delta[4] = (waga[6][2] * delta[6] + waga[5][2] * delta[5]) * perceptron[4].funkcjaAktywacji() * (1 - perceptron[4].funkcjaAktywacji());
            delta[3] = (waga[6][1] * delta[6] + waga[5][1] * delta[5]) * perceptron[3].funkcjaAktywacji() * (1 - perceptron[3].funkcjaAktywacji());
            delta[2] = (waga[6][0] * delta[6] + waga[5][0] * delta[5]) * perceptron[2].funkcjaAktywacji() * (1 - perceptron[2].funkcjaAktywacji());
            delta[1] = (waga[4][1] * delta[4] + waga[3][1] * delta[3] + waga[2][1] * delta[2]) * perceptron[1].funkcjaAktywacji() * (1 - perceptron[1].funkcjaAktywacji());
            delta[0] = (waga[4][0] * delta[4] + waga[3][0] * delta[3] + waga[2][0] * delta[2]) * perceptron[0].funkcjaAktywacji() * (1 - perceptron[0].funkcjaAktywacji());


            waga[6][2] = waga[6][2] + wspolczynnikUczenia * delta[6] * perceptron[4].funkcjaAktywacji();
            waga[6][1] = waga[6][1] + wspolczynnikUczenia * delta[6] * perceptron[3].funkcjaAktywacji();
            waga[6][0] = waga[6][0] + wspolczynnikUczenia * delta[6] * perceptron[2].funkcjaAktywacji();

            waga[5][2] = waga[5][2] + wspolczynnikUczenia * delta[5] * perceptron[4].funkcjaAktywacji();
            waga[5][1] = waga[5][1] + wspolczynnikUczenia * delta[5] * perceptron[3].funkcjaAktywacji();
            waga[5][0] = waga[5][0] + wspolczynnikUczenia * delta[5] * perceptron[2].funkcjaAktywacji();

            waga[4][1] = waga[4][1] + wspolczynnikUczenia * delta[4] * perceptron[1].funkcjaAktywacji();
            waga[4][0] = waga[4][0] + wspolczynnikUczenia * delta[4] * perceptron[0].funkcjaAktywacji();

            waga[3][1] = waga[3][1] + wspolczynnikUczenia * delta[3] * perceptron[1].funkcjaAktywacji();
            waga[3][0] = waga[3][0] + wspolczynnikUczenia * delta[3] * perceptron[0].funkcjaAktywacji();

            waga[2][1] = waga[2][1] + wspolczynnikUczenia * delta[2] * perceptron[1].funkcjaAktywacji();
            waga[2][0] = waga[2][0] + wspolczynnikUczenia * delta[2] * perceptron[0].funkcjaAktywacji();

            waga[1][1] = waga[1][1] + wspolczynnikUczenia * delta[1] * przyklad[los].getPunkt()[1];
            waga[1][0] = waga[1][0] + wspolczynnikUczenia * delta[1] * przyklad[los].getPunkt()[0];

            waga[0][1] = waga[0][1] + wspolczynnikUczenia * delta[0] * przyklad[los].getPunkt()[1];
            waga[0][0] = waga[0][0] + wspolczynnikUczenia * delta[0] * przyklad[los].getPunkt()[0];


            bladKwadratowy = Math.pow(przyklad[los].getKat()[1] - perceptron[6].funkcjaAktywacji(), 2) + Math.pow(przyklad[los].getKat()[0] - perceptron[5].funkcjaAktywacji(), 2);
            //System.out.println(bladKwadratowy);
            if (bladKwadratowy < 0.0000001) {
                break;
            }
        }
    }

    public double[] sprawdzaj(double[] klik) {
        double[] p = new double[2];
        p[0] = klik[0];
        p[1] = klik[1];
        normalizujPunkt(p, punktStartowyRamienia, dlugosc);

        double[] u = new double[2];
        double[] u2 = new double[3];
        perceptron[0] = new Perceptron(new Przyklad(p, p), waga[0]);
        perceptron[1] = new Perceptron(new Przyklad(p, p), waga[1]);
        u[0] = perceptron[0].funkcjaAktywacji();
        u[1] = perceptron[1].funkcjaAktywacji();
        perceptron[2] = new Perceptron(new Przyklad(u, u), waga[2]);
        perceptron[3] = new Perceptron(new Przyklad(u, u), waga[3]);
        perceptron[4] = new Perceptron(new Przyklad(u, u), waga[4]);
        u2[0] = perceptron[2].funkcjaAktywacji();
        u2[1] = perceptron[3].funkcjaAktywacji();
        u2[2] = perceptron[4].funkcjaAktywacji();
        perceptron[5] = new Perceptron(new Przyklad(u2, u2), waga[5]);
        perceptron[6] = new Perceptron(new Przyklad(u2, u2), waga[6]);
        double[] katy = new double[2];
        katy[0] = perceptron[5].funkcjaAktywacji();
        katy[1] = perceptron[6].funkcjaAktywacji();

        odnormalizujKaty(katy, 180, 180);

        double[] wynik;
        wynik = obliczPunktyDlaPrzykladu(katy[0], katy[1]);

        return wynik;
    }

    private void normalizujPunkt(double[] punkt, int[] punktStartowyRamienia, double dlugosc) {
        for (int i = 0; i < punkt.length; i++) {
            punkt[i] = 0.8 * (punkt[i] - (punktStartowyRamienia[i] - 2 * dlugosc)) / (4 * dlugosc) + 0.1;
        }
    }

    private void normalizujKaty(double[] kat, double zasiegAlfa, double zasiegBeta) {
        kat[0] = (0.8 * kat[0]) / zasiegAlfa + 0.1;
        kat[1] = (0.8 * kat[1]) / zasiegBeta + 0.1;
    }

    private void odnormalizujKaty(double[] kat, double zasiegAlfa, double zasiegBeta) {
        kat[0] = zasiegAlfa * (kat[0] - 0.1) / 0.8;
        kat[1] = zasiegBeta * (kat[1] - 0.1) / 0.8;
    }

    /**
     * @return the punktStartowyRamienia
     */
    public int[] getPunktStartowyRamienia() {
        return punktStartowyRamienia;
    }
}
