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

/**
 * Einstiegspunkt der Anwendung und zentrale UI-Schicht.
 * Initialisiert alle Repositories und Services, legt Testdaten an
 * und steuert das interaktive Konsolenmenü.
 * Alle Nutzereingaben werden hier entgegengenommen und an die
 * zuständigen Services delegiert.
 */
public class Main {

    /** Repository für den Zugriff auf alle gespeicherten Flüge. */
    static FlugRepository flugRepo           = new FlugRepository();

    /** Repository für den Zugriff auf alle gespeicherten Buchungen. */
    static BuchungsRepository buchungsRepo   = new BuchungsRepository();

    /** Repository für den Zugriff auf alle registrierten Passagiere. */
    static PassagierRepository passagierRepo = new PassagierRepository();

    /** Service für die Preisberechnung von Buchungen. */
    static PreisService preisService             = new PreisService();

    /** Service für die Stornierungslogik. */
    static StornoService stornoService           = new StornoService(buchungsRepo);

    /** Service für das Erstellen und Stornieren von Buchungen. */
    static BuchungsService buchungsService       = new BuchungsService(buchungsRepo, preisService, stornoService);

    /** Service für das Umbuchen bestehender Buchungen. */
    static UmbuchungsService umbuchungsService   = new UmbuchungsService(buchungsRepo, preisService);

    /** Service für die Suche nach Flügen. */
    static FlugSuchService flugSuchService       = new FlugSuchService(flugRepo);

    /** Service für die Darstellung und Abfrage von Sitzplänen. */
    static SitzplanService sitzplanService       = new SitzplanService();

    /** Service für Verwaltungs- und Administrationsfunktionen. */
    static VerwaltungsService verwaltungsService = new VerwaltungsService(flugRepo, buchungsRepo);

    /** Flughafen Frankfurt — wird als Klassenvariable für die Suchfunktion bereitgehalten. */
    static Flughafen frankfurt;

    /** Flughafen New York — wird als Klassenvariable für die Suchfunktion bereitgehalten. */
    static Flughafen newYork;

    /** Flughafen München — wird als Klassenvariable für die Suchfunktion bereitgehalten. */
    static Flughafen muenchen;

    /** Flughafen Dubai — wird als Klassenvariable für die Suchfunktion bereitgehalten. */
    static Flughafen dubai;

    /**
     * Startmethode der Anwendung.
     * Legt Testdaten an und startet die Hauptschleife des Konsolenmenüs.
     * Die Schleife läuft so lange, bis der Nutzer „0" eingibt.
     *
     * @param args Kommandozeilenargumente (werden nicht verwendet)
     */
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

