package de.Flugbuchungssystem.model;

/**
 * Klassifiziert die Sitzkategorien eines Flugzeugs und legt deren Preismultiplikator fest.
 * Der Multiplikator wird im {@link PreisService} mit dem Basispreis des Fluges verrechnet.
 */
public enum SitzKategorie {

    /** First-Class-Sitz; Preis = Basispreis × 2,0. */
    FIRST(2.0),

    /** Business-Class-Sitz; Preis = Basispreis × 1,5. */
    BUSINESS(1.5),

    /** Economy-Class-Sitz; Preis = Basispreis × 1,0. */
    ECONOMY(1.0);

    /** Der Faktor, mit dem der Basispreis multipliziert wird. */
    private final double preisMultiplikator;

    /**
     * Erstellt eine Sitzkategorie mit dem angegebenen Preismultiplikator.
     *
     * @param preisMultiplikator der Faktor für die Preisberechnung
     */
    SitzKategorie(double preisMultiplikator) {
        this.preisMultiplikator = preisMultiplikator;
    }

    /**
     * Gibt den Preismultiplikator dieser Kategorie zurück.
     *
     * @return der Preisfaktor, z.B. 1.5 für Business
     */
    public double getPreisMultiplikator() {
        return preisMultiplikator;
    }
}