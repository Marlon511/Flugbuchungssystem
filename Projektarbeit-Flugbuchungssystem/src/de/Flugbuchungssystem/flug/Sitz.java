package de.Flugbuchungssystem.flug;

public class Sitz {

	private char reihe;
	private int platz;
    private SitzKategorie kategorie;
    private boolean belegt;

    public Sitz(char reihe, int platz, SitzKategorie kategorie) {
    		this.reihe = reihe;
    		this.platz = platz;
    		this.kategorie = kategorie;
    		this.belegt = false;
    	}

    public String getSitzName() {
    		return "" + reihe + platz;
    }

    public boolean isBelegt() {
    		return belegt;
    }

    public void belegen() {
		this.belegt = true;
	}
    
    public void freigeben() {
		this.belegt = false;
	}

	public SitzKategorie getKategorie() {
		return kategorie;
	}
}
