package de.Flugbuchungssystem.flug;

public enum SitzKategorie {

	FIRST(2.0),
	BUSINESS(1.6),
    ECONOMY(1.0);
	
	private final double preisMultiplikator;

    SitzKategorie(double preisMultiplikator) {
    		this.preisMultiplikator = preisMultiplikator;
    }

    public double getPreisMultiplikator() {
    		return preisMultiplikator;
    }
}
