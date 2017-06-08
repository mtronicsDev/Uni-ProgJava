package de.gruppe3.factorial;

import java.math.BigInteger;

/**
 * @author Max
 */
public class Fak {
    public static void main(String... args) {
        BigInteger currentFac = BigInteger.ONE;

        for (int i = 2; i <= 500000; i++) {
            currentFac = currentFac.multiply(BigInteger.valueOf(i));
            System.out.println(i + "! = " + currentFac.toString());
        }
    }
}
