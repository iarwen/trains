package com.tw.homework.trains;

import com.tw.homework.trains.conditions.ConditionConstants;
import com.tw.homework.trains.conditions.SearchStopCondition;
import com.tw.homework.trains.conditions.SearchStopConditionFactory;
import com.tw.homework.trains.modle.*;

import java.util.*;

/**
 * Function: 路径搜索类. <br>
 * Date : 2018年10月30日 9:53 <br>
 *
 * @author : changwentao
 */
public class RouteSearchEngine {
    /**
     * 键值对方式直接获取确定路径的长度
     *
     * @param edges 包含起点终点权重的路径 [source, target, weight]
     * @param route 确定的路径
     * @return 返回确定路径的长度，如果不可达返回Integer.MAX_VALUE
     */
    public int certainRouteDistance(String[][] edges, String route) {
        Map<String, Integer> vectorMap = new HashMap<>();
        for (String[] edge : edges) {
            vectorMap.put(edge[0] + edge[1], new Integer(edge[2]));
        }
        int distance = 0;
        String[] routes = route.split("-");
        for (int i = 1; i < routes.length; i++) {
            String cur = routes[i - 1] + routes[i];
            if (vectorMap.containsKey(cur)) {
                distance += vectorMap.get(cur);
            } else {
                return Integer.MAX_VALUE;
            }
        }
        return distance;
    }

    /**
     * 迪杰斯特拉最小堆最短路径算法
     *
     * @param edges 包含起点终点权重的路径 [source, target, weight]
     * @param s     要搜索的最短路径的起点
     * @return 起点s可达的所有终点的最短路径
     */
    public Map<String, Integer> dijkstraShortestRoute(String[][] edges, String s) {
        Map<String, List<TargetCity>> adjMap = new HashMap<>();
        for (String[] edge : edges) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(new TargetCity(edge[1], edge[2]));
        }

        Map<String, Integer> dist = new HashMap<>();
        PriorityQueue<TargetCity> minHeap = new PriorityQueue<>(Comparator.comparingInt(TargetCity::getDistance));
        minHeap.offer(new TargetCity(s, 0));

        while (!minHeap.isEmpty()) {
            TargetCity curr = minHeap.poll();
            if (dist.containsKey(curr.getTo())) continue;
            if (curr.getDistance() > 0) {
                dist.put(curr.getTo(), curr.getDistance());
            }
            if (adjMap.containsKey(curr.getTo())) {
                for (TargetCity edge : adjMap.get(curr.getTo())) {
                    minHeap.offer(new TargetCity(edge.getTo(), edge.getDistance() + curr.getDistance()));
                }
            }
        }

