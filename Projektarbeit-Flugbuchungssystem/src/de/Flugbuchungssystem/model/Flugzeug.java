package de.Flugbuchungssystem.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert ein Flugzeug mit Modell, Kennung, Standort und Sitzplan.
 * Beim Erstellen werden alle Sitze der drei Kategorien (FIRST, BUSINESS, ECONOMY) automatisch generiert und in einer internen Liste gespeichert.
 */
public class Flugzeug implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private String modell;
    private String kennung;
    private List<Sitz> sitze;
    private Flughafen standort;

    /**
     * Erstellt ein neues Flugzeug und generiert alle Sitzplätze automatisch.
     * Sitze werden in der Reihenfolge FIRST → BUSINESS → ECONOMY angelegt.
     * Werden für eine Kategorie beide Werte mit 0 übergeben, wird sie übersprungen.
     * @param modell = das Flugzeugmodell
     * @param kennung = die Luftfahrzeugkennung
     * @param standort = der aktuelle Standortflughafen
     * @param firstSitze = Gesamtanzahl First-Class-Sitze (0 = keine First Class)
     * @param firstReihen = Anzahl der First-Class-Reihen
     * @param businessSitze = Gesamtanzahl Business-Sitze
     * @param businessReihen = Anzahl der Business-Reihen
     * @param economySitze = Gesamtanzahl Economy-Sitze
     * @param economyReihen = Anzahl der Economy-Reihen
     */
    public Flugzeug(
            String modell, String kennung, Flughafen standort,
            int firstSitze, int firstReihen,
            int businessSitze, int businessReihen,
            int economySitze, int economyReihen
    ) {
        this.modell = modell;
        this.kennung = kennung;
        this.sitze = new ArrayList<>();
        this.standort = standort;

        char reihe = 'A';

        reihe = erstelleKategorie(reihe, firstSitze, firstReihen, SitzKategorie.FIRST);
        reihe = erstelleKategorie(reihe, businessSitze, businessReihen, SitzKategorie.BUSINESS);
        erstelleKategorie(reihe, economySitze, economyReihen, SitzKategorie.ECONOMY);
    }

    /**
     * Legt alle Sitze einer Kategorie an und fügt sie der internen Sitzliste hinzu.
     * @param startReihe = der Startbuchstabe für die erste Reihe dieser Kategorie
     * @param sitzeGesamt = Gesamtanzahl der Sitze in dieser Kategorie
     * @param reihen = Anzahl der Reihen in dieser Kategorie
     * @param kategorie = die Sitzkategorie
     * @return der nächste freie Reihenbuchstabe nach dieser Kategorie
     * @throws IllegalArgumentException wenn nur einer der Werte 0 ist oder die Sitze nicht gleichmäßig auf die Reihen verteilt werden können
     */
    private char erstelleKategorie(
            char startReihe,
            int sitzeGesamt,
            int reihen,
            SitzKategorie kategorie
    ) {
        if (sitzeGesamt == 0 && reihen == 0) {
            return startReihe;
        }

        if (sitzeGesamt == 0 || reihen == 0) {
            throw new IllegalArgumentException(
                    "Ungültige Werte für " + kategorie
            );
        }

        if (sitzeGesamt % reihen != 0) {
            throw new IllegalArgumentException(
                    "Sitze nicht gleichmäßig verteilbar für " + kategorie
            );
        }

        int sitzeProReihe = sitzeGesamt / reihen;
        char aktuelleReihe = startReihe;

        for (int r = 0; r < reihen; r++) {
            for (int p = 1; p <= sitzeProReihe; p++) {
                sitze.add(new Sitz(aktuelleReihe, p, kategorie));
            }
            aktuelleReihe++;
        }

        return aktuelleReihe;
    }

    /**
     * @return Liste aller Sitzplätze
     */
    public List<Sitz> getSitze() {
        return sitze;
    }

    /**
     * @return die Kennung, z.B. „D-ABCD"
     */
    public String getKennung() {
        return kennung;
    }

    /**
     * @return das Modell, z.B. „Boeing 747"
     */
    public String getModell() {
        return modell;
    }

    /**
     * @return Standortbeschreibung mit der Stadt des Standortflughafens
     */
    public String getStandort() {
        return "\n" + "Das Flugzeug befindet sich aktuell in: " + standort.getStadt() + ".";
    }

    /**
     * @param kategorie die gewünschte Sitzkategorie
     * @return Liste der freien Sitze in der angegebenen Kategorie
     */
    public List<Sitz> getFreieSitze(SitzKategorie kategorie) {
        List<Sitz> freieSitze = new ArrayList<>();
        for (Sitz sitz : sitze) {
            if (sitz.getKategorie() == kategorie && !sitz.isBelegt()) {
                freieSitze.add(sitz);
            }
        }
        return freieSitze;
    }

    /**
     * Sucht einen Sitz anhand seines Namens.
     * @param sitznummer die Sitznummer als Zeichenkette, z.B. „A3"
     * @return der gefundene Sitz, oder {@code null} wenn kein Sitz mit diesem Namen existiert
     */
    public Sitz findeSitz(String sitznummer) {
        for (Sitz sitz : sitze) {
            if (sitz.getSitzName().equals(sitznummer)) {
                return sitz;
            }
        }
        return null;
    }
}