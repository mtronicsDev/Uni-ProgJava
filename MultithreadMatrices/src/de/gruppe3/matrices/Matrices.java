package de.gruppe3.matrices;

/**
 * @author Max
 */
public class Matrices {
    public static void main(String... args) {
        double[][] matA = {
                {12, 11, 10},
                { 9,  8,  7},
                { 6,  5,  4},
                { 3,  2,  1}};

        double[][] matB = {
                {1,  2,  3,  4},
                {5,  6,  7,  8},
                {9, 10, 11, 12}};

        double[][] result = multiply(matA, matB);

        for (double[] line : result) {
            for (double cell : line) {
                System.out.print(cell + ", ");
            }
            System.out.println();
        }
    }

    public static double[][] multiply(double[][] matA, double[][] matB) {
        int n = matB.length, m = matA.length;

        double[][] result = new double[m][m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                final int x = j, y = i;

                new Thread(() -> {
                    double cell = 0;
                    for (int k = 0; k < n; k++) {
                        cell += matA[y][k] * matB[k][x];
                    }

                    result[y][x] = cell;
                }).start();
            }
        }

        return result;
    }
}
