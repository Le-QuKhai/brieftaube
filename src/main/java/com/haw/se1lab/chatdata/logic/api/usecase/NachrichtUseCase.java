package com.haw.se1lab.chatdata.logic.api.usecase;

import com.haw.se1lab.chatdata.common.api.datatype.NachrichtErstellung;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;

public interface NachrichtUseCase {

    /**
     * Erstellt eine Nachricht und speichert diese in der Datenbank
     * @param nachrichtErstellung enthält die Nachricht
     * und die ChatID des Chats zu den die Nachricht gehört. Dieser muss schon existieren.
     * @return die erstellte Nachricht.
     */
    Nachricht createNachricht(NachrichtErstellung nachrichtErstellung);

    /**
     * Überprüft, ob eine Nachricht anhand der Id schon existiert.
     * @param nachrichtId die Nachricht, die überprüft werden soll.
     * @return true, wenn sie existiert. False, wenn nicht.
     */
    boolean checkIfNachrichtExistsById(Long nachrichtId);
}
