package com.tw.homework.trains.modle;

/**
 * Function: 最远距离停止条件. <br>
 * Date : 2018年10月29日 16:20 <br>
 *
 * @author : changwentao
 */
public class MaxDistanceSearchStopCondition implements SearchStopCondition {

    private RouteDistance route;
    private int maxDistance;

    MaxDistanceSearchStopCondition(RouteDistance route, int maxDistance) {
        this.route = route;
        this.maxDistance = maxDistance;
    }

    @Override
    public boolean canStop() {
        return route.getDistance() >= maxDistance;
    }
}
