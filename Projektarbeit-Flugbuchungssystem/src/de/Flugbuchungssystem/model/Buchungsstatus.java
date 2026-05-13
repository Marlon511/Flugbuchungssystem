package de.Flugbuchungssystem.model;
/**
 * Beschreibt den Lebenszyklus einer Buchung.
 * Eine neue Buchung beginnt immer mit dem Status {@link #GEBUCHT}.
 */
public enum Buchungsstatus {
	GEBUCHT, 	/** Die Buchung ist aktiv und gültig. */
	UMGEBUCHT, 	/** Die Buchung wurde auf einen anderen Flug umgebucht. */
	STORNIERT; 	/** Die Buchung wurde storniert; der Sitz ist wieder verfügbar. */

}
