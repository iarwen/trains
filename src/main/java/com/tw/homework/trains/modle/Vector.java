package com.tw.homework.trains.modle;

/**
 * 描述城市与城市之间的向量
 *
 * @author changwentao
 */
public class Vector {
    private CityNode from;
    private CityNode to;

    private int distance;

    public CityNode getFrom() {
        return from;
    }

    public void setFrom(CityNode from) {
        this.from = from;
    }

    public CityNode getTo() {
        return to;
    }

    public void setTo(CityNode to) {
        this.to = to;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
