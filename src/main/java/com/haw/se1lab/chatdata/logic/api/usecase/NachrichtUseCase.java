package com.haw.se1lab.chatdata.logic.api.usecase;

import com.haw.se1lab.chatdata.common.api.datatype.NachrichtErstellung;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;

import java.util.List;

public interface NachrichtUseCase {

    /**
     * Erstellt eine Nachricht und speichert diese in der Datenbank
     * @param nachrichtErstellung enthält die Nachricht
     * und die ChatID des Chats zu den die Nachricht gehört. Dieser muss schon existieren.
     * @return die erstellte Nachricht.
     */
    Nachricht createNachricht(NachrichtErstellung nachrichtErstellung);

    /**
     * Überprüft, ob eine Nachricht schon existiert.
     * @param nachricht die Nachricht, die überprüft werden soll.
     * @return true, wenn sie existiert. False, wenn nicht.
     */
    boolean checkIfNachrichtExists(Nachricht nachricht);

    /**
     * Gibt alle neuen Nachrichten zurück, die seit der letzten Nachricht geschrieben wurden.
     * @param chatId die Id des Chats
     * @param lastMessageId die Id der letzten Nachricht
     * @return Liste von Nachrichten, leere Liste, wenn es keine neuen Nachrichten gibt.
     */
    List<Nachricht> getNewMessages(Long chatId, Long lastMessageId);
}
