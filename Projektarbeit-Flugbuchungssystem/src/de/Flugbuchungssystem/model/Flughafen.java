package de.Flugbuchungssystem.model;
/**
 * Repräsentiert einen realen Flughafen mit allen Stammdaten.
 * Reine Datenklasse ohne Geschäftslogik — nur Getter und {@link #toString()}.
 */

public class Flughafen {

	/** Der vollständige Name des Flughafens, z.B. „Frankfurt am Main". */
	private String name;

	/** Der dreistellige IATA-Code des Flughafens, z.B. „FRA". */
	private String iataCode;

	/** Die Stadt, in der sich der Flughafen befindet. */
	private String stadt;

	/** Das Land, in dem sich der Flughafen befindet, z.B. „DE". */
	private String land;

	/**
	 * Erstellt einen neuen Flughafen mit den angegebenen Stammdaten.
	 *
	 * @param name     der vollständige Name des Flughafens
	 * @param iataCode der dreistellige IATA-Code
	 * @param stadt    die Stadt des Flughafens
	 * @param land     das Land des Flughafens
	 */
	public Flughafen(String name, String iataCode, String stadt, String land) {
		this.name = name;
		this.iataCode = iataCode;
		this.stadt = stadt;
		this.land = land;
	}

	/**
	 * Gibt den vollständigen Namen des Flughafens zurück.
	 *
	 * @return Der Flughafenname
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gibt den IATA-Code des Flughafens zurück.
	 *
	 * @return Der dreistellige IATA-Code
	 */
	public String getIataCode() {
		return iataCode;
	}

	/**
	 * Gibt die Stadt des Flughafens zurück.
	 *
	 * @return Die Stadt
	 */
	public String getStadt() {
		return stadt;
	}

	/**
	 * Gibt das Land des Flughafens zurück.
	 *
	 * @return Das Land
	 */
	public String getLand() {
		return land;
	}

	/**
	 * Gibt eine lesbare Darstellung des Flughafens zurück.
	 * Beispiel: {@code "FRA – Frankfurt am Main (DE)"} 
	 *
	 * @return formatierte Zeichenkette mit IATA-Code, Stadt und Land
	 */
	public String toString() {
		return iataCode + " – " + stadt + " (" + land + ")";
	}
}