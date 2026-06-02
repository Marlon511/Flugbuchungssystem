package de.Flugbuchungssystem.service;

import java.util.List;

import de.Flugbuchungssystem.model.Buchung;
import de.Flugbuchungssystem.model.Flug;
import de.Flugbuchungssystem.model.Sitz;
import de.Flugbuchungssystem.service.interfaces.IBuchungsRepository;
import de.Flugbuchungssystem.service.interfaces.IFlugRepository;

/**
 * Zeigt Auslastungsberichte & Gepäckübersichten an.
 * Liest nur aus den Repositories — schreibt und verändert nichts.
 */
public class VerwaltungsService {
    private IFlugRepository flugRepo;
    private IBuchungsRepository buchungsRepo;

    /**
     * Erstellt einen neuen VerwaltungsService.
     * @param flugRepo das Repository für den Zugriff auf Flüge
     * @param buchungsRepo das Repository für den Zugriff auf Buchungen
     */
    public VerwaltungsService(IFlugRepository flugRepo, IBuchungsRepository buchungsRepo) {
        this.flugRepo = flugRepo;
        this.buchungsRepo = buchungsRepo;
    }

    /**
     * Gibt alle gespeicherten Flüge mit ihrer Auslastung auf der Konsole aus.
     */
    public void alleFluegeAusgeben() {
        System.out.println("=== Alle Flüge ===");
        for (Flug flug : flugRepo.getAlleFluege()) {
            auslastungProFlug(flug);
        }
    }

    /**
     * Gibt die Auslastung eines einzelnen Fluges auf der Konsole aus.
     * Zeigt die Anzahl der belegten und freien Sitze an.
     * @param flug der auszugebende Flug
     */
    public void auslastungProFlug(Flug flug) {
        int gesamt = flug.getFlugzeug().getSitze().size();
        int belegt = 0;
        for (Sitz sitz : flug.getFlugzeug().getSitze()) {
            if (sitz.isBelegt()) {
                belegt++;
            }
        }
        int frei = gesamt - belegt;
        System.out.println(flug.getFlugnummer() + " | " + flug.baueRoute()
                + " | Belegt: " + belegt + " | Frei: " + frei + " | Gesamt: " + gesamt);
    }

    /**
     * Gibt die Gepäckübersicht aller Buchungen für einen bestimmten Flug aus.
     * Zeigt pro Buchung Passagier und Gepäckanzahl an.
     * @param flug der Flug, für den die Gepäckübersicht ausgegeben werden soll
     */
    public void gepaeckUebersicht(Flug flug) {
        System.out.println("=== Gepäckübersicht: " + flug.getFlugnummer() + " ===");
        List<Buchung> alleBuchungen = buchungsRepo.getAlleBuchungen();
        for (Buchung buchung : alleBuchungen) {
            if (buchung.getFlug().getFlugnummer().equals(flug.getFlugnummer())) {
                System.out.println(buchung.getPassagier().getVollerName()
                        + " | Gepäckstücke: " + buchung.getGepaeckAnzahl());
            }
        }
    }

    /**
     * @return die Anzahl aller Buchungen
     */
    public int gesamtanzahlBuchungen() {
        return buchungsRepo.getAlleBuchungen().size();
    }
}