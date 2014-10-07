package com.zuehlke.carrera.bot.model;

/**
 * Specifies the type of a RaceTrackEvent
 */
public enum SensorEventType {

    /**
     * Sensor-Data directly from the Car.
     */
    CAR_SENSOR_DATA,

    /**
     * The relay track has sent a start-passed event (round start/end toggle, issued from the light barrier)
     */
    ROUND_PASSED
}
