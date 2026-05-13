package de.Flugbuchungssystem.model;
import java.util.ArrayList;
import java.util.List;

public class Flugzeug {

    private String modell;
    private String kennung;
    private List<Sitz> sitze;

    public Flugzeug(
            String modell, String kennung,
            int firstSitze, int firstReihen,
            int businessSitze, int businessReihen,
            int economySitze, int economyReihen
    ) {
        this.modell = modell;
        this.kennung = kennung;
        this.sitze = new ArrayList<>();

        char reihe = 'A';

        reihe = erstelleKategorie(reihe, firstSitze, firstReihen, SitzKategorie.FIRST);
        reihe = erstelleKategorie(reihe, businessSitze, businessReihen, SitzKategorie.BUSINESS);
        erstelleKategorie(reihe, economySitze, economyReihen, SitzKategorie.ECONOMY);
    }

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

    public List<Sitz> getSitze() {
        return sitze;
    }
    
    public String getKennung() {
    		return kennung;
    }
    
    public String getModell() {
    		return modell;
    }
    
    public List<Sitz> getFreieSitze(SitzKategorie kategorie) {
        List<Sitz> freieSitze = new ArrayList<>();
        for (Sitz sitz : sitze) {
            if (sitz.getKategorie() == kategorie && !sitz.isBelegt()) {
                freieSitze.add(sitz);
            }
        }
        return freieSitze;
    }

    public Sitz findeSitz(String sitznummer) {
        for (Sitz sitz : sitze) {
            if (sitz.getSitzName().equals(sitznummer)) {
                return sitz;
            }
        }
        return null;
    }
}