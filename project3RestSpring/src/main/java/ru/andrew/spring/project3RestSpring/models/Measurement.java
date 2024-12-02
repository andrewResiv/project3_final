package ru.andrew.spring.project3RestSpring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Measurement {

    @Id
    @Column(name = "measurement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    @Min(value = -100, message = "value should be greater then -100")
    @Max(value = 100, message = "value should be less then 100")
    private double value;

    @Column(name = "raining")
    @NotEmpty
    private boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor_name", nullable = false)
    private Sensor sensor;

    public Measurement(double value, boolean raining) {
        this.value = value;
        this.raining = raining;
    }

    public Measurement() {}
}
