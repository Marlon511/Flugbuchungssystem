package de.Flugbuchungssystem.repository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.Flugbuchungssystem.model.Passagier;

/**
 * Verwaltet alle registrierten Passagiere der Anwendung in einer internen Liste.
 * Ermöglicht die Suche nach E-Mail-Adresse, um zu prüfen ob ein Passagier
 * bereits bekannt ist. Enthält keine Geschäftslogik, nur Datenhaltung.
 */
public class PassagierRepository implements Serializable {
	
	private static final long serialVersionUID = 1L;

    /** Interne Liste aller registrierten Passagiere. */
    private ArrayList<Passagier> passagiere;

    /**
     * Erstellt ein neues, leeres PassagierRepository.
     */
    public PassagierRepository() {
        this.passagiere = new ArrayList<>();
    }

    /**
     * Fügt einen neuen Passagier in das Repository ein.
     *
     * @param passagier der hinzuzufügende Passagier
     */
    public void addPassagier(Passagier passagier) {
        passagiere.add(passagier);
    }

    /**
     * Sucht einen Passagier anhand seiner E-Mail-Adresse.
     * Gibt {@code null} zurück, wenn kein Passagier mit dieser Adresse gefunden wird —
     * so kann der Aufrufer prüfen, ob der Passagier bereits registriert ist.
     *
     * @param email die E-Mail-Adresse des gesuchten Passagiers
     * @return der gefundene Passagier, oder {@code null} wenn nicht vorhanden
     */
    public Passagier findePassagier(String email) {
        for (Passagier passagier : passagiere) {
            if (passagier.getEmail().equals(email)) {
                return passagier;
            }
        }
        return null;
    }

    /**
     * Gibt alle registrierten Passagiere zurück.
     *
     * @return Liste aller Passagiere, leer wenn keine vorhanden
     */
    public List<Passagier> getAllePassagiere() {
        return passagiere;
    }
}
