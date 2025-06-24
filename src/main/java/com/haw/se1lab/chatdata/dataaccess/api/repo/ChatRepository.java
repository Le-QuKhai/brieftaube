package com.haw.se1lab.chatdata.dataaccess.api.repo;

import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import com.haw.se1lab.users.dataaccess.api.entity.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository für die Chat Entity
 */
public interface ChatRepository extends JpaRepository<Chat, Long>
{
    /**
     * Es wird in einem Chat geguckt ob ein bestimmter Teilnehmer in diesen Chat drinnen ist.
     * Es wird die ID zurückgegeben, des Teilnehmers, wenn es in dem Chat drinnen ist.
     * @param chatId ID des Chats
     * @param participantId Id des Teilnehmers
     * @return
     */
    @Query("SELECT t.id FROM Chat c JOIN c.teilnehmer t WHERE c.id = :ChatId AND t.id = :ParticipantId")
    Optional<Long> findParticipantInChat(@Param("ChatId") Long chatId, @Param("ParticipantId") Long participantId);

    /**
     * Checkt ob ein Chat mit der Id existiert
     * @param chatId die Id die überprüft werden soll
     * @return 1, wenn ein Chat mit der Id existiert.
     */
    // TODO check if Query works
    @Query("SELECT 1 FROM Chat c WHERE c.id = :ChatId")
    Optional<Integer> checkIfChatExists(@Param("ChatId") Long chatId);

    /**
     * Holt alle Chats, die in den der Benutzer drinnen ist.
     * @param benutzerId die Id des Benutzers
     * @return eine Liste, mit allen Chats
     */
    @Query("SELECT c FROM Chat c JOIN c.teilnehmer t WHERE t.id = :benutzerId")
    Optional<List<Chat>> findMyChats(@Param("benutzerId") Long benutzerId);

    /**
     * Gibt den Chat mit der Id wieder.
     * @param chatId die Id des Chats, der gesucht werden soll.
     * @return den Chat mit der Id oder null, wenn kein Chat mit der Id existiert.
     */
    @Override
    Optional<Chat> findById(Long chatId);

}
