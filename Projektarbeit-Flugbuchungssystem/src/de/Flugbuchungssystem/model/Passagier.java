package de.Flugbuchungssystem.model;
import java.io.Serializable;

/**
 * Repräsentiert einen Passagier mit seinen persönlichen Kontaktdaten.
 * Reine Datenklasse ohne Geschäftslogik — nur Getter und {@link #toString()}.
 */
public class Passagier implements Serializable{
	private static final long serialVersionUID = 1L;
    private String vorname;
    private String nachname;
    private String email;
    private String telefonnummer;
    /**
     * Erstellt einen neuen Passagier mit den angegebenen Kontaktdaten.
     * @param vorname = der Vorname des Passagiers
     * @param nachname = der Nachname des Passagiers
     * @param email = die E-Mail-Adresse des Passagiers
     * @param telefonnummer = die Telefonnummer des Passagiers
     */
    public Passagier(String vorname, String nachname, String email, String telefonnummer) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.telefonnummer = telefonnummer;
        this.email = email;
    }

    /**
     * @return der Vorname
     */
    public String getVorname() {
        return this.vorname;
    }

    /**
     * @return der Nachname
     */
    public String getNachname() {
        return this.nachname;
    }

    /**
     * @return die E-Mail-Adresse
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @return die Telefonnummer
     */
    public String getTelefonnummer() {
        return this.telefonnummer;
    }

    /**
     * @return Vorname und Nachname durch Leerzeichen getrennt
     */
    public String getVollerName() {
        return this.vorname + " " + this.nachname;
    }

    /**
     * @return formatierte Zeichenkette mit Name, E-Mail und Telefonnummer
     */
    public String toString() {
        return getVollerName() +
                " | " + this.email +
                " | " + this.telefonnummer;
    }
}