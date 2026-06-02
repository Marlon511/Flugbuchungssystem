package de.Flugbuchungssystem.model;

import java.io.Serializable;
import java.util.List;

/**
 * Das zentrale Verbindungsobjekt des Buchungssystems.
 * Verknüpft {@link Passagier}, {@link Flug} und {@link Sitz} und speichert
 * den Buchungsstatus sowie den bezahlten Gesamtpreis.
 * Die Buchungsnummer wird beim Erstellen automatisch vergeben.
 */

public class Buchung implements Serializable {

	private static final long serialVersionUID = 1L;
	private static int zaehler = 1;
	private String buchungsnummer;
	private Passagier passagier;
	private Flug flug;
	private Sitz sitz;
	private int gepaeckAnzahl;
	private Buchungsstatus status;
	private double gesamtpreis;

	/**
	 * Erstellt eine neue Buchung. Die Buchungsnummer wird automatisch generiert
	 * und der Status auf {@link Buchungsstatus#GEBUCHT} gesetzt.
	 * @param passagier = der buchende Passagier
	 * @param flug = der gebuchte Flug
	 * @param sitz = der zugewiesene Sitzplatz
	 * @param gepaeckAnzahl = die Anzahl der aufgegebenen Gepäckstücke
	 * @param gesamtpreis = der Gesamtpreis in Euro
	 */
	public Buchung(Passagier passagier, Flug flug, Sitz sitz, int gepaeckAnzahl, double gesamtpreis) {
		// Buchungsnummer automatisch generieren, z.B. "BU-0001"
		// %04d formatiert die Zahl mit führenden Nullen auf 4 Stellen, z.B. 1 → "0001"
		this.buchungsnummer = "BU-" + String.format("%04d", zaehler++);
		
		this.passagier = passagier;
		this.flug = flug;
		this.sitz = sitz;
		this.gepaeckAnzahl = gepaeckAnzahl;
		this.gesamtpreis = gesamtpreis;
		this.status = Buchungsstatus.GEBUCHT;
	}

	/**
	 * @return die eindeutige Buchungsnummer
	 */
	public String getBuchungsnummer() {
		return buchungsnummer;
	}

	public static void aktualisiereZaehler(List<Buchung> buchungen) {
		int hoechsteNummer = 0;

		for (Buchung buchung : buchungen) {
			String nummer = buchung.getBuchungsnummer();
			if (nummer == null || !nummer.startsWith("BU-")) {
				continue;
			}

			try {
				int wert = Integer.parseInt(nummer.substring(3));
				if (wert > hoechsteNummer) {
					hoechsteNummer = wert;
				}
			} catch (NumberFormatException e) {
				// Unbekannte Nummernformate werden ignoriert.
			}
		}

		zaehler = hoechsteNummer + 1;
	}

	/**
	 * @return der Passagier
	 */
	public Passagier getPassagier() {
		return passagier;
	}

	/**
	 * @return der Flug
	 */
	public Flug getFlug() {
		return flug;
	}

	/**
	 * @return der Sitz
	 */
	public Sitz getSitz() {
		return sitz;
	}

	/**
	 * @return die Gepäckanzahl
	 */
	public int getGepaeckAnzahl() {
		return gepaeckAnzahl;
	}

	/**
	 * @return der Buchungsstatus
	 */
	public Buchungsstatus getStatus() {
		return status;
	}

	/**
	 * @return der Gesamtpreis in Euro
	 */
	public double getGesamtpreis() {
		return gesamtpreis;
	}

	/**
	 * @param flug der neue Flug
	 */
	public void setFlug(Flug flug) {
		this.flug = flug;
	}

	/**
	 * @param sitz der neue Sitzplatz
	 */
	public void setSitz(Sitz sitz) {
		this.sitz = sitz;
	}

	/**
	 * @param status der neue Buchungsstatus
	 */
	public void setStatus(Buchungsstatus status) {
		this.status = status;
	}

	/**
	 * @param preis der neue Gesamtpreis in Euro
	 */
	public void setGesamtpreis(double preis) {
		this.gesamtpreis = preis;
	}

	/**
	 * Gibt eine lesbare Darstellung der Buchung zurück.
	 * Beispiel: {@code "BU-0001 | Max Mustermann | FRA → JFK | A3 | GEBUCHT | 450.0 EUR"}
	 * @return formatierte Zeichenkette mit allen wesentlichen Buchungsdaten
	 */
	public String toString() {
		return buchungsnummer + " | " + passagier.getVollerName()
				+ " | " + flug.baueRoute()
				+ " | " + sitz.getSitzName()
				+ " | " + status
				+ " | " + gesamtpreis + " EUR";
	}
}
