package ru.andrew.spring.project3RestSpring.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SensorErrorResponse {
    private String message;
    private long timestamp;

    public SensorErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
