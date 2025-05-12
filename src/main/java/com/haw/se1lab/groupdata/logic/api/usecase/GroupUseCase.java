package com.haw.se1lab.groupdata.logic.api.usecase;

import com.haw.se1lab.groupdata.common.api.exception.GroupNameInvalidException;
import com.haw.se1lab.groupdata.dataaccess.api.entity.Gruppe;

public interface GroupUseCase {

    /**
     * Validiert den Gruppen Namen ob dieser korrekt ist.
     * Ein Gruppen name ist korrekt, wenn er zwischen 1 und 50 Zeichen lang ist
     * 9. Das System prüft ob der eingegebene Gruppenname eine Länge zwischen 1 und 50 Zeichen hat.
     * @param groupName der eingeben Gruppen Name
     * @return true, wenn er der Gruppen Name valid ist, false wenn nicht
     */
    public boolean validateGroupName(String groupName);

    /**
     * Erstellt eine Gruppe
     * 10. Das System speichert die Gruppeninformationen in der Datenbank
     * @param gruppe die Gruppe die erstellt werden soll
     * @return die erstellte Gruppe
     */
    public Gruppe createGruppe(Gruppe gruppe);

}
