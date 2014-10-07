package com.zuehlke.carrera.bot.service;

import com.zuehlke.carrera.bot.model.SensorEvent;
import org.springframework.stereotype.Service;

/**
 * Created by paba on 10/5/14.
 */
@Service
public class MyBotService {


    /**
     * Occurs when a race starts.
     */
    public void start() {
        // TODO Maybe send initial velocity here...

    }

    /**
     * Occurs when the bot receives sensor data from the car or the race-track.
     * @param data
     */
    public void handleSensorEvent(SensorEvent data) {
        switch (data.getType()){
            case CAR_SENSOR_DATA:
                // Sensor data from the mounted car sensor
                // TODO
                break;

            case ROUND_PASSED:
                // A round has been passed - generated event from the light barrier
                // TODO
                break;
        }
    }



}
