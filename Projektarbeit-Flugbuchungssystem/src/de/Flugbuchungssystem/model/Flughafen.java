package de.Flugbuchungssystem.model;
import java.io.Serializable;

/**
 * Repräsentiert einen realen Flughafen mit allen Stammdaten.
 * Speichert die Stammdaten eines Flughafens.
 */
public class Flughafen implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String iataCode;
	private String stadt;
	private String land;

	/**
	 * Erstellt einen neuen Flughafen mit den angegebenen Stammdaten.
	 * @param name = der vollständige Name des Flughafens
	 * @param iataCode =  der dreistellige IATA-Code
	 * @param stadt = die Stadt des Flughafens
	 * @param land = das Land des Flughafens
	 */
	public Flughafen(String name, String iataCode, String stadt, String land) {
		this.name = name;
		this.iataCode = iataCode;
		this.stadt = stadt;
		this.land = land;
	}

	/**
	 * @return Der Flughafenname
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Der dreistellige IATA-Code
	 */
	public String getIataCode() {
		return iataCode;
	}

	/**
	 * @return Die Stadt
	 */
	public String getStadt() {
		return stadt;
	}

	/**
	 * @return Das Land
	 */
	public String getLand() {
		return land;
	}

	/**
	 * Gibt eine lesbare Darstellung des Flughafens zurück.
	 * Beispiel: {@code "FRA – Frankfurt am Main (DE)"} 
	 * @return formatierte Zeichenkette mit IATA-Code, Stadt und Land
	 */
	public String toString() {
		return iataCode + " – " + stadt + " (" + land + ")";
	}
}