package de.Flugbuchungssystem.model;
import java.io.Serializable;

/**
 * Repräsentiert einen Passagier mit seinen persönlichen Kontaktdaten.
 * Reine Datenklasse ohne Geschäftslogik — nur Getter und {@link #toString()}.
 */
public class Passagier implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    /** Der Vorname des Passagiers. */
    private String vorname;

    /** Der Nachname des Passagiers. */
    private String nachname;

    /** Die E-Mail-Adresse des Passagiers, dient als eindeutiger Bezeichner. */
    private String email;

    /** Die Telefonnummer des Passagiers. */
    private String telefonnummer;

    /**
     * Erstellt einen neuen Passagier mit den angegebenen Kontaktdaten.
     *
     * @param vorname       der Vorname des Passagiers
     * @param nachname      der Nachname des Passagiers
     * @param email         die E-Mail-Adresse des Passagiers
     * @param telefonnummer die Telefonnummer des Passagiers
     */
    public Passagier(String vorname, String nachname, String email, String telefonnummer) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.telefonnummer = telefonnummer;
        this.email = email;
    }

    /**
     * Gibt den Vornamen des Passagiers zurück.
     *
     * @return der Vorname
     */
    public String getVorname() {
        return this.vorname;
    }

    /**
     * Gibt den Nachnamen des Passagiers zurück.
     *
     * @return der Nachname
     */
    public String getNachname() {
        return this.nachname;
    }

    /**
     * Gibt die E-Mail-Adresse des Passagiers zurück.
     *
     * @return die E-Mail-Adresse
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Gibt die Telefonnummer des Passagiers zurück.
     *
     * @return die Telefonnummer
     */
    public String getTelefonnummer() {
        return this.telefonnummer;
    }

    /**
     * Gibt den vollständigen Namen des Passagiers zurück.
     * Beispiel: {@code "Max Mustermann"}
     *
     * @return Vorname und Nachname durch Leerzeichen getrennt
     */
    public String getVollerName() {
        return this.vorname + " " + this.nachname;
    }

    /**
     * Gibt eine lesbare Darstellung des Passagiers zurück.
     * Beispiel: {@code "Max Mustermann | max@mail.de | 0170123456"}
     *
     * @return formatierte Zeichenkette mit Name, E-Mail und Telefonnummer
     */
    public String toString() {
        return getVollerName() +
                " | " + this.email +
                " | " + this.telefonnummer;
    }
}