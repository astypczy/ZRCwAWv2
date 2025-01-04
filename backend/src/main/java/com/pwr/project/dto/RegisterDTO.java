package com.pwr.project.dto;

public record RegisterDTO (
        String firstname,
        String surname,
        String login,
        String password,
        Boolean isSeller
) {}
