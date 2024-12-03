package ru.andrew.spring.project3RestSpring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrew.spring.project3RestSpring.models.Measurement;
import ru.andrew.spring.project3RestSpring.models.Sensor;
import ru.andrew.spring.project3RestSpring.repositories.MeasurementRepository;
import ru.andrew.spring.project3RestSpring.repositories.SensorRepository;
import ru.andrew.spring.project3RestSpring.util.SensorNotFoundException;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }
    public List<Measurement> getMeasurementByName(String name) {
        return measurementRepository.findBySensor_Name(name);
    }
    public List<Measurement> getAllMeasurements() {
        return measurementRepository.findAll();
    }

    public Integer countByRainingDays(){
        return measurementRepository.countByRainingTrue();
    }

    @Transactional
    public void saveMeasurement(Measurement measurement) {
        Sensor sensor = sensorRepository.findByName(measurement.getSensor().getName())
                .orElseThrow(SensorNotFoundException::new);
        List<Measurement> measurements = sensor.getMeasurements();
        measurements.add(measurement);
        sensor.setMeasurements(measurements);
        sensorRepository.save(sensor);
    }

//    @Transactional
//    public Measurement create(Measurement measurement) {
//        Sensor sensor = sensorRepository.findByName(measurement.getSensor().getName())
//                .orElseGet(() -> {
//                    Sensor newSensor = new Sensor();
//                    newSensor.setName(measurement.getSensor().getName());
//                    return sensorRepository.save(newSensor);
//                });
//
//        measurement.setSensor(sensor);
//        //Добавим в измерение текущую дату.
//        measurement.setCreatedAt(new Date(System.currentTimeMillis()));
//        return measurementRepository.save(measurement);
//    }
}
