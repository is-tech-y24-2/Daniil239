package ru.itmo.kotiki.dto;

public record RabbitOwnerMessage(
        OperationType operationType,
        Integer entityId,
        String name,
        AuthUser user) {
}
