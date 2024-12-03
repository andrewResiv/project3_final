package ru.andrew.spring.project3RestSpring.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andrew.spring.project3RestSpring.dto.SensorDTO;
import ru.andrew.spring.project3RestSpring.models.Sensor;
import ru.andrew.spring.project3RestSpring.repositories.SensorRepository;
import ru.andrew.spring.project3RestSpring.util.SensorNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final ModelMapper modelMapper;
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(ModelMapper modelMapper, SensorRepository sensorRepository) {
        this.modelMapper = modelMapper;
        this.sensorRepository = sensorRepository;
    }

    public List<SensorDTO> findAll() {
        return sensorRepository.findAll().stream()
                .map(this::convertToSensorDTO)
                .collect(Collectors.toList());
    }
    public SensorDTO findById(int id) {
        return convertToSensorDTO(sensorRepository.findById(id).orElseThrow(SensorNotFoundException::new));
    }

    public Optional<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }

    @Transactional
    public Sensor save(Sensor sensor) {
        return sensorRepository.save(sensor);
    }
    @Transactional
    public void deleteById(int id) {
        sensorRepository.deleteById(id);
    }


    public SensorDTO convertToSensorDTO(Sensor sensor){
        return modelMapper.map(sensor, SensorDTO.class);
    }

    public Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }

}