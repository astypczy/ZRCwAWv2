package com.pwr.project.entities;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "messages")
@Data
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String senderId;

    @Column(nullable = false)
    private String recipientId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private Long offerId;

    @Column(nullable = false)
    private String senderName;

}
