package com.pwr.project.repositories;

import com.pwr.project.entities.Message;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByOfferId(Long offerId);
    List<Message> findByRecipientId(String recipientId);
}
