package org.temperature.fixing.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.temperature.fixing.dto.MeasurementDTO;
import org.temperature.fixing.models.Measurement;
import org.temperature.fixing.models.Sensor;
import org.temperature.fixing.services.MeasurementService;
import org.temperature.fixing.services.SensorService;
import org.temperature.fixing.util.MeasurementAddException;
import org.temperature.fixing.util.MeasurementsSensorExistingValidator;
import org.temperature.fixing.util.SensorNotExistException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/measurements")
public class MeasurementRestController {
    private final MeasurementsSensorExistingValidator measurementsSensorExistingValidator;

    private final MeasurementService measurementService;
    private final SensorService sensorService;

    public MeasurementRestController(MeasurementsSensorExistingValidator measurementsSensorExistingValidator, MeasurementService measurementService, SensorService sensorService) {
        this.measurementsSensorExistingValidator = measurementsSensorExistingValidator;
        this.measurementService = measurementService;
        this.sensorService = sensorService;
    }

    @PostMapping(value = "/add",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public HttpEntity<String> add(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        this.measurementsSensorExistingValidator.validate(measurementDTO, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            StringBuilder message = new StringBuilder();
            message.append("message ");
            //Building the error message to response
            for (FieldError fieldError: bindingResult.getFieldErrors()) {
                message.append(fieldError.getField());
                message.append(" - ");
                message.append(fieldError.getDefaultMessage());
                message.append("; ");
            }
            throw new MeasurementAddException(message.toString());
        }
        this.measurementService.save(measurementDTO);
        return new HttpEntity<>("The measurement was added");
    }

    @GetMapping(value = "/all",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<MeasurementDTO> getAll(@RequestParam(value = "sensor", required = false)
                                        String sensorName) {
        Optional<Sensor> sensor;
        //Check if sensor's name has given and is there sensor with this name in database
        //If all it true then return measurements from explicit sensor
        if (sensorName != null) {
            sensor = this.sensorService.findByName(sensorName);
            if (sensor.isEmpty()) {
                throw new SensorNotExistException("Sensor with this name not exist");
            } else {
                return this.measurementService.findBySensor(sensorName);
            }
        }
        //Else return all measurements
        return this.measurementService.findAll();
    }

    @GetMapping(value = "/all",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            params = {"page", "size"})
    public List<MeasurementDTO> getAllWithPagination(
            @RequestParam(value = "sensor", required = false) String sensorName,
            @RequestParam("page") @Min(0) int page,
            @RequestParam("size") @Min(1) @Max(10) int size
    ) {
        Optional<Sensor> sensor;
        //Check if sensor's name has given and is there sensor with this name in database
        //If all it true then return measurements from explicit sensor
        if (sensorName != null) {
            sensor = this.sensorService.findByName(sensorName);
            if (sensor.isEmpty()) {
                throw new SensorNotExistException("Sensor with this name not exist");
            } else {
                return this.measurementService.findBySensorWithPagination(sensorName, page, size);
            }
        }

        return this.measurementService.findAllWithPagination(page, size);
    }

    @GetMapping(value = "/rainyDaysCount",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer rainyDays() {
        return this.measurementService.rainyDays();
    }
}
