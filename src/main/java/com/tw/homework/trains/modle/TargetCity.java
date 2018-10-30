package com.tw.homework.trains.modle;

/**
 * Function: 目的地以及直线距离，构造起点的所有直达点的对象. <br>
 * Date : 2018年10月29日 13:51 <br>
 *
 * @author : changwentao
 */
public class TargetCity {
    private String to;

    private int distance;

    public TargetCity(String to, String distance) {
        this.to = to;
        this.distance = new Integer(distance);
    }

    public TargetCity(String to, int distance) {
        this.to = to;
        this.distance = distance;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
