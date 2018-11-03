package com.tw.homework.trains.conditions;

import com.tw.homework.trains.modle.RouteDistance;

/**
 * Function: 最大步数停止条件. <br>
 * Date : 2018年10月29日 16:20 <br>
 *
 * @author : changwentao
 */
public class MaxStopsSearchStopCondition implements SearchStopCondition {

    private RouteDistance route;
    private int stopCnt;

    MaxStopsSearchStopCondition(RouteDistance route, int stopCnt) {
        this.route = route;
        this.stopCnt = stopCnt;
    }


    @Override
    public boolean canStop() {
        return (route.getSb().length() - 1) >= stopCnt;
    }
}
