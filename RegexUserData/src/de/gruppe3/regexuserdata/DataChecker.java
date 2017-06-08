package de.gruppe3.regexuserdata;

import java.util.Scanner;

/**
 * @author Max
 */
public class DataChecker {
    public static void main(String... args) {
        Scanner hansl = new Scanner(System.in);
        hansl.useDelimiter("\n");

        System.out.println("Guten Tag, bitte geben Sie Ihre Daten hier ein:");

        boolean nameOkay = false;

        while(!nameOkay){
            System.out.println("Name (Vorname, [Zweitnamen], Nachname) eingeben:");
            String name = hansl.next();
            nameOkay = name.matches("(\\p{Lu}\\p{Ll}+)([\\- ](\\p{Lu}\\p{Ll}+))* (\\p{Lu}\\p{Ll}+)(-(\\p{Lu}\\p{Ll}+))*");
            if (!nameOkay) System.err.println("Name inkorrekt!");
        }

        System.out.println("Name korrekt, weiter im Programm.");

        boolean mnummerOkay = false;

        while(!mnummerOkay){
            System.out.println("Matrikelnummer eingeben:");
            String mnummer = hansl.next();
            mnummerOkay = mnummer.matches("[1-9][0-9]{4,5}");
            if (!mnummerOkay) System.err.println("Matrikelnummer inkorrekt!");
        }

        System.out.println("Matrikelnummer korrekt, weiter im Programm.");

        boolean dateOkay = false;

        while(!dateOkay){
            System.out.println("Geburtsdatum eingeben:");
            String date = hansl.next();
            dateOkay = date.matches(
                    "([1-9]|[1-2][0-9]|3[0-1])\\.([1-9]|1[0-2])\\.(19[5-9][0-9]|200[0-7])"
            );
            if (!dateOkay) System.err.println("Geburtsdatum inkorrekt!");
        }

        System.out.println("Geburtsdatum korrekt, weiter im Programm.");

        boolean siteOkay = false;

        while(!siteOkay){
            System.out.println("Emailadresse eingeben:");
            String email = hansl.next();
            siteOkay = email.matches(
                    "(\\p{Alnum}+[\\p{Alnum}\\-.]*\\p{Alnum}+)@(\\p{Alnum}+[\\p{Alnum}\\-.]*\\p{Alnum}+)\\.\\p{L}{2,10}"
            );
            if (!siteOkay) System.err.println("Emailadresse inkorrekt!");
        }

        System.out.println("Emailadresse korrekt, weiter im Programm.");

        boolean urlOkay = false;

        while(!urlOkay){
            System.out.println("Website eingeben:");
            String site = hansl.next();
            urlOkay = site.matches(
                    "(http(s?)://([\\p{L}0-9\\-]+)(\\.[\\p{L}0-9\\-]+)*(\\.\\p{L}+)(/.*?)*(/?))"
            );
            if (!urlOkay) System.err.println("Website inkorrekt!");
        }

        System.out.println("Website korrekt, weiter im Programm.");

        boolean ipOkay = false;

        while(!ipOkay){
            System.out.println("IP-Adresse eingeben:");
            String ip = hansl.next();
            ipOkay = ip.matches(
                    "([0-9]|[1-9][0-9]|1[0-9]{2}|2([0-4][0-9]|5[0-5]))\\.([0-9]|[1-9][0-9]|1[0-9]{2}|2([0-4][0-9]|5[0-5]))\\.([0-9]|[1-9][0-9]|1[0-9]{2}|2([0-4][0-9]|5[0-5]))\\.([0-9]|[1-9][0-9]|1[0-9]{2}|2([0-4][0-9]|5[0-5]))"
            );
            if (!ipOkay) System.err.println("IP-Adresse inkorrekt!");
        }

        System.out.println("IP-Adresse korrekt, weiter im Programm.");

    }
}
