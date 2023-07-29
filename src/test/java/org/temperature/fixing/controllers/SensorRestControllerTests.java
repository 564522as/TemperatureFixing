package org.temperature.fixing;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validation;
import org.hibernate.annotations.Immutable;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.temperature.fixing.controllers.SensorRestController;
import org.temperature.fixing.dto.SensorDTO;
import org.temperature.fixing.models.Sensor;
import org.temperature.fixing.services.SensorServiceImpl;
import org.temperature.fixing.util.SensorNameValidation;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SensorRestController.class)
@Import(SensorNameValidation.class)
public class SensorRestControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SensorServiceImpl sensorService;
    @Test
    public void whenSendDTOToSave_thenStatusOk() throws Exception {
        SensorDTO sensor = new SensorDTO("sensor");
        Mockito.when(sensorService.save(any(SensorDTO.class))).thenReturn(any(Sensor.class));

        mockMvc.perform(post("http://localhost:8080/sensor/registration")
                .content(objectMapper.writeValueAsString(sensor))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void whenSendEmptyDTOToSave_thenResponseWithClientError() throws Exception {
        SensorDTO sensor = new SensorDTO();
        Mockito.when(sensorService.save(any(SensorDTO.class))).thenReturn(any(Sensor.class));

        mockMvc.perform(post("http://localhost:8080/sensor/registration")
                        .content(objectMapper.writeValueAsString(sensor))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept("application/json;charset=UTF-8"))
                        .andExpect(status().is4xxClientError())
                        .andDo(print());
    }
}
