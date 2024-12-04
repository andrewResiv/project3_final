package ru.andrew.spring.project3RestSpring.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.andrew.spring.project3RestSpring.dto.SensorDTO;
import ru.andrew.spring.project3RestSpring.models.Sensor;
import ru.andrew.spring.project3RestSpring.services.SensorService;
import ru.andrew.spring.project3RestSpring.exceptions.SensorNotCreatedException;
import ru.andrew.spring.project3RestSpring.util.SensorValidator;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorService sensorService, SensorValidator sensorValidator) {
        this.sensorService = sensorService;

        this.sensorValidator = sensorValidator;
    }

    @GetMapping()
    public List<SensorDTO> getSensors() {
        return sensorService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<SensorDTO> getSensorById(@PathVariable int id) {
        return sensorService.findById(id);
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> createSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult) {
        // Преобразуем DTO в модель
        Sensor sensor = sensorService.convertToSensor(sensorDTO);
        // Проверка через валидатор
        sensorValidator.validate(sensor, bindingResult);

        // ЕСли есть ошибки валидации
        if (bindingResult.hasErrors()) {
            StringBuilder errorsMsg = new StringBuilder();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorsMsg.append(fieldError.getField())
                        .append(": ")
                        .append(fieldError.getDefaultMessage())
                        .append("\n");
            }

            // Ошибки с подробным сообщением
            throw new SensorNotCreatedException(errorsMsg.toString());
        }

        //Если нет ошибок, сохраняем сенсор
        sensorService.save(sensor);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
