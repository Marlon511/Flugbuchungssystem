package de.Flugbuchungssystem.flug;

public class Sitz {

	private char reihe;
	private int platz;
    private SitzKategorie kategorie;
    private boolean gebucht;

    public Sitz(char reihe, int platz, SitzKategorie kategorie) {
    		this.reihe = reihe;
    		this.platz = platz;
    		this.kategorie = kategorie;
    		this.gebucht = false;
    	}

    public String getSitzName() {
    		return "" + reihe + platz;
    }

    public boolean isGebucht() {
    		return gebucht;
    }

    public void buche() {
		this.gebucht = true;
	}

	public SitzKategorie getKategorie() {
		return kategorie;
	}
}
