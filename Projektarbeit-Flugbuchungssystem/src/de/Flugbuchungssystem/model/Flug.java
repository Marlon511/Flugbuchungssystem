package de.Flugbuchungssystem.model;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import de.Flugbuchungssystem.model.*;

/**
 * Repräsentiert einen konkreten Flug mit Route, Zeit und Preis.
 * Bietet Hilfsmethoden zur Suche nach Datum und Route und delegiert
 * Sitzabfragen an das zugewiesene {@link #flugzeug}.
 */
public class Flug implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String flugnummer;
	private Fluggesellschaft airline;
	private Flughafen start;
	private Flughafen ziel;
	private LocalDateTime abflugzeit;
	private LocalDateTime ankunftszeit;
	private double basispreis;
	private Flugzeug flugzeug;

	/**
	 * Erstellt einen neuen Flug mit allen notwendigen Daten.
	 * @param flugnummer = die eindeutige Flugnummer
	 * @param airline = die durchführende Fluggesellschaft
	 * @param start = der Startflughafen
	 * @param ziel = der Zielflughafen
	 * @param abflugzeit = Datum und Uhrzeit des Abflugs
	 * @param ankunftszeit = Datum und Uhrzeit der Ankunft
	 * @param basispreis = der Grundpreis in Euro
	 * @param flugzeug = das eingesetzte Flugzeug
	 */
	public Flug(String flugnummer, Fluggesellschaft airline, Flughafen start, Flughafen ziel,
			LocalDateTime abflugzeit, LocalDateTime ankunftszeit, double basispreis, Flugzeug flugzeug) {
		this.flugnummer = flugnummer;
		this.airline = airline;
		this.start = start;
		this.ziel = ziel;
		this.abflugzeit = abflugzeit;
		this.ankunftszeit = ankunftszeit;
		this.basispreis = basispreis;
		this.flugzeug = flugzeug;
	}

	/**
	 * @return die Flugnummer
	 */
	public String getFlugnummer() {
		return flugnummer;
	}

	/**
	 * @return die Fluggesellschaft
	 */
	public Fluggesellschaft getAirline() {
		return airline;
	}

	/**
	 * @return der Startflughafen
	 */
	public Flughafen getStart() {
		return start;
	}

	/**
	 * @return der Zielflughafen
	 */
	public Flughafen getZiel() {
		return ziel;
	}

	/**
	 * @return Datum und Uhrzeit des Abflugs
	 */
	public LocalDateTime getAbflugzeit() {
		return abflugzeit;
	}

	/**
	 * @return Datum und Uhrzeit der Ankunft
	 */
	public LocalDateTime getAnkunftszeit() {
		return ankunftszeit;
	}

	/**
	 * @return der Grundpreis in Euro
	 */
	public double getBasispreis() {
		return basispreis;
	}

	/**
	 * @return das Flugzeug
	 */
	public Flugzeug getFlugzeug() {
		return flugzeug;
	}

	/**
	 * @return die Route als Zeichenkette
	 */
	public String baueRoute() {
		return start.getIataCode() + " → " + ziel.getIataCode();
	}

	/**
	 * Prüft, ob dieser Flug der angegebenen Route entspricht.
	 *
	 * @param start der zu prüfende Startflughafen
	 * @param ziel  der zu prüfende Zielflughafen
	 * @return {@code true}, wenn Start- und Zielflughafen übereinstimmen
	 */
	public boolean istRoute(Flughafen start, Flughafen ziel) {
		return this.start.getIataCode().equals(start.getIataCode())
				&& this.ziel.getIataCode().equals(ziel.getIataCode());
	}

	/**
	 * Prüft, ob dieser Flug am angegebenen Datum stattfindet.
	 * @param datum das zu prüfende Datum
	 * @return {@code true}, wenn der Abflug am angegebenen Datum liegt
	 */
	public boolean istAmDatum(LocalDate datum) {
		return this.abflugzeit.toLocalDate().equals(datum);
	}

	/**
	 * Gibt die freien Sitze der angegebenen Kategorie zurück.
	 * Delegiert die Abfrage an das zugewiesene Flugzeug.
	 * @param kategorie die gewünschte Sitzkategorie
	 * @return Liste der freien Sitze in der angegebenen Kategorie
	 */
	public List<Sitz> getFreieSitze(SitzKategorie kategorie) {
		return flugzeug.getFreieSitze(kategorie);
	}

	/**
	 * Gibt eine lesbare Darstellung des Flugs zurück.
	 * Beispiel: {@code "LH400 | FRA → JFK | 2026-06-01T10:00 → 2026-06-01T14:00"}
	 * @return formatierte Zeichenkette mit Flugnummer, Route und Zeiten
	 */
	public String toString() {
		return flugnummer + " | " + baueRoute() + " | " + abflugzeit + " → " + ankunftszeit;
	}
}