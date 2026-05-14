
package de.Flugbuchungssystem.ui;
import de.Flugbuchungssystem.model.*;
import java.util.List;
import java.time.LocalDateTime;


public class Main {

	public static void main(String[] args) {
		
		// Flughafen testen
		Flughafen f1 = new Flughafen("Frankfurt Airport", "FRA", "Frankfurt am Main","DE");
		System.out.println(f1.toString());
		
        // Flugzeug erstellen
        Flugzeug flugzeug = new Flugzeug(
                "Boeing 747", "D-ABCD", f1,
                0, 0,    // 4 First-Sitze, 2 Reihen → A1-A2, B1-B2
                8, 4,    // 6 Business-Sitze, 2 Reihen → C1-C3, D1-D3
                10, 2    // 10 Economy-Sitze, 2 Reihen → E1-E5, F1-F5
        );

        // --- getFreieSitze testen ---
        System.out.println("=== Freie Economy-Sitze ===");
        List<Sitz> freieEconomy = flugzeug.getFreieSitze(SitzKategorie.ECONOMY);
        for (Sitz s : freieEconomy) {
            System.out.println(s.getSitzName());
        }

        // Einen Sitz belegen
        flugzeug.findeSitz("E1").belegen();
        flugzeug.findeSitz("E2").belegen();

        System.out.println("\n=== Freie Economy-Sitze nach Belegung ===");
        freieEconomy = flugzeug.getFreieSitze(SitzKategorie.ECONOMY);
        for (Sitz s : freieEconomy) {
            System.out.println(s.getSitzName());
        }

        // --- findeSitz testen ---
        System.out.println("\n=== findeSitz testen ===");

        Sitz suche = flugzeug.findeSitz("A1");
        if (suche == null) {
            System.out.println("Sitz " + suche.getSitzName() + "Z9 nicht gefunden.");
        }
        if (suche != null) {
            System.out.println("Gefunden: " + suche.getSitzName() + " | Belegt: " + suche.isBelegt());
        }
        
        // Alle Sitze ausgeben
		for (Sitz s : flugzeug.getSitze()) {
        System.out.println(s.getSitzName() + " - " + s.getKategorie());
		}
		
		//Flugzeug Standort testen
		System.out.println(flugzeug.getStandort());
		
		//Fluggesellschaft testen
		System.out.println("\n=== Fluggesellschaft testen ===");
		Fluggesellschaft gesellschaft1 = new Fluggesellschaft("Lufthansa", "LH");
		gesellschaft1.addFlugzeug(flugzeug);
		gesellschaft1.setHeimflughafen(f1);
		System.out.println("Der Heimflughafen ist: "+gesellschaft1.getHeimflughafen());
		
		// --- Stammdaten anlegen ---
        Flughafen frankfurt = new Flughafen("Frankfurt am Main", "FRA", "Frankfurt", "DE");
        Flughafen newYork   = new Flughafen("John F. Kennedy International", "JFK", "New York", "US");

        System.out.println("=== Flughäfen ===");
        System.out.println(frankfurt);
        System.out.println(newYork);

        Fluggesellschaft lufthansa = new Fluggesellschaft("Lufthansa", "LH");
        System.out.println("\n=== Fluggesellschaft ===");
        System.out.println(lufthansa);

        // --- Flugzeug anlegen (4 First, 2 Reihen | 20 Business, 4 Reihen | 120 Economy, 20 Reihen) ---
        Flugzeug flugzeug2 = new Flugzeug("Boeing 747", "D-ABCD", null,
                4, 2,
                20, 4,
                120, 20);
        lufthansa.addFlugzeug(flugzeug2);

        // --- Flug anlegen ---
        Flug flug = new Flug(
                "LH400",
                lufthansa,
                frankfurt,
                newYork,
                LocalDateTime.of(2026, 6, 1, 10, 0),
                LocalDateTime.of(2026, 6, 1, 20, 30),
                499.99,
                flugzeug2
        );
        lufthansa.addFlug(flug);

        System.out.println("\n=== Flug ===");
        System.out.println(flug);
        System.out.println("Route:          " + flug.getRoute());
        System.out.println("Basispreis:     " + flug.getBasispreis() + " EUR");
        System.out.println("istRoute FRA→JFK: " + flug.istRoute(frankfurt, newYork));
        System.out.println("istRoute FRA→MUC: " + flug.istRoute(frankfurt, new Flughafen("München", "MUC", "München", "DE")));
        System.out.println("istAmDatum 01.06: " + flug.istAmDatum(java.time.LocalDate.of(2026, 6, 1)));
        System.out.println("istAmDatum 02.06: " + flug.istAmDatum(java.time.LocalDate.of(2026, 6, 2)));

     // --- Freie Sitze prüfen ---
        System.out.println("\n=== Freie Economy-Sitze (erste 3) ===");
        List<Sitz> freieSitze = flug.getFreieSitze(SitzKategorie.ECONOMY);
        for (int i = 0; i < 3; i++) {
            Sitz s = freieSitze.get(i);
            System.out.println(s.getSitzName() + " | " + s.getKategorie());
        }

        // --- Passagier anlegen ---
        Passagier passagier = new Passagier("Max", "Mustermann", "max@beispiel.de", "+49123456789");

        // --- Sitz belegen und Buchung erstellen ---
        Sitz gewaehlterSitz = flugzeug.findeSitz("A1");
        gewaehlterSitz.belegen();

        Buchung buchung1 = new Buchung(passagier, flug, gewaehlterSitz, 2, 799.99);

        System.out.println("\n=== Buchung ===");
        System.out.println(buchung1);
        System.out.println("Buchungsnummer: " + buchung1.getBuchungsnummer());
        System.out.println("Status:         " + buchung1.getStatus());
        System.out.println("Passagier:      " + buchung1.getPassagier().getVollerName());
        System.out.println("Sitz:           " + buchung1.getSitz().getSitzName());
        System.out.println("Gepäckstücke:   " + buchung1.getGepaeckAnzahl());
        System.out.println("Gesamtpreis:    " + buchung1.getGesamtpreis() + " EUR");

        // --- Umbuchung testen ---
        System.out.println("\n=== Umbuchung ===");
        Sitz neuerSitz = flugzeug.findeSitz("B1");
        neuerSitz.belegen();
        buchung1.setSitz(neuerSitz);
        buchung1.setStatus(Buchungsstatus.UMGEBUCHT);
        buchung1.setGesamtpreis(850.00);
        System.out.println(buchung1);

        // --- Stornierung testen ---
        System.out.println("\n=== Stornierung ===");
        buchung1.setStatus(Buchungsstatus.STORNIERT);
        neuerSitz.freigeben();
        System.out.println(buchung1);
        System.out.println("Sitz wieder frei: " + !neuerSitz.isBelegt());

        // --- Zweite Buchung (auto-increment Nummer) ---
        System.out.println("\n=== Zweite Buchung ===");
        Passagier passagier2 = new Passagier("Anna", "Schmidt", "anna@beispiel.de", "+49987654321");
        Sitz sitz2 = flugzeug.findeSitz("C1");
        sitz2.belegen();
        Buchung buchung2 = new Buchung(passagier2, flug, sitz2, 0, 499.99);
        System.out.println(buchung2);

	}

}
