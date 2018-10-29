package com.tw.homework.trains.modle;

/**
 * Function: trains. <br>
 * Date : 2018年10月29日 17:15 <br>
 *
 * @author : changwentao
 */
public class SearchStopConditionFactory {
    //使用 getCondition 方法获取条件对象
    public SearchStopCondition getCondition(String conditionName, RouteDistance route, int stops, int distance) {
        if (conditionName == null) {
            return null;
        }
        if (conditionName.equalsIgnoreCase(ConditionConstants.MaxStops)) {
            return new MaxStopsSearchStopCondition(route, stops);
        } else if (conditionName.equalsIgnoreCase(ConditionConstants.MaxDistance)) {
            return new MaxDistanceSearchStopCondition(route, distance);
        } else if (conditionName.equalsIgnoreCase(ConditionConstants.ExactlyStops)) {
            return new ExactlyStopsSearchStopCondition(route, stops);
        }
        return null;
    }
}
