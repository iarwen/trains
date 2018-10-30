package com.tw.homework.trains.modle;

/**
 * Function: 指定步数停止条件. <br>
 * Date : 2018年10月29日 16:20 <br>
 *
 * @author : changwentao
 */
public class ExactlyStopsSearchStopCondition implements SearchStopCondition {

    private RouteDistance route;
    private int stopCnt;

    ExactlyStopsSearchStopCondition(RouteDistance route, int stopCnt) {
        this.route = route;
        this.stopCnt = stopCnt;
    }


    @Override
    public boolean canStop() {
        return (route.getSb().length() - 1) == stopCnt;
    }
}
