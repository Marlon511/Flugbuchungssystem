package de.Flugbuchungssystem.service;

import java.util.ArrayList;
import java.util.List;

import de.Flugbuchungssystem.model.Flug;
import de.Flugbuchungssystem.model.Sitz;
import de.Flugbuchungssystem.model.SitzKategorie;

/**
 * Zuständig für die Darstellung und Abfrage von Sitzplänen.
 * Gibt formatierte Übersichten aller Sitze eines Fluges auf der Konsole aus
 * und kann nach Kategorie filtern.
 */
public class SitzplanService {

    /**
     * Erstellt einen neuen SitzplanService.
     */
    public SitzplanService() {
    }

    /**
     * Gibt den vollständigen Sitzplan eines Fluges auf der Konsole aus.
     * Zeigt für jeden Sitz Name, Kategorie und Belegungsstatus an.
     *
     * @param flug der Flug, dessen Sitzplan ausgegeben werden soll
     */
    public void zeigeSitzplan(Flug flug) {
        System.out.println("=== Sitzplan: " + flug.getFlugnummer() + " (" + flug.baueRoute() + ") ===");
        for (Sitz sitz : flug.getFlugzeug().getSitze()) {
            String status = sitz.isBelegt() ? "BELEGT" : "FREI";
            System.out.println(sitz.getSitzName() + " | " + sitz.getKategorie() + " | " + status);
        }
    }

    /**
     * Gibt alle freien Sitze einer bestimmten Kategorie für einen Flug zurück.
     *
     * @param flug      der zu prüfende Flug
     * @param kategorie die gewünschte Sitzkategorie
     * @return Liste der freien Sitze in der angegebenen Kategorie
     */
    public List<Sitz> zeigeFreieSitze(Flug flug, SitzKategorie kategorie) {
        List<Sitz> freieSitze = new ArrayList<>();
        for (Sitz sitz : flug.getFlugzeug().getSitze()) {
            if (sitz.getKategorie() == kategorie && !sitz.isBelegt()) {
                freieSitze.add(sitz);
            }
        }
        return freieSitze;
    }
}
