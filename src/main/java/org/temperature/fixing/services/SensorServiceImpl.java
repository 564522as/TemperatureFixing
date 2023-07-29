package org.temperature.fixing.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.temperature.fixing.dto.SensorDTO;
import org.temperature.fixing.mappers.SensorMapper;
import org.temperature.fixing.models.Sensor;
import org.temperature.fixing.repositories.SensorRepository;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class SensorServiceImpl implements SensorService{
    private final SensorRepository sensorRepository;
    private final SensorMapper sensorMapper;

    public SensorServiceImpl(SensorRepository sensorRepository, SensorMapper sensorMapper) {
        this.sensorRepository = sensorRepository;
        this.sensorMapper = sensorMapper;
    }

    public Sensor save(SensorDTO sensorDTO) {
        Sensor sensor = this.sensorMapper.toEntity(sensorDTO);
        sensor.setDateAdd(new Date());
        return this.sensorRepository.save(sensor);
    }

    @Transactional(readOnly = true)
    public Optional<Sensor> findByName(String name) {
        return this.sensorRepository.findByName(name);
    }

}
