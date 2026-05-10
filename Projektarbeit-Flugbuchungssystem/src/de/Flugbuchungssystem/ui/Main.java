
package de.Flugbuchungssystem.ui;
import de.Flugbuchungssystem.flug.*;
import de.Flugbuchungssystem.service.*;
import de.Flugbuchungssystem.stammdaten.*;
import de.Flugbuchungssystem.buchung.*;

public class Main {

	public static void main(String[] args) {
		Flugzeug f1 = new Flugzeug("A380", "LH-1512",4,8,8,4,12,3);

		for (Sitz s : f1.getSitze()) {
        System.out.println(s.getSitzName() + " - " + s.getKategorie());
		}

	}

}
