package ru.andrew.spring.project3RestSpring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "measurement")
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
    private boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor_name",referencedColumnName = "name", nullable = false)
    private Sensor sensor;

    @Column(name = "created_at")
    @CreationTimestamp()
    private Date createdAt;

    public Measurement(double value, boolean raining) {
        this.value = value;
        this.raining = raining;
    }

    public Measurement() {}

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
        if (sensor != null && !sensor.getMeasurements().contains(this)) {
            sensor.getMeasurements().add(this); // Устанавливаем обратную связь
        }
    }
}
