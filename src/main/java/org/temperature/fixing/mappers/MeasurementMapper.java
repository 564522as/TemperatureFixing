package org.temperature.fixing.mappers;

import org.temperature.fixing.models.Measurement;
import org.temperature.fixing.dto.MeasurementDTO

public class MeasurementMapper implements SimpleMapper<Measurement, org.temperature.fixing.dto.MeasurementDTO> {
    @Override
    public Measurement toEntity(org.temperature.fixing.dto.MeasurementDTO measurementDTO) {
        return null;
    }

    @Override
    public org.temperature.fixing.dto.MeasurementDTO toDTO(Measurement measurement) {
        return null;
    }
}
