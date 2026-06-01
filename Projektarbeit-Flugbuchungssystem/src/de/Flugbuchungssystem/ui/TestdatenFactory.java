package de.Flugbuchungssystem.ui;

import java.time.LocalDateTime;

import de.Flugbuchungssystem.model.Flug;
import de.Flugbuchungssystem.model.Fluggesellschaft;
import de.Flugbuchungssystem.model.Flughafen;
import de.Flugbuchungssystem.model.Flugzeug;
import de.Flugbuchungssystem.model.Passagier;
import de.Flugbuchungssystem.repository.BuchungsRepository;
import de.Flugbuchungssystem.repository.FlugRepository;
import de.Flugbuchungssystem.repository.FlughafenRepository;
import de.Flugbuchungssystem.repository.PassagierRepository;

/**
 * Erstellt alle Testdaten für den initialen Programmstart.
 * Wird von {@link Main} nur aufgerufen, wenn keine gespeicherten CSV-Daten vorhanden sind.
 * Enthält realistische Flughäfen, Fluggesellschaften, Flugzeuge, Flüge und Passagiere.
 */
public class TestdatenFactory {

    /**
     * Befüllt alle übergebenen Repositories mit vordefinierten Testdaten.
     * Legt Flughäfen, Fluggesellschaften, Flugzeuge, Flüge und Passagiere an.
     *
     * @param flughafenRepo das Repository für Flughäfen
     * @param flugRepo      das Repository für Flüge
     * @param passagierRepo das Repository für Passagiere
     */
    public static void laden(FlughafenRepository flughafenRepo,
                             FlugRepository flugRepo,
                             PassagierRepository passagierRepo) {

        // --- Flughäfen ---
        Flughafen frankfurt = new Flughafen("Frankfurt am Main", "FRA", "Frankfurt", "DE");
        Flughafen newYork   = new Flughafen("John F. Kennedy International", "JFK", "New York", "US");
        Flughafen muenchen  = new Flughafen("München Franz Josef Strauss", "MUC", "München", "DE");
        Flughafen dubai     = new Flughafen("Dubai International Airport", "DXB", "Dubai", "VAE");
        Flughafen london    = new Flughafen("London Heathrow", "LHR", "London", "GB");
        Flughafen paris     = new Flughafen("Paris Charles de Gaulle", "CDG", "Paris", "FR");

        flughafenRepo.addFlughafen(frankfurt);
        flughafenRepo.addFlughafen(newYork);
        flughafenRepo.addFlughafen(muenchen);
        flughafenRepo.addFlughafen(dubai);
        flughafenRepo.addFlughafen(london);
        flughafenRepo.addFlughafen(paris);

        // --- Fluggesellschaften ---
        Fluggesellschaft lufthansa = new Fluggesellschaft("Lufthansa", "LH");
        Fluggesellschaft emirates  = new Fluggesellschaft("Emirates", "EK");
        Fluggesellschaft airFrance = new Fluggesellschaft("Air France", "AF");

        // --- Flugzeuge ---
        Flugzeug flugzeug1 = new Flugzeug("Boeing 747", "D-ABCD", frankfurt,
                4, 2, 20, 4, 120, 20);
        Flugzeug flugzeug2 = new Flugzeug("Airbus A320", "D-EFGH", muenchen,
                0, 0, 12, 2, 144, 24);
        Flugzeug flugzeug3 = new Flugzeug("Boeing 777", "A6-EDN", dubai,
                6, 6, 12, 6, 24, 6);
        Flugzeug flugzeug4 = new Flugzeug("Airbus A380", "F-HPJA", paris,
                6, 3, 24, 4, 200, 25);
        Flugzeug flugzeug5 = new Flugzeug("Airbus A220", "F-HBIQ", paris,
                0, 0, 12, 2, 96, 16);

        lufthansa.addFlugzeug(flugzeug1);
        lufthansa.addFlugzeug(flugzeug2);
        emirates.addFlugzeug(flugzeug3);
        airFrance.addFlugzeug(flugzeug4);
        airFrance.addFlugzeug(flugzeug5);

        // --- Flüge ---
        // Lufthansa: FRA → JFK
        flugRepo.addFlug(new Flug("LH400", lufthansa, frankfurt, newYork,
                LocalDateTime.of(2026, 6, 1, 10, 0),
                LocalDateTime.of(2026, 6, 1, 20, 30), 499.99, flugzeug1));
        flugRepo.addFlug(new Flug("LH402", lufthansa, frankfurt, newYork,
                LocalDateTime.of(2026, 7, 5, 11, 0),
                LocalDateTime.of(2026, 7, 5, 21, 30), 519.99, flugzeug1));

        // Lufthansa: MUC → FRA
        flugRepo.addFlug(new Flug("LH200", lufthansa, muenchen, frankfurt,
                LocalDateTime.of(2026, 6, 1, 8, 0),
                LocalDateTime.of(2026, 6, 1, 9, 15), 89.99, flugzeug2));
        flugRepo.addFlug(new Flug("LH202", lufthansa, muenchen, frankfurt,
                LocalDateTime.of(2026, 7, 3, 7, 30),
                LocalDateTime.of(2026, 7, 3, 8, 45), 79.99, flugzeug2));

        // Lufthansa: FRA → LHR
        flugRepo.addFlug(new Flug("LH300", lufthansa, frankfurt, london,
                LocalDateTime.of(2026, 6, 10, 9, 0),
                LocalDateTime.of(2026, 6, 10, 10, 45), 129.99, flugzeug2));
        flugRepo.addFlug(new Flug("LH302", lufthansa, frankfurt, london,
                LocalDateTime.of(2026, 7, 14, 14, 0),
                LocalDateTime.of(2026, 7, 14, 15, 45), 119.99, flugzeug2));

        // Emirates: DXB → FRA
        flugRepo.addFlug(new Flug("EK051", emirates, dubai, frankfurt,
                LocalDateTime.of(2026, 6, 1, 10, 0),
                LocalDateTime.of(2026, 6, 1, 17, 10), 534.99, flugzeug3));
        flugRepo.addFlug(new Flug("EK052", emirates, dubai, frankfurt,
                LocalDateTime.of(2026, 6, 15, 15, 0),
                LocalDateTime.of(2026, 6, 15, 22, 10), 534.99, flugzeug3));

        // Air France: FRA → CDG
        flugRepo.addFlug(new Flug("AF100", airFrance, frankfurt, paris,
                LocalDateTime.of(2026, 6, 5, 7, 0),
                LocalDateTime.of(2026, 6, 5, 8, 30), 99.99, flugzeug5));
        flugRepo.addFlug(new Flug("AF102", airFrance, frankfurt, paris,
                LocalDateTime.of(2026, 7, 20, 16, 0),
                LocalDateTime.of(2026, 7, 20, 17, 30), 109.99, flugzeug5));

        // Air France: CDG → JFK
        flugRepo.addFlug(new Flug("AF200", airFrance, paris, newYork,
                LocalDateTime.of(2026, 7, 1, 10, 30),
                LocalDateTime.of(2026, 7, 1, 19, 0), 429.99, flugzeug4));
        flugRepo.addFlug(new Flug("AF202", airFrance, paris, newYork,
                LocalDateTime.of(2026, 8, 10, 11, 0),
                LocalDateTime.of(2026, 8, 10, 19, 30), 449.99, flugzeug4));

        // --- Passagiere ---
        passagierRepo.addPassagier(new Passagier("Max", "Mustermann", "maxmustermann@mail.de", "+49 170 1234567"));
        passagierRepo.addPassagier(new Passagier("Anna", "Schmidt", "annaschmidt@mail.de", "+49 160 9876543"));
        passagierRepo.addPassagier(new Passagier("John", "Doe", "johndoe@mail.com", "+1 212 5550199"));
        passagierRepo.addPassagier(new Passagier("Marie", "Dupont", "marieDupton@mail.fr", "+33 6 12345678"));
        passagierRepo.addPassagier(new Passagier("Lena", "Bauer", "lenabauer@mail.de", "+49 151 2345678"));

        System.out.println("Testdaten geladen: 6 Flughäfen | 3 Airlines | 5 Flugzeuge | 12 Flüge | 5 Passagiere");
    }
}