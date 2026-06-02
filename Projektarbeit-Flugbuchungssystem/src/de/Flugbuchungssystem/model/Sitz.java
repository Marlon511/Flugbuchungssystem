package de.Flugbuchungssystem.model;
import java.io.Serializable;

/**
 * Repräsentiert einen einzelnen Sitzplatz in einem Flugzeug.
 * Jeder Sitz hat eine Reihe (Buchstabe), eine Platznummer und eine {@link SitzKategorie}.
 * Verwaltet außerdem den eigenen Belegungsstatus.
 */
public class Sitz implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /** Der Reihenbuchstabe des Sitzes, z.B. 'A'. */
    private char reihe;

    /** Die Platznummer innerhalb der Reihe, z.B. 3. */
    private int platz;

    /** Die Kategorie des Sitzes (ECONOMY, BUSINESS oder FIRST). */
    private SitzKategorie kategorie;

    /** Gibt an, ob dieser Sitz bereits belegt ist. */
    private boolean belegt;

    /**
     * Erstellt einen neuen, freien Sitz mit der angegebenen Reihe, Platznummer und Kategorie.
     *
     * @param reihe     der Reihenbuchstabe, z.B. 'A'
     * @param platz     die Platznummer innerhalb der Reihe
     * @param kategorie die Sitzkategorie
     */
    public Sitz(char reihe, int platz, SitzKategorie kategorie) {
        this.reihe = reihe;
        this.platz = platz;
        this.kategorie = kategorie;
        this.belegt = false;
    }

    /**
     * Gibt den Sitznamen als Zeichenkette zurück.
     * Beispiel: Reihe 'A', Platz 3 ergibt {@code "A3"}.
     *
     * @return der Sitzname aus Reihe und Platznummer
     */
    public String getSitzName() {
        return "" + reihe + platz;
    }

    /**
     * Gibt an, ob der Sitz belegt ist.
     *
     * @return {@code true}, wenn der Sitz belegt ist, sonst {@code false}
     */
    public boolean isBelegt() {
        return belegt;
    }

    /**
     * Markiert den Sitz als belegt.
     * Wird beim Erstellen einer Buchung aufgerufen.
     */
    public void belegen() {
        this.belegt = true;
    }

    /**
     * Gibt den Sitz wieder frei.
     * Wird bei Stornierung oder Umbuchung aufgerufen.
     */
    public void freigeben() {
        this.belegt = false;
    }

    /**
     * Gibt die Kategorie des Sitzes zurück.
     *
     * @return die Sitzkategorie
     */
    public SitzKategorie getKategorie() {
        return kategorie;
    }
}