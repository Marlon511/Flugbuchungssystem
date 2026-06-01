package de.Flugbuchungssystem.ui;

import de.Flugbuchungssystem.repository.BuchungsRepository;
import de.Flugbuchungssystem.repository.FlugRepository;
import de.Flugbuchungssystem.repository.FlughafenRepository;
import de.Flugbuchungssystem.repository.PassagierRepository;
import de.Flugbuchungssystem.service.BuchungsService;
import de.Flugbuchungssystem.service.FlugSuchService;
import de.Flugbuchungssystem.service.PreisService;
import de.Flugbuchungssystem.service.SitzplanService;
import de.Flugbuchungssystem.service.StornoService;
import de.Flugbuchungssystem.service.UmbuchungsService;
import de.Flugbuchungssystem.service.VerwaltungsService;

/**
 * Einstiegspunkt der Anwendung.
 * Erstellt alle Repositories und Services, verdrahtet die Abhängigkeiten
 * und startet die Benutzeroberfläche. Enthält keine Geschäftslogik.
 */
public class Main {

    /**
     * Startmethode der Anwendung.
     * Initialisiert alle Komponenten und startet das Konsolenmenü.
     *
     * @param args Kommandozeilenargumente (werden nicht verwendet)
     */
    public static void main(String[] args) {

        // Repositories erstellen
        FlughafenRepository flughafenRepo = new FlughafenRepository();
        FlugRepository flugRepo = new FlugRepository();
        BuchungsRepository buchungsRepo = new BuchungsRepository();
        PassagierRepository passagierRepo = new PassagierRepository();

        // Services erstellen und verdrahten
        PreisService preisService = new PreisService();
        StornoService stornoService = new StornoService(buchungsRepo);
        BuchungsService buchungsService = new BuchungsService(buchungsRepo, preisService, stornoService);
        UmbuchungsService umbuchungsService = new UmbuchungsService(buchungsRepo, preisService);
        FlugSuchService flugSuchService = new FlugSuchService(flugRepo);
        SitzplanService sitzplanService = new SitzplanService();
        VerwaltungsService verwaltungsService = new VerwaltungsService(flugRepo, buchungsRepo);

        // Testdaten laden
        TestdatenFactory.laden(flughafenRepo, flugRepo, passagierRepo);

        // UI starten
        new KonsolenUI(
            flughafenRepo, flugRepo, buchungsRepo, passagierRepo,
            buchungsService, umbuchungsService, flugSuchService,
            sitzplanService, verwaltungsService
        ).starten();
    }
}