
package de.Flugbuchungssystem.ui;
import de.Flugbuchungssystem.model.*;
import java.util.List;


public class Main {

	public static void main(String[] args) {
		
		// Flughafen testen
		Flughafen f1 = new Flughafen("Frankfurt Airport", "FRA", "Frankfurt am Main","DE");
		System.out.println(f1.toString());
		
        // Flugzeug erstellen
        Flugzeug flugzeug = new Flugzeug(
                "Boeing 747", "D-ABCD",
                4, 2,    // 4 First-Sitze, 2 Reihen → A1-A2, B1-B2
                6, 2,    // 6 Business-Sitze, 2 Reihen → C1-C3, D1-D3
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

	}

}
