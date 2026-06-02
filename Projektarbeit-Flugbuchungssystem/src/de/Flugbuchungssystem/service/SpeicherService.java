package de.Flugbuchungssystem.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import de.Flugbuchungssystem.model.Buchung;

/**
 * Zuständig für das Speichern und Laden aller Programmdaten.
 */
public class SpeicherService {

    private static final String ORDNER = "data";
    private static final String DATEI = ORDNER + File.separator + "flugbuchungssystem.ser";
    
    /**
     * Lädt die gespeicherten Daten aus der Datei.
     * @return gespeicherte Daten, oder null wenn keine Datei vorhanden
     */
    public Datenerhaltung laden() {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(DATEI))) {
        	Datenerhaltung daten = (Datenerhaltung) input.readObject();
            Buchung.aktualisiereZaehler(daten.getBuchungsRepo().getAlleBuchungen());
            return daten;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Gespeicherte Daten konnten nicht geladen werden: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Speichert alle Repositories in einer Datei.
     * @param daten die zu speichernden Repositories
     */
    
    public void speichern(Datenerhaltung daten) {
        File ordner = new File(ORDNER);
        if (!ordner.exists()) {
            ordner.mkdirs();
        }

        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(DATEI))) {
            output.writeObject(daten);
            System.out.println("Daten wurden gespeichert: " + DATEI);
        } catch (IOException e) {
            System.out.println("Daten konnten nicht gespeichert werden: " + e.getMessage());
        }
    }
}