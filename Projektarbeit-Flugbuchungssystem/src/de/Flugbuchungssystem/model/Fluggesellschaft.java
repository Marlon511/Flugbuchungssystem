package de.Flugbuchungssystem.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert eine Fluggesellschaft mit ihrer Flotte und allen angebotenen Flügen.
 * Dient als Ausgangspunkt beim Anlegen von Flugzeugen und Flügen.
 */
public class Fluggesellschaft implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private Flughafen heimflughafen;
	private String code;
	private List<Flugzeug> flotte;

	/**
	 * Erstellt eine neue Fluggesellschaft mit leerem Fuhrpark und leerer Flugliste.
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
	 * @param flugzeug das hinzuzufügende Flugzeug
	 */
	public void addFlugzeug(Flugzeug flugzeug) {
		this.flotte.add(flugzeug);
	}

	/**
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
	 * @return den Heimflughafen
	 */	
	public Flughafen getHeimflughafen() {
		return heimflughafen;
	}

	/**
	 * @return der Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return das IATA-Kürzel
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Gibt eine lesbare Darstellung der Fluggesellschaft zurück.
	 * Beispiel: {@code "LH – Lufthansa"}
	 * @return formatierte Zeichenkette mit Kürzel und Name
	 */
	public String toString() {
		return code + " – " + name;
	}
}