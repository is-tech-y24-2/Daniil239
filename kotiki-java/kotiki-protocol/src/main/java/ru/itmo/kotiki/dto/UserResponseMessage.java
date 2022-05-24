package ru.itmo.kotiki.dto;

public record UserResponseMessage(String name, String password, boolean enabled, Roles role, Integer ownerId) {
}
