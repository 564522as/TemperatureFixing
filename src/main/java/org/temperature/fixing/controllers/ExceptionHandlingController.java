package org.temperature.fixing.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.temperature.fixing.util.MeasurementAddException;
import org.temperature.fixing.util.SensorMeasurementErrorBody;
import org.temperature.fixing.util.SensorNotExistException;
import org.temperature.fixing.util.SensorRegistrationException;

@RestControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<SensorMeasurementErrorBody> handleSensorRegException(SensorRegistrationException e) {
        SensorMeasurementErrorBody errorBody = new SensorMeasurementErrorBody(e.getMessage(), System.currentTimeMillis());
        return new HttpEntity<>(errorBody);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<SensorMeasurementErrorBody> handleSensorNotExistException(SensorNotExistException e) {
        SensorMeasurementErrorBody errorBody = new SensorMeasurementErrorBody(e.getMessage(), System.currentTimeMillis());
        return new HttpEntity<>(errorBody);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<SensorMeasurementErrorBody> handleMeasurementAddException(MeasurementAddException e) {
        SensorMeasurementErrorBody errorBody = new SensorMeasurementErrorBody(e.getMessage(), System.currentTimeMillis());
        return new HttpEntity<>(errorBody);
    }

}
