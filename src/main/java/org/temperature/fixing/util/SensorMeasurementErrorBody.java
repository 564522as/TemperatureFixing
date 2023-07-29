package org.temperature.fixing.util;

public class SensorMeasurementErrorBody {
    private String message;
    private long timestamp;

    public SensorMeasurementErrorBody(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public SensorMeasurementErrorBody() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
