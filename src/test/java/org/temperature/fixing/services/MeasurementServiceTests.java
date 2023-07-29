package org.temperature.fixing;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.temperature.fixing.dto.MeasurementDTO;
import org.temperature.fixing.dto.SensorDTO;
import org.temperature.fixing.mappers.MeasurementMapper;
import org.temperature.fixing.mappers.SensorMapper;
import org.temperature.fixing.models.Measurement;
import org.temperature.fixing.models.Sensor;
import org.temperature.fixing.repositories.MeasurementRepository;
import org.temperature.fixing.repositories.SensorRepository;
import org.temperature.fixing.services.MeasurementServiceImpl;
import org.temperature.fixing.services.SensorServiceImpl;

import java.util.Optional;
import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.any;


@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MeasurementServiceTests {
    Logger logger = Logger.getLogger("MeasurementServiceTest");
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private MeasurementRepository measurementRepository;
    private MeasurementServiceImpl measurementService;


    @Test
    void whenGiveDTO_ThanShouldSaveEntity() {
        SensorDTO sensorDTO = new SensorDTO("sensor");
        MeasurementDTO measurementDTO = new MeasurementDTO(34f, sensorDTO);

        MeasurementMapper measurementMapper1 = Mockito.mock(MeasurementMapper.class);
        Mockito
                .when(measurementMapper1.toEntity(measurementDTO))
                .thenReturn(new Measurement(34f, new Sensor("sensor")));

        this.measurementService = new MeasurementServiceImpl(this.measurementRepository, measurementMapper1);
        Measurement measurement = this.measurementService.save(measurementDTO);

        Measurement savedMeasurement = this.entityManager.find(Measurement.class, measurement.getId());

        Assertions.assertNotNull(measurement);
        Assertions.assertNotNull(savedMeasurement);

    }

}
