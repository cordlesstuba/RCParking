package com.rcp.rcparking;

/**
 * Created by gcarves on 13/04/2017.
 */

public class Travel {

    static double distance;
    static double duration;

    public Travel(double distance, double duration) {
        this.distance = distance;
        this.duration = duration;
    }
    public static double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public static double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }




}
