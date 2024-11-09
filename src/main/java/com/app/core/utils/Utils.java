package com.app.core.utils;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Utils {

    public static <T extends Exception> void checkNull(Object value, String message, Class<T> exceptionClass) {
        if (value == null) {
            try {
                throw exceptionClass.getConstructor(String.class).newInstance(message);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create exception");
            }
        }
    }

    private Utils() {}
}

