package org.temperature.fixing;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.temperature.fixing.dto.SensorDTO;
import org.temperature.fixing.mappers.SensorMapper;
import org.temperature.fixing.models.Measurement;
import org.temperature.fixing.models.Sensor;
import org.temperature.fixing.repositories.SensorRepository;
import org.temperature.fixing.services.SensorServiceImpl;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SensorServiceTests {
    private final SensorMapper sensorMapper;
    private SensorRepository sensorRepository;
    private SensorServiceImpl sensorService;
    @Autowired
    public SensorServiceTests(SensorMapper sensorMapper) {
        this.sensorMapper = sensorMapper;

    }

    @BeforeEach
    public void initRepositoryAndService() {
        this.sensorRepository = Mockito.mock(SensorRepository.class);
        this.sensorService = new SensorServiceImpl(this.sensorRepository, this.sensorMapper);

    }

    @Test
    void whenFindByNameCorrect_ThenReturnSensor(@Autowired SensorMapper sensorMapper) {
        Mockito
                .when(sensorRepository.findByName("sensor"))
                .thenReturn(Optional.of(new Sensor("sensor")));

        Assertions.assertTrue(sensorService.findByName("sensor").isPresent());
        Mockito.verify(sensorRepository).findByName("sensor");
    }

    @Test
    void whenFindByNameNotCorrect_ThanReturnEmptyResult() {
        Mockito
                .when(sensorRepository.findByName("sensor"))
                .thenReturn(Optional.empty());

        Assertions.assertTrue(sensorService.findByName("sensor").isEmpty());
        Mockito.verify(sensorRepository).findByName("sensor");
    }

    @Test
    void whenGiveSensorDTO_ThanShouldSaveSensor() {
        Sensor sensor = new Sensor("sensor");
        Mockito
                .when(this.sensorRepository.save(any(Sensor.class))).thenReturn(sensor);

        Sensor sensor1 = this.sensorService.save(new SensorDTO("sensor"));
        Mockito.verify(this.sensorRepository).save(any(Sensor.class));
        Assertions.assertNotNull(sensor1);
        Assertions.assertDoesNotThrow(() -> this.sensorService.save(new SensorDTO("sensor")));
    }

}
