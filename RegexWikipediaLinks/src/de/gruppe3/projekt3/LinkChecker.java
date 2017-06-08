package de.gruppe3.projekt3;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LinkChecker {
    public static void main(String... args) {
        //Argument muss Wikipedia-URL (oder andere URL) sein
        if (args.length != 1) {
            throw new IllegalArgumentException("Bitte URL eingeben als Kommandozeilenparameter!!");
        }

        String url = args[0];

        try {
            String article = downloadWikipediaLink(url);

            List<String> links = getLinks(article);
            followLinks(links);
        }
        //Wenn Wikipedia-Link nicht aufrufbar ist: Fehlermeldung
        catch (IOException e) {
            System.err.println("Konnte Artikel nicht einlesen: " + e.getMessage());
        }
    }

    /**
     * Liest HTML-Quelltext aus der Seite mit dem angegebenen Link ein.
     * @param url Link der Seite
     * @return HTML-Code
     * @throws IOException Falls HTTP-Code nicht "OK" ist oder Connection fehlschlägt
     */
    private static String downloadWikipediaLink(String url) throws IOException {
        URL myurl = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
        con.setRequestProperty("User-Agent", "LinkTester/0.1(mein.name@uni-passau.de)Java/1.8.0");

        try {
            return downloadLink(url, con);
        } catch (IOException e) {
            System.err.println("Konnte nicht mit Wikipedia verbinden, Code: " + e.getMessage());
        }

        return ""; //Falls Fehler aufgetreten ist
    }

    /**
     * Lädt HTML-Code der Seite des angegebenen Links herunter, {@link LinkChecker#downloadWikipediaLink(String)}
     * @param url Der Link
     * @return Der HTML-Code
     * @throws IOException Falls HTTP-Antwort nicht "OK" ist oder Connection fehlschlägt
     */
    private static String downloadLink(String url, HttpURLConnection customConnection) throws IOException {
        //Vergleiche mit downloadWikipediaLink()

        HttpURLConnection con;

        if(customConnection != null) {
            con = customConnection;
        } else {
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();
        }
        con.connect();

        int responseCode = con.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException(String.valueOf(responseCode));
        }

        String site = "";

        InputStream ins = con.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            site += inputLine + "\n";
        }

        in.close();

        return site;
    }

    /**
     * Filtert HTML-Code nach externen Links und gibt diese zurück.
     * @param article Der HTML-Code
     * @return Liste von externen Links
     */
    private static List<String> getLinks(String article) {
        //Matcht "href"-Attribut aller Tags, v.a. <a>-Tag
        Pattern linkPattern = Pattern.compile("href=\"(http(s?)://([\\p{L}0-9\\-]+)(\\.[\\p{L}0-9\\-]+)*(\\.\\p{L}+)(/.*?)*(/?))\"");
        Matcher matcher = linkPattern.matcher(article);

        List<String> links = new LinkedList<>();

        //Solange externe Links gefunden werden, diese einlesen und zur Liste hinzufügen
        while (matcher.find()) {
            //linkTag ist nächster gefundener Link
            String linkTag = matcher.group();

            //Holt den Link aus dem gematchten Muster heraus (löscht href=" und ")
            linkTag = linkTag.replaceAll("(href=\")(.*?)\"", "$2");

            //Gibt aktuellen Link aus und speichert ihn in Liste
            System.out.println(linkTag);
            links.add(linkTag);
        }

        return links;
    }

    /**
     * Folgt allen übergebenen Links, prüft ob diese ein freistehendes "404" beinhalten.
     * @param links die Links, denen zu folgen ist
     * @throws IOException Falls HTTP-Antwort nicht "OK" ist oder Connection fehlschlägt
     */
    private static void followLinks(List<String> links) throws IOException {
        for (String link : links) {
            URL url = new URL(link);
            HttpURLConnection con;

            //Folge allen Redirects bis zu einer lesbaren Seite
            while (true) {
                con = (HttpURLConnection) url.openConnection();
                con.setInstanceFollowRedirects(false);

                int responseCode = con.getResponseCode();

                //Behandle Fehler- und Redirectcodes separat
                switch (responseCode) {
                    //Redirect
                    case HttpURLConnection.HTTP_MOVED_PERM:
                    case HttpURLConnection.HTTP_MOVED_TEMP:
                        String next = con.getHeaderField("Location");
                        url = new URL(url, next);
                        link = url.toExternalForm(); //converts relative URLs like /profile to absolute ones (http://.../profile)
                        continue; //Bei Redirect muss Seite nicht weiter behandelt werden, also direkt weiter in der While-Schleife
                    default:
                        //401 kommt oft vor, wenn Bots auf einer Seite nicht zugelassen sind, vgl. Google Books
                        if (responseCode == 401) System.err.println("Unauthorized (Code: 401, Link akzeptiert vermutlich keine Bots!): " + link);
                        //Alle restlichen Fehlercodes werden als toter Link ausgewertet
                        else if (responseCode >= 400 && responseCode < 600) {
                            System.err.println("Toter Link (Code: "+responseCode+"): " + link);
                            break;
                        }
                        //Kein direkter Fehlercode, lade Seite herunter uns suche nach 404 im Text
                        else {
                            try {
                                String site = downloadLink(link, null);

                                //404 darf nicht von Buchstaben, Zahlen oder Bindestrichen umgeben sein, um als "freistehend" zu gelten
                                if (site.matches(".*?[^p{L}0-9\\-]?404[^p{L}0-9\\-]?.*?")) {
                                    System.err.println("Toter Link wegen 404 im Text: " + link);
                                }
                            }
                            //Falls Download der Seite schiefgeht: Fehlermeldung
                            catch (IOException e) {
                                System.err.println("Toter Link (Konnte ihn nicht aufrufen, Code: " + e.getMessage() + "): " + link);
                            }
                        }
                }

                break;
            }
        }
    }
}
