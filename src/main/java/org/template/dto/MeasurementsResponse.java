package org.template.dto;

import java.util.List;

public class MeasurementsResponse {

    private List<MeasurementDTO> measurements;

    public MeasurementsResponse() {
    }

    public MeasurementsResponse(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }

    public List<MeasurementDTO> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }
}
