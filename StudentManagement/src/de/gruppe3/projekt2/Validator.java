package de.gruppe3.projekt2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

class Validator {
    /*
     * Predefined lambdas, ready to be used in the InputHelper (or elsewhere)
     */
    static InputHelper.ValidationMethod valBirthday =
            (object) -> validateBirthday((String) object);

    static InputHelper.ValidationMethod valName =
            (object) -> validateName((String) object);

    static InputHelper.ValidationMethod valID =
            (object) -> validateID((int) object);

    static InputHelper.ValidationMethod valGrade =
            (object) -> validateGrade((float) object);

    static InputHelper.ValidationMethod valSubject =
            (object) -> validateSubject((String) object);

    /**
     * checks if the given birth date is in the range of typical student birth dates (i.e. not exceptionally early or late)
     *
     * @param birthday The birth date to be validated, in the format dd.MM.yyy
     * @return true if the birthday is valid, false otherwise
     */
    @SuppressWarnings("WeakerAccess")
    static boolean validateBirthday(String birthday) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        try {
            Date date = format.parse(birthday);

            //Students typically aren't older than 40-50 and have finished school (most likely).
            if (date.after(Date.from(Instant.now().minus(Duration.ofDays((long) (365.25 * 14))))))
                return false;

            if (date.before(format.parse("01.01.1970")))
                return false;

        } catch (ParseException e) {
            //If wrong format is supplied, reject date
            return false;
        }

        //If none of the rejection criteria applies, accept date
        return true;
    }

    /**
     * Checks the validity of the supplied name.
     * <p>
     * Names are ALWAYS composed of first and last name but can also contain secondary names.
     * Furthermore, names have to start with capital letters and combined names are written with dashes (-),
     * e.g. Karl-Heinz.
     *
     * @param name uppercase and lowercase letters and dash; A-Z, a-z, '-'
     * @return true if name is valid, false otherwise
     */
    static boolean validateName(String name) {
        return name.matches("(\\p{Lu}\\p{Ll}+)([\\- ](\\p{Lu}\\p{Ll}+))+");
    }

    /**
     * Validates the name of a subject based on these criteria:
     * <p>
     * Subjects have to start with capital letters,
     * Subjects can be abbreviated by a series of capital letters (e.g. MTS),
     * Subjects can contain numbers, but not at their start.
     *
     * @param subject uppercase and lowercase letters and numbers from 0-9; A-Z, a-z, 0-9
     * @return true if subject is valid, false otherwise
     */
    @SuppressWarnings("WeakerAccess")
    static boolean validateSubject(String subject) {
        return subject.matches("[\\p{Lu}0-9 ]+|(\\p{Lu}\\p{Ll}+)( ([\\p{L}0-9]+))*");
    }

    /**
     * Checks if the grades of a student are below 5.0 and above 1.0. If both statemnts are true, then it returns true.
     *
     * @param grades values 1.0-5.0
     * @return true if grade is valid, false otherwise
     */
    @SuppressWarnings("WeakerAccess")
    static boolean validateGrade(float grades) {
        return grades <= 5 && grades >= 1;
    }

    /**
     * Negative IDs don't exist, ID 0 is disallowed by many systems
     *
     * @param id The ID to validate
     * @return true if the ID is valid, false otherwise
     */
    static boolean validateID(int id) {
        return id > 0;
    }
}
