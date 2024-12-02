package ru.andrew.spring.project3RestSpring.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import ru.andrew.spring.project3RestSpring.models.Sensor;

@Setter
@Getter
public class MeasurementDTO {

    @Min(value = -100, message = "value should be greater then -100")
    @Max(value = 100, message = "value should be less then 100")
    private double value;


    @NotEmpty
    private boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;
}
