package de.Flugbuchungssystem.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import de.Flugbuchungssystem.exception.BuchungNichtGefundenException;
import de.Flugbuchungssystem.exception.FlugNichtVerfuegbarException;
import de.Flugbuchungssystem.exception.FlughafenNichtGefundenException;
import de.Flugbuchungssystem.exception.SitzNichtVerfuegbarException;
import de.Flugbuchungssystem.exception.UngueltigeSitznummerException;
import de.Flugbuchungssystem.model.Buchung;
import de.Flugbuchungssystem.model.Flug;
import de.Flugbuchungssystem.model.Flughafen;
import de.Flugbuchungssystem.model.Passagier;
import de.Flugbuchungssystem.model.Sitz;
import de.Flugbuchungssystem.model.SitzKategorie;
import de.Flugbuchungssystem.repository.BuchungsRepository;
import de.Flugbuchungssystem.repository.FlugRepository;
import de.Flugbuchungssystem.repository.FlughafenRepository;
import de.Flugbuchungssystem.repository.PassagierRepository;
import de.Flugbuchungssystem.service.BuchungsService;
import de.Flugbuchungssystem.service.FlugSuchService;
import de.Flugbuchungssystem.service.SitzplanService;
import de.Flugbuchungssystem.service.UmbuchungsService;
import de.Flugbuchungssystem.service.VerwaltungsService;

/**
 * Steuert das interaktive Konsolenmenü der Anwendung.
 * Die Klasse verarbeitet die Eingaben des Benutzers, übergibt die Aufgaben an die entsprechenden Services und zeigt die Ergebnisse auf der Konsole an.
 */
public class KonsolenUI {

    private final FlughafenRepository flughafenRepo;
    private final FlugRepository flugRepo;
    private final BuchungsRepository buchungsRepo;
    private final PassagierRepository passagierRepo;
    private final BuchungsService buchungsService;
    private final UmbuchungsService umbuchungsService;
    private final FlugSuchService flugSuchService;
    private final SitzplanService sitzplanService;
    private final VerwaltungsService verwaltungsService;

    /**
     * Erstellt eine neue KonsolenUI mit allen benötigten Abhängigkeiten.
     * @param flughafenRepo = das Repository für Flughäfen
     * @param flugRepo = das Repository für Flüge
     * @param buchungsRepo = das Repository für Buchungen
     * @param passagierRepo = das Repository für Passagiere
     * @param buchungsService = der Service für Buchungsoperationen
     * @param umbuchungsService = der Service für Umbuchungen
     * @param flugSuchService = der Service für die Flugsuche
     * @param sitzplanService = der Service für Sitzpläne
     * @param verwaltungsService = der Service für Verwaltungsfunktionen
     */
    public KonsolenUI(FlughafenRepository flughafenRepo,
                      FlugRepository flugRepo,
                      BuchungsRepository buchungsRepo,
                      PassagierRepository passagierRepo,
                      BuchungsService buchungsService,
                      UmbuchungsService umbuchungsService,
                      FlugSuchService flugSuchService,
                      SitzplanService sitzplanService,
                      VerwaltungsService verwaltungsService) {
        this.flughafenRepo    = flughafenRepo;
        this.flugRepo         = flugRepo;
        this.buchungsRepo     = buchungsRepo;
        this.passagierRepo    = passagierRepo;
        this.buchungsService  = buchungsService;
        this.umbuchungsService = umbuchungsService;
        this.flugSuchService  = flugSuchService;
        this.sitzplanService  = sitzplanService;
        this.verwaltungsService = verwaltungsService;
    }

