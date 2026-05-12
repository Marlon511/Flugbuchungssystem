package de.Flugbuchungssystem.stammdaten;

public class Passagier {

	private String vorname;
	private String nachname;
    private String email;
    private String telefonnummer;

    public Passagier(String vorname, String nachname, String email, String telefonnummer) {

    		this.vorname = vorname;
    		this.nachname = nachname;
    		this.telefonnummer = telefonnummer;
    		this.email = email;


    		}

    public String getVorname() {
    		return this.vorname;
    		}

    public String getNachname() {
    		return this.nachname;
    		}

    public String getEmail() {
    		return this.email;
    		}

    public String getTelefonnummer() {
	        return this.telefonnummer;
    		}

    public String getVollerName() {
	        return this.vorname + " " + this.nachname;
	    }

    public String toString() {
    		return getVollerName() + 
    				" | " + this.email + 
    				" | " + this.telefonnummer;
    		}
}
