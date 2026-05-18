package de.Flugbuchungssystem.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import de.Flugbuchungssystem.exception.BuchungNichtGefundenException;
import de.Flugbuchungssystem.exception.FlugNichtVerfuegbarException;
import de.Flugbuchungssystem.exception.SitzNichtVerfuegbarException;
import de.Flugbuchungssystem.exception.UngueltigeSitznummerException;
import de.Flugbuchungssystem.model.Buchung;
import de.Flugbuchungssystem.model.Flug;
import de.Flugbuchungssystem.model.Fluggesellschaft;
import de.Flugbuchungssystem.model.Flughafen;
import de.Flugbuchungssystem.model.Flugzeug;
import de.Flugbuchungssystem.model.Passagier;
import de.Flugbuchungssystem.model.Sitz;
import de.Flugbuchungssystem.model.SitzKategorie;
import de.Flugbuchungssystem.repository.BuchungsRepository;
import de.Flugbuchungssystem.repository.FlugRepository;
import de.Flugbuchungssystem.repository.PassagierRepository;
import de.Flugbuchungssystem.service.BuchungsService;
import de.Flugbuchungssystem.service.FlugSuchService;
import de.Flugbuchungssystem.service.PreisService;
import de.Flugbuchungssystem.service.SitzplanService;
import de.Flugbuchungssystem.service.StornoService;
import de.Flugbuchungssystem.service.UmbuchungsService;
import de.Flugbuchungssystem.service.VerwaltungsService;

public class Main {

    // Repositories
    static FlugRepository flugRepo           = new FlugRepository();
    static BuchungsRepository buchungsRepo   = new BuchungsRepository();
    static PassagierRepository passagierRepo = new PassagierRepository();

    // Services
    static PreisService preisService             = new PreisService();
    static StornoService stornoService           = new StornoService(buchungsRepo);
    static BuchungsService buchungsService       = new BuchungsService(buchungsRepo, preisService, stornoService);
    static UmbuchungsService umbuchungsService   = new UmbuchungsService(buchungsRepo, preisService);
    static FlugSuchService flugSuchService       = new FlugSuchService(flugRepo);
    static SitzplanService sitzplanService       = new SitzplanService();
    static VerwaltungsService verwaltungsService = new VerwaltungsService(flugRepo, buchungsRepo);

    // Flughäfen als Klassenvariablen für Suchfunktion
    static Flughafen frankfurt;
    static Flughafen newYork;
    static Flughafen muenchen;

