package org.temperature.fixing.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.temperature.fixing.dto.SensorDTO;
import org.temperature.fixing.models.Sensor;
import org.temperature.fixing.services.SensorServiceImpl;

import java.util.Optional;

@Component
public class SensorExistingValidation implements Validator {
    private final SensorServiceImpl sensorService;

    public SensorExistingValidation(SensorServiceImpl sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensor = (SensorDTO) target;
        Optional<Sensor> optionalSensor = this.sensorService.findByName(sensor.getName());

        if (optionalSensor.isEmpty()) {
            errors.rejectValue("name", "", "Sensor with this name not exist");
        }
    }
}
