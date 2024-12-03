package ru.andrew.spring.project3RestSpring.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SensorErrorResponce extends RuntimeException {
    private String message;
    private long timestamp;

    public SensorErrorResponce(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
