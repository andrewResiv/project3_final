package ru.andrew.spring.project3RestSpring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.andrew.spring.project3RestSpring.models.Sensor;
import ru.andrew.spring.project3RestSpring.services.SensorService;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        // проверка уникальности имени сенсора
        if (sensorService.findByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "This sensor already exists",
                    "Sensor with this name already exists.");
        }
    }
}