    /**
     * Fragt Start- und Zielflughafen sowie ein optionales Datum ab
     * und gibt alle passenden Flüge auf der Konsole aus.
     *
     * @param scanner der aktive Scanner für Nutzereingaben
     */
    private static void flugeSuchen(Scanner scanner) {
        System.out.println("\n--- Flüge suchen ---");
        System.out.println("Verfügbare Flughäfen: FRA, MUC, JFK, DXB");

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

    /**
     * Führt den vollständigen Buchungsvorgang durch.
     * Sucht den Passagier anhand der E-Mail oder legt ihn neu an,
     * lässt Flug, Sitz und Gepäckanzahl wählen und erstellt die Buchung.
     *
     * @param scanner der aktive Scanner für Nutzereingaben
     */
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
            
            if (!istGueltigerName(vorname)) {
                System.out.println("Fehler: Der Vorname darf keine Zahlen enthalten.");
                return;
            }
            if (!istGueltigerName(nachname)) {
                System.out.println("Fehler: Der Nachname darf keine Zahlen enthalten.");
                return;
            }
            
            if (!istGueltigeTelefonnummer(telefon)) {
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

    // -------------------------------------------------------
    // 3 - Buchung stornieren
    // -------------------------------------------------------

    /**
     * Zeigt alle vorhandenen Buchungen an, fragt eine Buchungsnummer ab
     * und storniert die entsprechende Buchung.
     * Gibt den berechneten Rückerstattungsbetrag aus.
     *
     * @param scanner der aktive Scanner für Nutzereingaben
     */
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

    /**
     * Zeigt alle Buchungen an, fragt Buchungsnummer, neuen Flug und neuen Sitz ab
     * und bucht die Buchung entsprechend um.
     * Gibt je nach Preisdifferenz einen Aufpreis oder eine Rückerstattung aus.
     *
     * @param scanner der aktive Scanner für Nutzereingaben
     */
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
        } catch (BuchungNichtGefundenException | SitzNichtVerfuegbarException
                | UngueltigeSitznummerException | IllegalStateException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // 5 - Sitzplan anzeigen
    // -------------------------------------------------------

    /**
     * Fragt eine Flugnummer ab und gibt den vollständigen Sitzplan
     * des gewählten Fluges mit Belegungsstatus auf der Konsole aus.
     *
     * @param scanner der aktive Scanner für Nutzereingaben
     */
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

    /**
     * Fragt Flugnummer und Sitzkategorie ab und gibt alle freien Sitze
     * der gewählten Kategorie für den gewählten Flug aus.
     *
     * @param scanner der aktive Scanner für Nutzereingaben
     */
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

    /**
     * Gibt alle gespeicherten Buchungen auf der Konsole aus.
     * Zeigt eine Meldung, wenn noch keine Buchungen vorhanden sind.
     */
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

    /**
     * Fragt eine Flugnummer ab und gibt die Gepäckübersicht
     * aller Buchungen für diesen Flug auf der Konsole aus.
     *
     * @param scanner der aktive Scanner für Nutzereingaben
     */
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
    /**
     * Sucht einen der bekannten Flughäfen anhand seines IATA-Codes.
     *
     * @param code der dreistellige IATA-Code, z.B. „FRA"
     * @return der passende {@link Flughafen}, oder {@code null} wenn kein Flughafen
     *         mit diesem Code bekannt ist
     */
    private static Flughafen findeFlughafenNachCode(String code) {
        if (code.equals(frankfurt.getIataCode())) return frankfurt;
        if (code.equals(newYork.getIataCode()))   return newYork;
        if (code.equals(muenchen.getIataCode()))  return muenchen;
        if (code.equals(dubai.getIataCode()))  	 return dubai;
        return null;
    }
    
    /**
     * Prüft, ob ein Name ausschließlich aus Buchstaben, Leerzeichen,
     * Bindestrichen und Apostrophen besteht.
     * <p>
     * Die Methode iteriert über jeden einzelnen Buchstaben des Namens.
     * Sobald ein Zeichen gefunden wird, das eine Ziffer ist
     * (geprüft mit {@link Character#isDigit(char)}), gilt der Name als ungültig.
     * Ziffern sind alle Zeichen im Unicode-Bereich 0–9.
     * Leerzeichen, Bindestriche und Apostrophe sind erlaubt,
     * da sie in realen Namen vorkommen (z.B. „Anna-Maria" oder „O'Brien").
     * </p>
     *
     * @param name der zu prüfende Vor- oder Nachname
     * @return {@code true}, wenn der Name keine Ziffern enthält und nicht leer ist,
     *         sonst {@code false}
     */
    private static boolean istGueltigerName(String name) {
        if (name.isEmpty()) {
            return false;
        }
        for (char c : name.toCharArray()) {
            if (Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Prüft, ob eine Telefonnummer ausschließlich aus erlaubten Zeichen besteht.
     * <p>
     * Die Methode iteriert über jedes Zeichen der Eingabe.
     * Erlaubt sind Ziffern (0–9), das Pluszeichen {@code +} für internationale
     * Vorwahlen (z.B. „+49"), Bindestriche {@code -}, runde Klammern {@code (} und
     * {@code )} sowie Leerzeichen zur Gliederung.
     * Sobald ein Zeichen gefunden wird, das keiner dieser Kategorien angehört
     * (insbesondere Buchstaben), gilt die Nummer als ungültig.
     * </p>
     * <p>
     * Beispiele für gültige Eingaben: {@code "+49 170 1234567"},
     * {@code "030-123456"}, {@code "(089) 98765"}.
     * </p>
     *
     * @param nummer die zu prüfende Telefonnummer
     * @return {@code true}, wenn die Nummer nicht leer ist und nur erlaubte
     *         Zeichen enthält, sonst {@code false}
     */
    private static boolean istGueltigeTelefonnummer(String nummer) {
        if (nummer.isEmpty()) {
            return false;
        }
        for (char c : nummer.toCharArray()) {
            if (!Character.isDigit(c) && c != '+' && c != '-' && c != ' ' && c != '(' && c != ')') {
                return false;
            }
        }
        return true;
    }

    /**
     * Legt alle Testdaten einmalig beim Programmstart an.
     * Erstellt Flughäfen, Fluggesellschaften, Flugzeuge und Flüge
     * und registriert diese in den jeweiligen Repositories.
     */
    private static void testdatenAnlegen() {
        frankfurt = new Flughafen("Frankfurt am Main", "FRA", "Frankfurt", "DE");
        newYork   = new Flughafen("John F. Kennedy International", "JFK", "New York", "US");
        muenchen  = new Flughafen("München Franz Josef Strauss", "MUC", "München", "DE");
        dubai     = new Flughafen("Dubai International Airport", "DXB", "Dubai", "VAE");

        Fluggesellschaft lufthansa = new Fluggesellschaft("Lufthansa", "LH");
        Fluggesellschaft emirates  = new Fluggesellschaft("Emirates", "UAE");

        Flugzeug flugzeug1 = new Flugzeug("Boeing 747", "D-ABCD", frankfurt,
                4, 2, 20, 4, 120, 20);
        Flugzeug flugzeug2 = new Flugzeug("Airbus A320", "D-EFGH", muenchen,
                0, 0, 12, 2, 144, 24);
        Flugzeug flugzeug3 = new Flugzeug("Boeing 777", "A6-EDN", dubai,
                6, 6, 12, 6, 24, 6);

        lufthansa.addFlugzeug(flugzeug1);
        lufthansa.addFlugzeug(flugzeug2);
        emirates.addFlugzeug(flugzeug3);

        Flug flug1 = new Flug("LH400", lufthansa, frankfurt, newYork,
                LocalDateTime.of(2026, 6, 1, 10, 0),
                LocalDateTime.of(2026, 6, 1, 20, 30),
                499.99, flugzeug1);

        Flug flug2 = new Flug("LH200", lufthansa, muenchen, frankfurt,
                LocalDateTime.of(2026, 6, 1, 8, 0),
                LocalDateTime.of(2026, 6, 1, 9, 15),
                89.99, flugzeug2);

        Flug flug3 = new Flug("EK051", emirates, dubai, frankfurt,
                LocalDateTime.of(2026, 6, 1, 10, 0),
                LocalDateTime.of(2026, 6, 12, 17, 10),
                534.99, flugzeug3);

        Flug flug4 = new Flug("EK052", emirates, dubai, frankfurt,
                LocalDateTime.of(2026, 6, 1, 15, 0),
                LocalDateTime.of(2026, 6, 12, 22, 10),
                534.99, flugzeug3);

        flugRepo.addFlug(flug1);
        flugRepo.addFlug(flug2);
        flugRepo.addFlug(flug3);
        flugRepo.addFlug(flug4);

        System.out.println("Testdaten geladen. Flüge: LH400 (FRA→JFK) | LH200 (MUC→FRA) | EK051 (DXB→FRA) | EK052 (DXB→FRA)");
    }
}