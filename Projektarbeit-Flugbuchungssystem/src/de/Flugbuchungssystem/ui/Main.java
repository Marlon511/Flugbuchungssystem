package de.Flugbuchungssystem.ui;

import de.Flugbuchungssystem.repository.BuchungsRepository;
import de.Flugbuchungssystem.repository.FlugRepository;
import de.Flugbuchungssystem.repository.FlughafenRepository;
import de.Flugbuchungssystem.repository.PassagierRepository;
import de.Flugbuchungssystem.service.Datenerhaltung;
import de.Flugbuchungssystem.service.BuchungsService;
import de.Flugbuchungssystem.service.FlugSuchService;
import de.Flugbuchungssystem.service.PreisService;
import de.Flugbuchungssystem.service.SpeicherService;
import de.Flugbuchungssystem.service.SitzplanService;
import de.Flugbuchungssystem.service.StornoService;
import de.Flugbuchungssystem.service.UmbuchungsService;
import de.Flugbuchungssystem.service.VerwaltungsService;

/**
 * Einstiegspunkt der Anwendung.
 * Erstellt alle Repositories und Services.
 */
public class Main {

    /**
     * Startmethode der Anwendung.
     * Initialisiert alle Komponenten und startet das Konsolenmenü.
     * @param args Kommandozeilenargumente (werden nicht verwendet)
     */
    public static void main(String[] args) {

        SpeicherService speicherService = new SpeicherService();
        Datenerhaltung gespeicherteDaten = speicherService.laden();

        FlughafenRepository flughafenRepo;
        FlugRepository flugRepo;
        BuchungsRepository buchungsRepo;
        PassagierRepository passagierRepo;

        if (gespeicherteDaten == null) {
            // Repositories erstellen
            flughafenRepo = new FlughafenRepository();
            flugRepo = new FlugRepository();
            buchungsRepo = new BuchungsRepository();
            passagierRepo = new PassagierRepository();

            // Testdaten laden
            TestdatenFactory.laden(flughafenRepo, flugRepo, passagierRepo);
        } else {
            flughafenRepo = gespeicherteDaten.getFlughafenRepo();
            flugRepo = gespeicherteDaten.getFlugRepo();
            buchungsRepo = gespeicherteDaten.getBuchungsRepo();
            passagierRepo = gespeicherteDaten.getPassagierRepo();
            System.out.println("Gespeicherte Daten wurden geladen.");
        } 
        

        // Services erstellen und verdrahten
        PreisService preisService = new PreisService();
        StornoService stornoService = new StornoService(buchungsRepo);
        BuchungsService buchungsService = new BuchungsService(buchungsRepo, preisService, stornoService);
        UmbuchungsService umbuchungsService = new UmbuchungsService(buchungsRepo, preisService);
        FlugSuchService flugSuchService = new FlugSuchService(flugRepo);
        SitzplanService sitzplanService = new SitzplanService();
        VerwaltungsService verwaltungsService = new VerwaltungsService(flugRepo, buchungsRepo);

        // UI starten
        KonsolenUI ui = new KonsolenUI(
            flughafenRepo, flugRepo, buchungsRepo, passagierRepo,
            buchungsService, umbuchungsService, flugSuchService,
            sitzplanService, verwaltungsService
        );
        ui.starten();

        speicherService.speichern(new Datenerhaltung(flughafenRepo, flugRepo, buchungsRepo, passagierRepo));
    }
}

