package com.pwr.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AdminQueueController {
    @Autowired
    private SqsClient sqsClient;

    private final String adminQueueUrl = "https://sqs.us-east-1.amazonaws.com/637423541704/admin-queue";

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/messages")
    public ResponseEntity<List<String>> getAdminMessages() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(adminQueueUrl)
                .maxNumberOfMessages(10) // Maksymalnie 10 wiadomości jednocześnie
                .waitTimeSeconds(5) // Long-polling
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();

        List<String> messageBodies = messages.stream()
                .map(Message::body) // Pobierz treści wiadomości
                .collect(Collectors.toList());

        // Opcjonalnie usuń wiadomości z kolejki po odczytaniu
        messages.forEach(message -> sqsClient.deleteMessage(DeleteMessageRequest.builder()
                .queueUrl(adminQueueUrl)
                .receiptHandle(message.receiptHandle())
                .build()));

        return ResponseEntity.ok(messageBodies);
    }
}
