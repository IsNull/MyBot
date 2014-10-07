package com.zuehlke.carrera.bot.model;

import java.io.Serializable;

/**
 * A SpeedControl contains the power value which controls the
 * velocity of a relay-car.
 *
 * Created by wgiersche on 06/09/14.
 */
public class SpeedControl implements Serializable {

    private String teamId;
    private String accessCode;
    private double power;
    private long timeStamp;

    /**
     * Empty Constructor for Serialisation
     */
    protected SpeedControl () {
    }


    /**
     * Creates a new SpeedControl with the given power
     *
     * @param power Power value in range [0-250]
     * @param teamId
     * @param accessCode
     */
    public SpeedControl ( double power, String teamId, String accessCode, long timestamp ) {

        this.power = power;
        this.accessCode = accessCode;
        this.teamId = teamId;
        this.timeStamp = timestamp;
    }

    /**
     * Get the power
     * @return
     */
    public double getPower() {
        return power;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public String toString(){
        return getPower() + "";
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
