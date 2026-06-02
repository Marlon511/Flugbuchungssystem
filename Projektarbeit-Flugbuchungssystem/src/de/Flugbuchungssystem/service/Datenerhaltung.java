package de.Flugbuchungssystem.service;

import java.io.Serializable;

import de.Flugbuchungssystem.repository.BuchungsRepository;
import de.Flugbuchungssystem.repository.FlugRepository;
import de.Flugbuchungssystem.repository.FlughafenRepository;
import de.Flugbuchungssystem.repository.PassagierRepository;

/**
 * Enthält alle Repositories für die Datenspeicherung.
 */

public class Datenerhaltung implements Serializable {
    private static final long serialVersionUID = 1L;

    private FlughafenRepository flughafenRepo;
    private FlugRepository flugRepo;
    private BuchungsRepository buchungsRepo;
    private PassagierRepository passagierRepo;
    
    /**
     * Erstellt ein neues Datenerhaltung-Objekt mit allen Repositories.
     * @param flughafenRepo Repository für Flughäfen
     * @param flugRepo Repository für Flüge
     * @param buchungsRepo Repository für Buchungen
     * @param passagierRepo Repository für Passagiere
     */

    public Datenerhaltung(FlughafenRepository flughafenRepo,
                    FlugRepository flugRepo,
                    BuchungsRepository buchungsRepo,
                    PassagierRepository passagierRepo) {
        this.flughafenRepo = flughafenRepo;
        this.flugRepo = flugRepo;
        this.buchungsRepo = buchungsRepo;
        this.passagierRepo = passagierRepo;
    }

    public FlughafenRepository getFlughafenRepo() {
        return flughafenRepo;
    }

    public FlugRepository getFlugRepo() {
        return flugRepo;
    }

    public BuchungsRepository getBuchungsRepo() {
        return buchungsRepo;
    }

    public PassagierRepository getPassagierRepo() {
        return passagierRepo;
    }
}