    /**
     * Startet die Hauptschleife des Konsolenmenüs.
     * Läuft so lange, bis der Nutzer „0" (Beenden) eingibt.
     */
    public void starten() {
        Scanner scanner = new Scanner(System.in);
        boolean laufen = true;

        while (laufen) {
            System.out.println("\n========================================");
            System.out.println("      FLUGBUCHUNGSSYSTEM - Menü");
            System.out.println("========================================");
            System.out.println("1 - Flüge suchen");
            System.out.println("2 - Flug buchen");
            System.out.println("3 - Buchung stornieren");
            System.out.println("4 - Buchung umbuchen");
            System.out.println("5 - Sitzplan anzeigen");
            System.out.println("6 - Freie Sitze anzeigen");
            System.out.println("7 - Alle Buchungen anzeigen");
            System.out.println("8 - Auslastung aller Flüge");
            System.out.println("9 - Gepäckübersicht für einen Flug");
            System.out.println("0 - Beenden");
            System.out.println("----------------------------------------");
            System.out.print("Deine Wahl: ");

            String eingabe = scanner.nextLine().trim();

            switch (eingabe) {
                case "1": flugeSuchen(scanner);                        break;
                case "2": flugBuchen(scanner);                         break;
                case "3": buchungStornieren(scanner);                   break;
                case "4": buchungUmbuchen(scanner);                     break;
                case "5": sitzplanAnzeigen(scanner);                    break;
                case "6": freieSitzeAnzeigen(scanner);                  break;
                case "7": alleBuchungenAnzeigen();                      break;
                case "8": verwaltungsService.alleFluegeAusgeben();      break;
                case "9": gepaeckUebersicht(scanner);                   break;
                case "0":
                    System.out.println("Auf Wiedersehen!");
                    laufen = false;
                    break;
                default:
                    System.out.println("Ungültige Eingabe. Bitte 0–9 eingeben.");
            }
        }

        scanner.close();
    }

   
    // 1 - Flüge suchen
  

    /**
     * Fragt Start- und Zielflughafen sowie ein optionales Datum ab
     * und gibt alle passenden Flüge auf der Konsole aus.
     * @param scanner der aktive Scanner für Nutzereingaben
     */
    private void flugeSuchen(Scanner scanner) {
        System.out.println("\n--- Flüge suchen ---");
        System.out.println("Verfügbare Flughäfen:");
        for (Flughafen f : flughafenRepo.getAlleFlughaefen()) {
            System.out.println("  " + f.getIataCode() + " – " + f.getStadt());
        }

        System.out.print("Start-IATA-Code (z.B. FRA): ");
        String startCode = scanner.nextLine().trim().toUpperCase();

        System.out.print("Ziel-IATA-Code  (z.B. JFK): ");
        String zielCode = scanner.nextLine().trim().toUpperCase();

        Flughafen start;
        Flughafen ziel;
        try {
            start = flughafenRepo.findeNachCode(startCode);
            ziel  = flughafenRepo.findeNachCode(zielCode);
        } catch (FlughafenNichtGefundenException e) {
            System.out.println("Fehler: " + e.getMessage());
            return;
        }

        System.out.print("Datum (YYYY-MM-DD) oder Enter für alle: ");
        String datumEingabe = scanner.nextLine().trim();

        LocalDate datum = null;
        if (!datumEingabe.isEmpty()) {
            try {
                datum = LocalDate.parse(datumEingabe);
            } catch (Exception e) {
                System.out.println("Ungültiges Datum — Suche ohne Datum.");
            }
        }

        List<Flug> ergebnisse = flugSuchService.sucheFluege(start, ziel, datum);

        if (ergebnisse.isEmpty()) {
            System.out.println("Keine Flüge für diese Route gefunden.");
        } else {
            System.out.println("\nGefundene Flüge:");
            for (Flug f : ergebnisse) {
                System.out.println("  " + f);
            }
        }
    }


    // 2 - Flug buchen


