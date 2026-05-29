package de.Flugbuchungssystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert eine Fluggesellschaft mit ihrer Flotte und allen angebotenen Flügen.
 * Dient als Ausgangspunkt beim Anlegen von Flugzeugen und Flügen.
 */
public class Fluggesellschaft {

	/** Der vollständige Name der Fluggesellschaft, z.B. „Lufthansa". */
	private String name;
	
	/** Der Heimflughafen einer Fluggesellschaft. z.b „Frankfurt Airport"  */
	private Flughafen heimflughafen;

	/** Das IATA-Kürzel der Fluggesellschaft, z.B. „LH". */
	private String code;

	/** Alle Flugzeuge, die zur Flotte dieser Gesellschaft gehören. */
	private List<Flugzeug> flotte;

	/**
	 * Erstellt eine neue Fluggesellschaft mit leerem Fuhrpark und leerer Flugliste.
	 *
	 * @param name der vollständige Name der Fluggesellschaft
	 * @param code das IATA-Kürzel der Fluggesellschaft
	 */
	public Fluggesellschaft(String name, String code) {
		this.name = name;
		this.code = code;
		this.flotte = new ArrayList<>();
		this.heimflughafen = null;
	}

	/**
	 * Fügt ein Flugzeug zur Flotte der Gesellschaft hinzu.
	 *
	 * @param flugzeug das hinzuzufügende Flugzeug
	 */
	public void addFlugzeug(Flugzeug flugzeug) {
		this.flotte.add(flugzeug);
	}

	/**
	 * Gibt die Flotte der Fluggesellschaft zurück.
	 *
	 * @return Liste aller Flugzeuge
	 */
	public List<Flugzeug> getFlotte() {
		return flotte;
	}
	
	public void setHeimflughafen(Flughafen heimflughafen) {
		this.heimflughafen = heimflughafen;
		System.out.println("Der Heimflughafen wurde auf " + heimflughafen.getName()+ " gesetzt.");
	}
	/**
	 * Gibt den Heimflughafen einer Fluggesellschaft zurück.
	 *
	 * @return den Heimflughafen
	 */	
	public Flughafen getHeimflughafen() {
		return heimflughafen;
	}

	/**
	 * Gibt den Namen der Fluggesellschaft zurück.
	 *
	 * @return der Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gibt das Kürzel der Fluggesellschaft zurück.
	 *
	 * @return das IATA-Kürzel
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Gibt eine lesbare Darstellung der Fluggesellschaft zurück.
	 * Beispiel: {@code "LH – Lufthansa"}
	 *
	 * @return formatierte Zeichenkette mit Kürzel und Name
	 */
	public String toString() {
		return code + " – " + name;
	}
}