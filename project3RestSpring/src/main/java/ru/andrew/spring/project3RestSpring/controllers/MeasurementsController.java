package ru.andrew.spring.project3RestSpring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.andrew.spring.project3RestSpring.models.Measurement;
import ru.andrew.spring.project3RestSpring.services.MeasurementService;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    public final MeasurementService measurementService;

    @Autowired
    public MeasurementsController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping()
    public List<Measurement> getMeasurements() {
        return measurementService.getAllMeasurements();
    }

    @PostMapping("/add")
    public Measurement addMeasurement(@RequestBody Measurement measurement) {

    }
}