        return dist;
    }

    /**
     * 普通路径算法-最大步数
     *
     * @param edges 包含起点终点权重的路径 [source, target, weight]
     * @param s     要搜索的路径的起点
     * @param e     要搜索的路径的起点
     * @return 起点s达到e的所有路径，考虑环以及起点终点是自身
     */
    public List<RouteDistance> maxStopRoute(String[][] edges,
                                            String s,
                                            String e,
                                            int stops) {
        Map<String, List<TargetCity>> adjMap = new HashMap<>();
        for (String[] edge : edges) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(new TargetCity(edge[1], edge[2]));
        }

        List<RouteDistance> routeDistances = new ArrayList<>(16);
        List<RouteDistance> tempRouteDistances = new ArrayList<>();

        //起点
        RouteDistance start = new RouteDistance();
        start.addStop(new TargetCity(s, 0));
        tempRouteDistances.add(start);

        SearchStopConditionFactory factory = new SearchStopConditionFactory();
        //递归遍历
        while (!tempRouteDistances.isEmpty()) {
            RouteDistance curr = tempRouteDistances.remove(tempRouteDistances.size() - 1);
            String lastStop = curr.getSb().substring(curr.getSb().length() - 1);
            if (adjMap.containsKey(lastStop)) {
                for (TargetCity edge : adjMap.get(lastStop)) {
                    RouteDistance routeDistance = new RouteDistance();
                    routeDistance.addStop(new TargetCity(curr.getSb().toString(), curr.getDistance()));

                    TargetCity targetCity = new TargetCity(edge.getTo(), edge.getDistance());
                    routeDistance.addStop(targetCity);
                    SearchStopCondition condition = factory.getMaxStopCondition(routeDistance, stops);

                    if (e.equals(edge.getTo())) {
                        routeDistances.add(routeDistance);
                    }

                    if (!condition.canStop()) {
                        tempRouteDistances.add(routeDistance);
                    }
                }
            }
        }
        return routeDistances;
    }

    /**
     * 普通路径算法-明确步数
     *
     * @param edges 包含起点终点权重的路径 [source, target, weight]
     * @param s     要搜索的路径的起点
     * @param e     要搜索的路径的起点
     * @return 起点s达到e的所有路径，考虑环以及起点终点是自身
     */
    public List<RouteDistance> exactlyStopsRoute(String[][] edges,
                                                 String s,
                                                 String e,
                                                 int stops) {
        Map<String, List<TargetCity>> adjMap = new HashMap<>();
        for (String[] edge : edges) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(new TargetCity(edge[1], edge[2]));
        }

        List<RouteDistance> routeDistances = new ArrayList<>(16);
        List<RouteDistance> tempRouteDistances = new ArrayList<>();

        //起点
        RouteDistance start = new RouteDistance();
        start.addStop(new TargetCity(s, 0));
        tempRouteDistances.add(start);

        SearchStopConditionFactory factory = new SearchStopConditionFactory();
        //递归遍历
        while (!tempRouteDistances.isEmpty()) {
            RouteDistance curr = tempRouteDistances.remove(tempRouteDistances.size() - 1);
            String lastStop = curr.getSb().substring(curr.getSb().length() - 1);
            if (adjMap.containsKey(lastStop)) {
                for (TargetCity edge : adjMap.get(lastStop)) {
                    RouteDistance routeDistance = new RouteDistance();
                    routeDistance.addStop(new TargetCity(curr.getSb().toString(), curr.getDistance()));

                    TargetCity targetCity = new TargetCity(edge.getTo(), edge.getDistance());
                    routeDistance.addStop(targetCity);
                    SearchStopCondition condition = factory.getExactlyStopsCondition(routeDistance, stops);

                    if (e.equals(edge.getTo())) {
                        routeDistances.add(routeDistance);
                    }

                    if (!condition.canStop()) {
                        tempRouteDistances.add(routeDistance);
                    }
                }
            }
        }

        //确定的步数将步数相等的过滤出来返回
        List<RouteDistance> result = new ArrayList<>(16);
        routeDistances.forEach(routeDistance -> {
            if (routeDistance.getSb().length() - 1 == stops) {
                result.add(routeDistance);
            }
        });
        return result;
    }

    /**
     * 普通路径算法-最大距离
     *
     * @param edges 包含起点终点权重的路径 [source, target, weight]
     * @param s     要搜索的路径的起点
     * @param e     要搜索的路径的起点
     * @return 起点s达到e的所有路径，考虑环以及起点终点是自身
     */
    public List<RouteDistance> maxDistanceRoute(String[][] edges,
                                                String s,
                                                String e,
                                                int maxDistance) {
        Map<String, List<TargetCity>> adjMap = new HashMap<>();
        for (String[] edge : edges) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(new TargetCity(edge[1], edge[2]));
        }

        List<RouteDistance> routeDistances = new ArrayList<>(16);
        List<RouteDistance> tempRouteDistances = new ArrayList<>();

        //起点
        RouteDistance start = new RouteDistance();
        start.addStop(new TargetCity(s, 0));
        tempRouteDistances.add(start);

        SearchStopConditionFactory factory = new SearchStopConditionFactory();
        //递归遍历
        while (!tempRouteDistances.isEmpty()) {
            RouteDistance curr = tempRouteDistances.remove(tempRouteDistances.size() - 1);
            String lastStop = curr.getSb().substring(curr.getSb().length() - 1);
            if (adjMap.containsKey(lastStop)) {
                for (TargetCity edge : adjMap.get(lastStop)) {
                    RouteDistance routeDistance = new RouteDistance();
                    routeDistance.addStop(new TargetCity(curr.getSb().toString(), curr.getDistance()));

                    TargetCity targetCity = new TargetCity(edge.getTo(), edge.getDistance());
                    routeDistance.addStop(targetCity);
                    SearchStopCondition condition = factory.getMaxDistanceCondition(routeDistance, maxDistance);

                    if (e.equals(edge.getTo())) {
                        routeDistances.add(routeDistance);
                    }

                    if (!condition.canStop()) {
                        tempRouteDistances.add(routeDistance);
                    }
                }
            }
        }


        //确定的最大距离的将距离符合的过滤出来返回
        List<RouteDistance> result = new ArrayList<>(16);
        routeDistances.forEach(routeDistance -> {
            if (routeDistance.getDistance() < maxDistance) {
                result.add(routeDistance);
            }
        });
        return result;
    }

    /**
     * 输入数据检查 抛参数非法异常
     *
     * @param edges 包含起点终点权重的路径 [source, target, weight]
     */
    public void dataCheck(String[][] edges) {
        Map<String, String> adjMap = new HashMap<>();
        for (String[] edge : edges) {
            if (edge[0].equals(edge[1])) {
                throw new IllegalArgumentException("The given route has same start and end:" + edge[0] + edge[1]);
            }
            String key = edge[0] + edge[1];
            if (adjMap.containsKey(key)) {
                throw new IllegalArgumentException("The same route has appear more than once:" + key);
            }
            adjMap.put(edge[0] + edge[1], "TEMP");
        }
    }

}
