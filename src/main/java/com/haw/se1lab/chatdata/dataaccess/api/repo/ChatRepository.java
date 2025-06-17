package com.haw.se1lab.chatdata.dataaccess.api.repo;

import com.haw.se1lab.chatdata.dataaccess.api.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


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

    // TODO check if Query works
    @Query("SELECT 1 FROM Chat c WHERE c.id = :ChatId")
    Optional<Integer> checkIfChatExists(@Param("ChatId") Long chatId);

}
