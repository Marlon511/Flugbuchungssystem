package de.Flugbuchungssystem.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.Flugbuchungssystem.interfaces.IFlugRepository;
import de.Flugbuchungssystem.interfaces.IFlugSuchService;
import de.Flugbuchungssystem.model.Flug;
import de.Flugbuchungssystem.model.Flughafen;

/**
 * Durchsucht alle gespeicherten Flüge nach Start, Ziel und optionalem Datum.
 * Holt die vollständige Flügeliste aus dem Repository und filtert sie.
 */
public class FlugSuchService implements IFlugSuchService {
    private IFlugRepository flugRepo;

    /**
     * Erstellt einen neuen FlugSuchService.
     * @param flugRepo das Repository für den Zugriff auf Flüge
     */
    public FlugSuchService(IFlugRepository flugRepo) {
        this.flugRepo = flugRepo;
    }

    /**
     * Sucht alle Flüge, die der angegebenen Route entsprechen.
     * Wird {@code datum} als {@code null} übergeben, wird nur nach Route gefiltert.
     * @param start das Startflughafen
     * @param ziel  der Zielflughafen
     * @param datum der gewünschte Abflugtag, oder {@code null} für alle Daten
     * @return Liste aller passenden Flüge, leer wenn keine gefunden
     */
    @Override
    public List<Flug> sucheFluege(Flughafen start, Flughafen ziel, LocalDate datum) {
        List<Flug> ergebnis = new ArrayList<>();

        for (Flug flug : flugRepo.getAlleFluege()) {
            if (!flug.istRoute(start, ziel)) {
                continue;
            }
            if (datum != null && !flug.istAmDatum(datum)) {
                continue;
            }
            ergebnis.add(flug);
        }

        return ergebnis;
    }
}
