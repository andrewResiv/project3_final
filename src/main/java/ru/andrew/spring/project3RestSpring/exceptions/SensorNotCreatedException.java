package ru.andrew.spring.project3RestSpring.exceptions;

public class SensorNotCreatedException extends RuntimeException {
    public SensorNotCreatedException(String message) {
        super(message);
    }
}
