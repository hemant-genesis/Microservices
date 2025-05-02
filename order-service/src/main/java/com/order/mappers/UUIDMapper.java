package com.order.mappers;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UUIDMapper {
    public String toString(UUID uuid) {
        return uuid.toString();
    }

    public UUID toUUID(String uuidString) {
        return UUID.fromString(uuidString);
    }
}
