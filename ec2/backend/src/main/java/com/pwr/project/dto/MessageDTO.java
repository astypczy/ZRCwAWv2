package com.pwr.project.dto;

import java.time.LocalDateTime;

public record MessageDTO (
        String message,
        String senderId,
        String recipientId,
        long timestamp,

        String senderName
) {}
