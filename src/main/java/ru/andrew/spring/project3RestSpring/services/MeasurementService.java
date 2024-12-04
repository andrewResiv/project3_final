package ru.andrew.spring.project3RestSpring.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrew.spring.project3RestSpring.dto.MeasurementDTO;
import ru.andrew.spring.project3RestSpring.models.Measurement;
import ru.andrew.spring.project3RestSpring.models.Sensor;
import ru.andrew.spring.project3RestSpring.repositories.MeasurementRepository;
import ru.andrew.spring.project3RestSpring.repositories.SensorRepository;
import ru.andrew.spring.project3RestSpring.exceptions.SensorNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository, ModelMapper modelMapper) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
        this.modelMapper = modelMapper;
    }

    public List<Measurement> getMeasurementByName(String name) {
        return measurementRepository.findBySensor_Name(name);
    }
    public List<MeasurementDTO> getAllMeasurements() {
        return measurementRepository.findAll().stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    public Integer countByRainingDays(){
        return measurementRepository.countByRainingTrue();
    }

    @Transactional
    public void saveMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        Sensor sensor = sensorRepository.findByName(measurement.getSensor().getName())
                .orElseThrow(SensorNotFoundException::new);
        sensor.addMeasurement(measurement);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }
}
