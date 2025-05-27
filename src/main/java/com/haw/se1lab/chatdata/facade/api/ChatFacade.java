package com.haw.se1lab.chatdata.facade.api;


import com.haw.se1lab.chatdata.common.api.exception.ParticipantAlreadyExistsException;
import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(path="/chat")
public interface ChatFacade
{
    /**
     * Fügt einen Benutzer zu diesem Chat hinzu
     * @param chat der Chat zu dem der Benutzer hinzugefügt werden soll
     * @param teilnehmer der Benutzer der zu dem Chat hinzugefügt werden soll
     * @return der hinzugefügt Benutzer
     * @throws ParticipantAlreadyExistsException wenn der Benutzer schon im Chat existiert.
     */
    @PostMapping("/add")
   void addParticipant(@RequestBody Chat chat, @RequestBody Benutzer teilnehmer) throws ParticipantAlreadyExistsException;

    @PostMapping()
    void createChat(@RequestBody Chat chat);
}
