package ru.andrew.spring.project3RestSpring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrew.spring.project3RestSpring.models.Measurement;
import ru.andrew.spring.project3RestSpring.repositories.MeasurementRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }
    public Measurement getMeasurementById(int id) {
        return measurementRepository.findById(id).orElse(null);
    }
    public List<Measurement> getAllMeasurements() {
        return measurementRepository.findAll();
    }

    @Transactional
    public Measurement createMeasurement(Measurement measurement) {
        return measurementRepository.save(measurement);
    }
}
