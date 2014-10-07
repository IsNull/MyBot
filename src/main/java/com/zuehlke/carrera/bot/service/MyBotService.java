package com.zuehlke.carrera.bot.service;

import com.zuehlke.carrera.bot.model.SensorEvent;
import com.zuehlke.carrera.bot.model.SpeedControl;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

/**
 * Contains the primary Bot AI.
 * Created by paba on 10/5/14.
 */
@Service
public class MyBotService {

    private static final String baseUrl = "http://relay2.beta.swisscloud.io";
    private static final String teamId = "testTeam";        // TODO Put here your team id
    private static final String accessCode = "1337toor";    // TODO Put here your team access code

    private final Client client;
    private final WebTarget relayRestApi;

    private final float MAX_Y_ACCELERATION = 40;


    /**
     * Creates a new MyBotService
     */
    public MyBotService(){
        client = ClientBuilder.newClient();
        relayRestApi = client.target(baseUrl).path("/ws/rest");
    }


    /**
     * Occurs when a race starts.
     */
    public void start() {
        // TODO Maybe send initial velocity here...
        sendSpeedControl(50);
    }

    /**
     * Occurs when the bot receives sensor data from the car or the race-track.
     * @param data
     */
    public void handleSensorEvent(SensorEvent data) {
        switch (data.getType()){
            case CAR_SENSOR_DATA:
                // Sensor data from the mounted car sensor
                // TODO Handle Car sensor data more intelligently

                // Simple, synchronous Bot implementation
                if(data.getAcc()[1] > MAX_Y_ACCELERATION) {
                    // Too High Y_ACCELERATION, reduce velocity
                    sendSpeedControl(45);
                }else{
                    // Y_ACCELERATION is ok, lets drive faster
                    sendSpeedControl(85);
                }
                break;

            case ROUND_PASSED:
                // A round has been passed - generated event from the light barrier
                // TODO Handle round passed event...
                break;
        }
    }

    /**
     * Sends the given power to the race-track using the rest API
     * @param power Power value in the range of [0 - 250]
     */
    public void sendSpeedControl(double power){
        SpeedControl control = new SpeedControl(power, teamId, accessCode, new Date().getTime());
        try {
            Response response = relayRestApi.path("relay/speed").request()
                    .post(Entity.entity(control, MediaType.APPLICATION_JSON));
        }catch (Exception e){
            e.printStackTrace(); // TODO better error handling
        }
    }



}
