package ru.andrew.spring.project3RestSpring.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.andrew.spring.project3RestSpring.dto.MeasurementDTO;
import ru.andrew.spring.project3RestSpring.models.Measurement;
import ru.andrew.spring.project3RestSpring.models.Sensor;
import ru.andrew.spring.project3RestSpring.services.MeasurementService;
import ru.andrew.spring.project3RestSpring.util.MeasurementNotCreatedException;
import ru.andrew.spring.project3RestSpring.util.SensorValidator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public MeasurementsController(MeasurementService measurementService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping()
    public List<MeasurementDTO> getMeasurements() {
        return measurementService.getAllMeasurements().stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public  ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                      BindingResult bindingResult) {
        Sensor sensor = modelMapper.map(measurementDTO, Sensor.class);
        sensorValidator.validate(sensor, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorsMsg = new StringBuilder();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorsMsg.append(fieldError.getField())
                        .append(": ")
                        .append(fieldError.getDefaultMessage())
                        .append("\n");
            }
            throw new MeasurementNotCreatedException(errorsMsg.toString());
        }
        measurementService.saveMeasurement(convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);

    }

    @GetMapping("/rainyDaysCount")
    public Integer getRainyDaysCount() {
        return measurementService.countByRainingDays();
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = new Measurement();
        measurement.setValue(measurementDTO.getValue());
        measurement.setRaining(measurementDTO.isRaining());

        Sensor sensor = new Sensor();
        sensor.setName(measurementDTO.getSensor().getName());
        measurement.setSensor(sensor);

        return measurement;
    }
}
