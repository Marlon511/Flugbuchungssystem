package de.Flugbuchungssystem.model;

/**
 * Beschreibt den Status einer Buchung.
 * Eine neue Buchung beginnt immer mit dem Status {@link #GEBUCHT}.
 */
public enum Buchungsstatus {
    /** Die Buchung ist aktiv und gültig. */
    GEBUCHT,
    /** Die Buchung wurde auf einen anderen Flug umgebucht. */
    UMGEBUCHT,
    /** Die Buchung wurde storniert; der Sitz ist wieder verfügbar. */
    STORNIERT;
}