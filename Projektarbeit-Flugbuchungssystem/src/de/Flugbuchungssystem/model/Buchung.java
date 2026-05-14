package de.Flugbuchungssystem.model;

import de.Flugbuchungssystem.model.*;


/**
 * Das zentrale Verbindungsobjekt des Buchungssystems.
 * Verknüpft {@link Passagier}, {@link Flug} und {@link Sitz} und speichert
 * den Buchungsstatus sowie den bezahlten Gesamtpreis.
 * Die Buchungsnummer wird beim Erstellen automatisch vergeben.
 */

public class Buchung {

	/** Zähler zur automatischen Vergabe eindeutiger Buchungsnummern. */
	private static int zaehler = 1;

	/** Die eindeutige Buchungsnummer, z.B. „BU-0001". */
	private String buchungsnummer;

	/** Der Passagier, der diese Buchung vorgenommen hat. */
	private Passagier passagier;

	/** Der gebuchte Flug. */
	private Flug flug;

	/** Der zugewiesene Sitzplatz. */
	private Sitz sitz;

	/** Die Anzahl der aufgegebenen Gepäckstücke. */
	private int gepaeckAnzahl;

	/** Der aktuelle Status dieser Buchung. */
	private Buchungsstatus status;

	/** Der Gesamtpreis der Buchung in Euro inklusive Gepäck. */
	private double gesamtpreis;

	/**
	 * Erstellt eine neue Buchung. Die Buchungsnummer wird automatisch generiert
	 * und der Status auf {@link Buchungsstatus#GEBUCHT} gesetzt.
	 *
	 * @param passagier     der buchende Passagier
	 * @param flug          der gebuchte Flug
	 * @param sitz          der zugewiesene Sitzplatz
	 * @param gepaeckAnzahl die Anzahl der aufgegebenen Gepäckstücke
	 * @param gesamtpreis   der Gesamtpreis in Euro
	 */
	public Buchung(Passagier passagier, Flug flug, Sitz sitz, int gepaeckAnzahl, double gesamtpreis) {
		this.buchungsnummer = "BU-" + String.format("%04d", zaehler++);
		this.passagier = passagier;
		this.flug = flug;
		this.sitz = sitz;
		this.gepaeckAnzahl = gepaeckAnzahl;
		this.gesamtpreis = gesamtpreis;
		this.status = Buchungsstatus.GEBUCHT;
	}

	/**
	 * Gibt die Buchungsnummer zurück.
	 *
	 * @return die eindeutige Buchungsnummer
	 */
	public String getBuchungsnummer() {
		return buchungsnummer;
	}

	/**
	 * Gibt den Passagier dieser Buchung zurück.
	 *
	 * @return der Passagier
	 */
	public Passagier getPassagier() {
		return passagier;
	}

	/**
	 * Gibt den gebuchten Flug zurück.
	 *
	 * @return der Flug
	 */
	public Flug getFlug() {
		return flug;
	}

	/**
	 * Gibt den zugewiesenen Sitzplatz zurück.
	 *
	 * @return der Sitz
	 */
	public Sitz getSitz() {
		return sitz;
	}

	/**
	 * Gibt die Anzahl der aufgegebenen Gepäckstücke zurück.
	 *
	 * @return die Gepäckanzahl
	 */
	public int getGepaeckAnzahl() {
		return gepaeckAnzahl;
	}

	/**
	 * Gibt den aktuellen Buchungsstatus zurück.
	 *
	 * @return der Buchungsstatus
	 */
	public Buchungsstatus getStatus() {
		return status;
	}

	/**
	 * Gibt den Gesamtpreis der Buchung zurück.
	 *
	 * @return der Gesamtpreis in Euro
	 */
	public double getGesamtpreis() {
		return gesamtpreis;
	}

	/**
	 * Setzt den Flug der Buchung, z.B. bei einer Umbuchung.
	 *
	 * @param flug der neue Flug
	 */
	public void setFlug(Flug flug) {
		this.flug = flug;
	}

	/**
	 * Setzt den Sitzplatz der Buchung, z.B. bei einer Umbuchung.
	 *
	 * @param sitz der neue Sitzplatz
	 */
	public void setSitz(Sitz sitz) {
		this.sitz = sitz;
	}

	/**
	 * Setzt den Status der Buchung.
	 *
	 * @param status der neue Buchungsstatus
	 */
	public void setStatus(Buchungsstatus status) {
		this.status = status;
	}

	/**
	 * Aktualisiert den Gesamtpreis der Buchung.
	 *
	 * @param preis der neue Gesamtpreis in Euro
	 */
	public void setGesamtpreis(double preis) {
		this.gesamtpreis = preis;
	}

	/**
	 * Gibt eine lesbare Darstellung der Buchung zurück.
	 * Beispiel: {@code "BU-0001 | Max Mustermann | FRA → JFK | A3 | GEBUCHT | 450.0 EUR"}
	 *
	 * @return formatierte Zeichenkette mit allen wesentlichen Buchungsdaten
	 */
	public String toString() {
		return buchungsnummer + " | " + passagier.getVollerName()
				+ " | " + flug.getRoute()
				+ " | " + sitz.getSitzName()
				+ " | " + status
				+ " | " + gesamtpreis + " EUR";
	}
}