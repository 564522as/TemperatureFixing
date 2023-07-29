package org.temperature.fixing.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.temperature.fixing.models.Sensor;
import org.temperature.fixing.services.SensorService;

import java.util.Optional;
@Component
public class SensorUniqueNameValidation implements Validator {
    private final SensorService sensorService;

    public SensorUniqueNameValidation(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        Optional<Sensor> optionalSensor = this.sensorService.findByName(sensor.getName());

        if (optionalSensor.isPresent()) {
            errors.rejectValue("name", "", "Sensor with this name has already exist");
        }

    }
}
