package com.order.utils;

import java.util.UUID;

public class Utils {
    public static String toString(UUID uuid){
        return uuid.toString();
    }

    public static UUID toUUID(String uuidString){
        return UUID.fromString(uuidString);
    }
}
