package com.haw.se1lab.chatdata.logic.api.usecase;

import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Nachricht;

public interface NachrichtUseCase {

    Nachricht createNachricht(Nachricht nachricht, Chat chat);

    boolean checkIfNachrichtExists(Nachricht nachricht);
}
