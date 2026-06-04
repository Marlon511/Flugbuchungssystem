package de.Flugbuchungssystem.ui;

/**
 * Enthält statische Hilfsmethoden zur Validierung von Nutzereingaben.
 * Wird von {@link KonsolenUI} verwendet, um Eingaben vor der Verarbeitung zu prüfen.
 * Alle Methoden sind statisch — kein Objekt dieser Klasse wird benötigt.
 */
public class InputValidator {

    /**
     * Prüft, ob ein Name ausschließlich aus Buchstaben, Leerzeichen,
     * Bindestrichen und Apostrophen besteht.
     * Ziffern sind nicht erlaubt.
     * @param name der zu prüfende Vor- oder Nachname
     * @return {@code true}, wenn der Name keine Ziffern enthält und nicht leer ist, sonst {@code false}
     */
    public static boolean istGueltigerName(String name) {
        if (name.isEmpty()) {
            return false;
        }
        for (char c : name.toCharArray()) {
            if (Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prüft, ob eine Telefonnummer ausschließlich aus erlaubten Zeichen besteht.
     * Erlaubt sind Ziffern, {@code +}, {@code -}, {@code (}, {@code )} und Leerzeichen.
     * @param nummer die zu prüfende Telefonnummer
     * @return {@code true}, wenn die Nummer nicht leer ist und nur erlaubte Zeichen enthält, sonst {@code false}
     */
    public static boolean istGueltigeTelefonnummer(String nummer) {
        if (nummer.isEmpty()) {
            return false;
        }
        for (char c : nummer.toCharArray()) {
            if (!Character.isDigit(c) && c != '+' && c != '-' && c != ' ' && c != '(' && c != ')') {
                return false;
            }
        }
        return true;
    }
}