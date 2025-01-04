package com.pwr.project.services;

import com.pwr.project.dto.MessageDTO;
import com.pwr.project.entities.Message;
import com.pwr.project.repositories.MessageRepository;
import com.pwr.project.repositories.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private NoticeRepository noticeRepository;

    public List<MessageDTO> getMessages(Long offerId, String userId) {
        String offerCreatorId = noticeRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Nie poprawne ID oferty"))
                .getCreatedBy();

        List<Message> messages = messageRepository.findByOfferId(offerId);

        List<MessageDTO> filteredMessages = messages.stream()
                .filter(message -> message.getSenderId().equals(userId) ||
                        message.getRecipientId().equals(userId) ||
                        (message.getSenderId().equals(offerCreatorId) &&
                        message.getRecipientId().equals(offerCreatorId)))
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return filteredMessages;
    }

    public void sendMessage(Long offerId, MessageDTO messageDTO) {
        Message message = new Message();
        message.setMessage(messageDTO.message());
        message.setSenderId(messageDTO.senderId());
        message.setRecipientId(messageDTO.recipientId());
        LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(messageDTO.timestamp()), ZoneId.systemDefault());
        message.setTimestamp(timestamp);
        message.setOfferId(offerId);
        message.setSenderName(messageDTO.senderName());
        messageRepository.save(message);
    }

    public List<MessageDTO> getUserMessages(String userId) {
        List<Message> messages = messageRepository.findByRecipientId(userId);
        return messages.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private MessageDTO convertToDTO(Message message) {
        long timestamp = message.getTimestamp().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return new MessageDTO(
                message.getMessage(),
                message.getSenderId(),
                message.getRecipientId(),
                timestamp,
                message.getSenderName()
        );
    }
}
