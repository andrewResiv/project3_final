package ru.andrew.spring.project3RestSpring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrew.spring.project3RestSpring.models.Sensor;
import ru.andrew.spring.project3RestSpring.repositories.SensorRepository;
import ru.andrew.spring.project3RestSpring.util.SensorNotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }
    public Sensor findById(int id) {
        return sensorRepository.findById(id).orElseThrow(SensorNotFoundException::new);
    }

    @Transactional
    public Sensor save(Sensor sensor) {
        return sensorRepository.save(sensor);
    }
    @Transactional
    public void deleteById(int id) {
        sensorRepository.deleteById(id);
    }

}
