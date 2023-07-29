package org.temperature.fixing.services;

import org.springframework.stereotype.Service;
import org.temperature.fixing.dto.MeasurementDTO;
import org.temperature.fixing.mappers.MeasurementMapper;
import org.temperature.fixing.models.Measurement;
import org.temperature.fixing.repositories.MeasurementRepository;

import java.util.Date;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final MeasurementMapper measurementMapper;

    public MeasurementService(MeasurementRepository measurementRepository, MeasurementMapper measurementMapper) {
        this.measurementRepository = measurementRepository;
        this.measurementMapper = measurementMapper;
    }

    public void save(MeasurementDTO measurementDTO) {
        Measurement measurement = this.measurementMapper.toEntity(measurementDTO);
        measurement.setDateOfMeasurement(new Date());
        this.measurementRepository.save(measurement);
    }



}