    /**
     * Führt den vollständigen Buchungsvorgang durch.
     * Sucht den Passagier anhand der E-Mail oder legt ihn neu an, lässt Flug, Sitz und Gepäckanzahl wählen und erstellt die Buchung.
     * @param scanner der aktive Scanner für Nutzereingaben
     */
    private void flugBuchen(Scanner scanner) {
        System.out.println("\n--- Flug buchen ---");

        System.out.print("E-Mail des Passagiers: ");
        String email = scanner.nextLine().trim();

        Passagier passagier = passagierRepo.findePassagier(email);
        if (passagier == null) {
            System.out.println("Neuer Passagier — bitte Daten eingeben:");
            System.out.print("Vorname: ");
            String vorname = scanner.nextLine().trim();
            System.out.print("Nachname: ");
            String nachname = scanner.nextLine().trim();
            System.out.print("Telefon: ");
            String telefon = scanner.nextLine().trim();

            if (!InputValidator.istGueltigerName(vorname)) {
                System.out.println("Fehler: Der Vorname darf keine Zahlen enthalten.");
                return;
            }
            if (!InputValidator.istGueltigerName(nachname)) {
                System.out.println("Fehler: Der Nachname darf keine Zahlen enthalten.");
                return;
            }
            if (!InputValidator.istGueltigeTelefonnummer(telefon)) {
                System.out.println("Fehler: Die Telefonnummer darf nur Ziffern sowie +, -, (), Leerzeichen enthalten.");
                return;
            }
            passagier = new Passagier(vorname, nachname, email, telefon);
            passagierRepo.addPassagier(passagier);
            System.out.println("Passagier angelegt: " + passagier.getVollerName());
        } else {
            System.out.println("Passagier gefunden: " + passagier.getVollerName());
        }

        System.out.println("\nVerfügbare Flüge:");
        for (Flug f : flugRepo.getAlleFluege()) {
            System.out.println("  " + f);
        }
        System.out.print("Flugnummer: ");
        String flugnummer = scanner.nextLine().trim().toUpperCase();

        Flug flug;
        try {
            flug = flugRepo.findeFlug(flugnummer);
        } catch (FlugNichtVerfuegbarException e) {
            System.out.println("Fehler: " + e.getMessage());
            return;
        }

        sitzplanService.zeigeSitzplan(flug);

        System.out.print("Sitznummer (z.B. A1): ");
        String sitznummer = scanner.nextLine().trim().toUpperCase();

        System.out.print("Anzahl Gepäckstücke (mind. 1): ");
        int gepaeckAnzahl = 1;
        try {
            gepaeckAnzahl = Integer.parseInt(scanner.nextLine().trim());
            if (gepaeckAnzahl < 1) {
                System.out.println("Fehler: Gepäckanzahl darf nicht negativ sein.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Ungültige Eingabe — Gepäck wird auf 1 gesetzt.");
        }

        try {
            Buchung buchung = buchungsService.bucheFlug(passagier, flug, sitznummer, gepaeckAnzahl);
            System.out.println("\nBuchung erfolgreich!");
            System.out.println(buchung);
        } catch (UngueltigeSitznummerException | SitzNichtVerfuegbarException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

  
    // 3 - Buchung stornieren
  

    /**
     * Zeigt alle vorhandenen Buchungen an, fragt eine Buchungsnummer ab
     * und storniert die entsprechende Buchung.
     * @param scanner der aktive Scanner für Nutzereingaben
     */
    private void buchungStornieren(Scanner scanner) {
        System.out.println("\n--- Buchung stornieren ---");
        alleBuchungenAnzeigen();

        System.out.print("Buchungsnummer (z.B. BU-0001): ");
        String buchungsnummer = scanner.nextLine().trim().toUpperCase();

        try {
            double rueckerstattung = buchungsService.storniere(buchungsnummer);
            System.out.println("Stornierung erfolgreich. Rückerstattung: " + rueckerstattung + " EUR");
        } catch (BuchungNichtGefundenException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }


    // 4 - Buchung umbuchen


    /**
     * Zeigt alle Buchungen an, fragt Buchungsnummer, neuen Flug und neuen Sitz ab und bucht die Buchung entsprechend um.
     * @param scanner der aktive Scanner für Nutzereingaben
     */
    private void buchungUmbuchen(Scanner scanner) {
        System.out.println("\n--- Buchung umbuchen ---");
        alleBuchungenAnzeigen();

        System.out.print("Buchungsnummer (z.B. BU-0001): ");
        String buchungsnummer = scanner.nextLine().trim().toUpperCase();

        System.out.println("\nVerfügbare Flüge:");
        for (Flug f : flugRepo.getAlleFluege()) {
            System.out.println("  " + f);
        }
        System.out.print("Neue Flugnummer: ");
        String neueFlugnummer = scanner.nextLine().trim().toUpperCase();

        Flug neuerFlug;
        try {
            neuerFlug = flugRepo.findeFlug(neueFlugnummer);
        } catch (FlugNichtVerfuegbarException e) {
            System.out.println("Fehler: " + e.getMessage());
            return;
        }

        sitzplanService.zeigeSitzplan(neuerFlug);

        System.out.print("Neue Sitznummer: ");
        String neueSitznummer = scanner.nextLine().trim().toUpperCase();

        try {
            double differenz = umbuchungsService.bucheUm(buchungsnummer, neuerFlug, neueSitznummer);
            if (differenz > 0) {
                System.out.println("Umbuchung erfolgreich. Aufpreis: " + differenz + " EUR");
            } else {
                System.out.println("Umbuchung erfolgreich. Rückerstattung: " + Math.abs(differenz) + " EUR");
            }
        } catch (BuchungNichtGefundenException | SitzNichtVerfuegbarException
                | UngueltigeSitznummerException | IllegalStateException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }


    // 5 - Sitzplan anzeigen


    /**
     * Fragt eine Flugnummer ab und gibt den vollständigen Sitzplan
     * des gewählten Fluges auf der Konsole aus.
     * @param scanner der aktive Scanner für Nutzereingaben
     */
    private void sitzplanAnzeigen(Scanner scanner) {
        System.out.println("\n--- Sitzplan anzeigen ---");
        System.out.println("Verfügbare Flüge:");
        for (Flug f : flugRepo.getAlleFluege()) {
            System.out.println("  " + f.getFlugnummer() + " | " + f.baueRoute());
        }
        System.out.print("Flugnummer: ");
        String flugnummer = scanner.nextLine().trim().toUpperCase();

        try {
            Flug flug = flugRepo.findeFlug(flugnummer);
            sitzplanService.zeigeSitzplan(flug);
        } catch (FlugNichtVerfuegbarException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    // 6 - Freie Sitze anzeigen

    /**
     * Fragt Flugnummer und Sitzkategorie ab und gibt alle freien Sitze der gewählten Kategorie für den gewählten Flug aus.
     * @param scanner der aktive Scanner für Nutzereingaben
     */
    private void freieSitzeAnzeigen(Scanner scanner) {
        System.out.println("\n--- Freie Sitze anzeigen ---");
        System.out.println("Verfügbare Flüge:");
        for (Flug f : flugRepo.getAlleFluege()) {
            System.out.println("  " + f.getFlugnummer() + " | " + f.baueRoute());
        }
        System.out.print("Flugnummer: ");
        String flugnummer = scanner.nextLine().trim().toUpperCase();

        Flug flug;
        try {
            flug = flugRepo.findeFlug(flugnummer);
        } catch (FlugNichtVerfuegbarException e) {
            System.out.println("Fehler: " + e.getMessage());
            return;
        }

        System.out.println("Kategorie: 1=ECONOMY  2=BUSINESS  3=FIRST");
        System.out.print("Wahl: ");
        String wahl = scanner.nextLine().trim();

        SitzKategorie kategorie;
        switch (wahl) {
            case "1": kategorie = SitzKategorie.ECONOMY;  break;
            case "2": kategorie = SitzKategorie.BUSINESS; break;
            case "3": kategorie = SitzKategorie.FIRST;    break;
            default:
                System.out.println("Ungültige Wahl.");
                return;
        }

        List<Sitz> freieSitze = sitzplanService.zeigeFreieSitze(flug, kategorie);
        if (freieSitze.isEmpty()) {
            System.out.println("Keine freien Sitze in dieser Kategorie.");
        } else {
            System.out.println("Freie " + kategorie + "-Sitze:");
            for (Sitz s : freieSitze) {
                System.out.println("  " + s.getSitzName() + " | " + s.getKategorie());
            }
        }
    }

    // 7 - Alle Buchungen anzeigen
  
    /**
     * Gibt alle gespeicherten Buchungen auf der Konsole aus.
     */
    private void alleBuchungenAnzeigen() {
        List<Buchung> buchungen = buchungsRepo.getAlleBuchungen();
        if (buchungen.isEmpty()) {
            System.out.println("Keine Buchungen vorhanden.");
            return;
        }
        System.out.println("\n--- Alle Buchungen ---");
        for (Buchung b : buchungen) {
            System.out.println(b);
        }
    }

    
    // 9 - Gepäckübersicht
   

    /**
     * Fragt eine Flugnummer ab und gibt die Gepäckübersicht
     * aller Buchungen für diesen Flug aus.
     * @param scanner der aktive Scanner für Nutzereingaben
     */
    private void gepaeckUebersicht(Scanner scanner) {
        System.out.println("\n--- Gepäckübersicht ---");
        System.out.println("Verfügbare Flüge:");
        for (Flug f : flugRepo.getAlleFluege()) {
            System.out.println("  " + f.getFlugnummer() + " | " + f.baueRoute());
        }
        System.out.print("Flugnummer: ");
        String flugnummer = scanner.nextLine().trim().toUpperCase();

        try {
            Flug flug = flugRepo.findeFlug(flugnummer);
            verwaltungsService.gepaeckUebersicht(flug);
        } catch (FlugNichtVerfuegbarException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }
}