package com.user.mappers;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UUIDMapper {
    public UUID toUUID(String id) {
        return id != null ? UUID.fromString(id) : null;
    }

    public String toString(UUID id) {
        return id != null ? id.toString() : null;
    }
}