    public static void main(String[] args) {
        testdatenAnlegen();

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
                case "1": flugeSuchen(scanner);              break;
                case "2": flugBuchen(scanner);               break;
                case "3": buchungStornieren(scanner);        break;
                case "4": buchungUmbuchen(scanner);          break;
                case "5": sitzplanAnzeigen(scanner);         break;
                case "6": freieSitzeAnzeigen(scanner);       break;
                case "7": alleBuchungenAnzeigen();           break;
                case "8": verwaltungsService.alleFluegeAusgeben(); break;
                case "9": gepaeckUebersicht(scanner);        break;
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

    // -------------------------------------------------------
    // 1 - Flüge suchen
    // -------------------------------------------------------
    private static void flugeSuchen(Scanner scanner) {
        System.out.println("\n--- Flüge suchen ---");
        System.out.println("Verfügbare Flughäfen: FRA, MUC, JFK");

        System.out.print("Start-IATA-Code (z.B. FRA): ");
        String startCode = scanner.nextLine().trim().toUpperCase();

        System.out.print("Ziel-IATA-Code  (z.B. JFK): ");
        String zielCode = scanner.nextLine().trim().toUpperCase();

        System.out.print("Datum (YYYY-MM-DD) oder Enter für alle: ");
        String datumEingabe = scanner.nextLine().trim();

        Flughafen start = findeFlughafenNachCode(startCode);
        Flughafen ziel  = findeFlughafenNachCode(zielCode);

        if (start == null || ziel == null) {
            System.out.println("Fehler: Unbekannter Flughafencode.");
            return;
        }

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

    // -------------------------------------------------------
    // 2 - Flug buchen
    // -------------------------------------------------------
    private static void flugBuchen(Scanner scanner) {
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

        System.out.print("Anzahl Gepäckstücke: ");
        int gepaeckAnzahl = 0;
        try {
            gepaeckAnzahl = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Ungültige Eingabe — Gepäck wird auf 0 gesetzt.");
        }

        try {
            Buchung buchung = buchungsService.bucheFlug(passagier, flug, sitznummer, gepaeckAnzahl);
            System.out.println("\nBuchung erfolgreich!");
            System.out.println(buchung);
        } catch (UngueltigeSitznummerException | SitzNichtVerfuegbarException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // 3 - Buchung stornieren
    // -------------------------------------------------------
    private static void buchungStornieren(Scanner scanner) {
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

    // -------------------------------------------------------
    // 4 - Buchung umbuchen
    // -------------------------------------------------------
    private static void buchungUmbuchen(Scanner scanner) {
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
        } catch (BuchungNichtGefundenException | SitzNichtVerfuegbarException | UngueltigeSitznummerException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // 5 - Sitzplan anzeigen
    // -------------------------------------------------------
    private static void sitzplanAnzeigen(Scanner scanner) {
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

    // -------------------------------------------------------
    // 6 - Freie Sitze anzeigen
    // -------------------------------------------------------
    private static void freieSitzeAnzeigen(Scanner scanner) {
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

    // -------------------------------------------------------
    // 7 - Alle Buchungen anzeigen
    // -------------------------------------------------------
    private static void alleBuchungenAnzeigen() {
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

    // -------------------------------------------------------
    // 9 - Gepäckübersicht
    // -------------------------------------------------------
    private static void gepaeckUebersicht(Scanner scanner) {
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

    // -------------------------------------------------------
    // Hilfsmethoden
    // -------------------------------------------------------
    private static Flughafen findeFlughafenNachCode(String code) {
        if (code.equals(frankfurt.getIataCode())) return frankfurt;
        if (code.equals(newYork.getIataCode()))   return newYork;
        if (code.equals(muenchen.getIataCode()))  return muenchen;
        return null;
    }

    private static void testdatenAnlegen() {
        frankfurt = new Flughafen("Frankfurt am Main", "FRA", "Frankfurt", "DE");
        newYork   = new Flughafen("John F. Kennedy International", "JFK", "New York", "US");
        muenchen  = new Flughafen("München Franz Josef Strauss", "MUC", "München", "DE");

        Fluggesellschaft lufthansa = new Fluggesellschaft("Lufthansa", "LH");

        Flugzeug flugzeug1 = new Flugzeug("Boeing 747", "D-ABCD", frankfurt,
                4, 2, 20, 4, 120, 20);
        Flugzeug flugzeug2 = new Flugzeug("Airbus A320", "D-EFGH", muenchen,
                0, 0, 12, 2, 144, 24);

        lufthansa.addFlugzeug(flugzeug1);
        lufthansa.addFlugzeug(flugzeug2);

        Flug flug1 = new Flug("LH400", lufthansa, frankfurt, newYork,
                LocalDateTime.of(2026, 6, 1, 10, 0),
                LocalDateTime.of(2026, 6, 1, 20, 30),
                499.99, flugzeug1);

        Flug flug2 = new Flug("LH200", lufthansa, muenchen, frankfurt,
                LocalDateTime.of(2026, 6, 1, 8, 0),
                LocalDateTime.of(2026, 6, 1, 9, 15),
                89.99, flugzeug2);

        lufthansa.addFlug(flug1);
        lufthansa.addFlug(flug2);
        flugRepo.addFlug(flug1);
        flugRepo.addFlug(flug2);

        System.out.println("Testdaten geladen. Flüge: LH400 (FRA→JFK) | LH200 (MUC→FRA)");
    }
}