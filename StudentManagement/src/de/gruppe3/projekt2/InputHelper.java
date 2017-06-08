package de.gruppe3.projekt2;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Helper class to check if input is correct
 */
class InputHelper {
    /**
     * Reads a string with the provided scanner, checks if valid and returns it.
     *
     * @param scanner          The scanner used for - you guessed it - SCANNING!
     * @param validationMethod The method used to validate the input
     * @return The input if valid, null if invalid
     */
    static String readString(Scanner scanner, ValidationMethod validationMethod) {
        String input = scanner.next();

        if (!validationMethod.validate(input)) {
            return null;
        } else return input;
    }

    /**
     * Reads a positive (or 0) int, validates it, returns it.
     *
     * @param scanner          The scanner used for - you guessed it - SCANNING!
     * @param validationMethod The method used to validate the input
     * @return The input if valid, -1 if invalid
     */
    static int readInt(Scanner scanner, ValidationMethod validationMethod) {
        try {
            int input = scanner.nextInt();

            if (!validationMethod.validate(input)) {
                return -1;
            } else return input;
        } catch (InputMismatchException e) {
            return -1;
        }
    }

    /**
     * Reads a float >= 0, validates and returns it.
     *
     * @param scanner          The scanner used for - you guessed it - SCANNING!
     * @param validationMethod The method used to validate the input
     * @return The input if valid, -1 if invalid
     */
    static float readFloat(Scanner scanner, ValidationMethod validationMethod) {
        try {
            float input = scanner.nextFloat();

            if (!validationMethod.validate(input)) {
                return -1;
            } else return input;
        } catch (InputMismatchException e) {
            return -1;
        }
    }

    /**
     * Functional interface to allow validators in form of lambdas to be passed to the read methods.
     */
    @FunctionalInterface
    interface ValidationMethod {
        /**
         * Check if input matches validation criteria.
         *
         * @param o The input to validate.
         * @return true if valid, false if invalid
         */
        boolean validate(Object o);
    }
}
