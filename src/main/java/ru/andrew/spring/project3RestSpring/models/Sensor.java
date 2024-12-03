package ru.andrew.spring.project3RestSpring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Sensor")
@Setter
@Getter
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;


    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Measurement> measurements = new ArrayList<>();


    public Sensor(String name) {
        this.name = name;
    }

    public Sensor() {}

    public void addMeasurement(Measurement measurement) {
        measurements.add(measurement);
        measurement.setSensor(this); // Устанавливаем обратную связь
    }

    public void removeMeasurement(Measurement measurement) {
        measurements.remove(measurement);
        measurement.setSensor(null); // Удаляем обратную связь
    }
}
