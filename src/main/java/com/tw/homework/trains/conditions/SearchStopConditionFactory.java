package com.tw.homework.trains.conditions;

import com.tw.homework.trains.modle.RouteDistance;

/**
 * Function: trains. <br>
 * Date : 2018年10月29日 17:15 <br>
 *
 * @author : changwentao
 */
public class SearchStopConditionFactory {
    //使用 getCondition 方法获取条件对象
    public SearchStopCondition getMaxStopCondition(RouteDistance route, int stops) {
        return new MaxStopsSearchStopCondition(route, stops);
    }

    public SearchStopCondition getMaxDistanceCondition(RouteDistance route, int distance) {
        return new MaxDistanceSearchStopCondition(route, distance);
    }

    public SearchStopCondition getExactlyStopsCondition(RouteDistance route, int stops) {
        return new ExactlyStopsSearchStopCondition(route, stops);
    }
}
