package ru.andrew.spring.project3RestSpring.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SensorDTO {
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;
}
