package com.tw.homework.trains.modle;

/**
 * Function: 记录全路径以及路径长度的类. <br>
 * Date : 2018年10月29日 16:14 <br>
 *
 * @author : changwentao
 */
public class RouteDistance {
    private StringBuilder sb = new StringBuilder(16);
    private int distance = 0;

    public void addStop(TargetCity targetCity) {
        sb.append(targetCity.getTo());
        this.distance += targetCity.getDistance();
    }

    public StringBuilder getSb() {
        return sb;
    }


    public int getDistance() {
        return distance;
    }

}
