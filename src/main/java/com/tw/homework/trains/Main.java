package com.tw.homework.trains;

import com.tw.homework.trains.modle.*;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {

    /**
     * 键值对方式直接获取确定路径的长度
     *
     * @param edges 包含起点终点权重的路径 [source, target, weight]
     * @param route 确定的路径
     * @return 返回确定路径的长度，如果不可达返回Integer.MAX_VALUE
     */
    private int certainRouteDistance(String[][] edges, String route) {
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
     * 迪杰斯特拉最短路径算法
     *
     * @param edges 包含起点终点权重的路径 [source, target, weight]
     * @param s     要搜索的最短路径的起点
     * @return 起点s可达的所有终点的最短路径，但不考虑环以及起点终点是自身
     */
    private Map<String, Integer> dijkstraShortestRoute(String[][] edges, String s) {
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
     * 普通路径算法
     *
     * @param edges 包含起点终点权重的路径 [source, target, weight]
     * @param s     要搜索的路径的起点
     * @param e     要搜索的路径的起点
     * @return 起点s达到e的所有路径，考虑环以及起点终点是自身
     */
    private List<RouteDistance> normalRoute(String[][] edges,
                                            String s,
                                            String e,
                                            int stops, int maxDistance,
                                            String conditionName) {
        Map<String, List<TargetCity>> adjMap = new HashMap<>();
        for (String[] edge : edges) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(new TargetCity(edge[1], edge[2]));
        }

        List<RouteDistance> routeDistances = new ArrayList<>(16);
        LinkedBlockingDeque<RouteDistance> tempRouteDistances = new LinkedBlockingDeque<>();

        //起点
        RouteDistance start = new RouteDistance();
        start.addStop(new TargetCity(s, 0));
        tempRouteDistances.add(start);

        SearchStopConditionFactory factory = new SearchStopConditionFactory();
        //递归遍历
        while (!tempRouteDistances.isEmpty()) {
            RouteDistance curr = tempRouteDistances.poll();
            String lastStop = curr.getSb().substring(curr.getSb().length() - 1);
            if (adjMap.containsKey(lastStop)) {
                for (TargetCity edge : adjMap.get(lastStop)) {
                    RouteDistance routeDistance = new RouteDistance();
                    routeDistance.addStop(new TargetCity(curr.getSb().toString(), curr.getDistance()));

                    TargetCity targetCity = new TargetCity(edge.getTo(), edge.getDistance());
                    routeDistance.addStop(targetCity);
                    SearchStopCondition condition = factory.getCondition(conditionName, routeDistance, stops, maxDistance);

                    if (e.equals(edge.getTo())) {
                        routeDistances.add(routeDistance);
                    }

                    if (!condition.canStop()) {
                        tempRouteDistances.offer(routeDistance);
                    }
                }
            }
        }

        //最大步数直接返回
        if (stops > 0 && ConditionConstants.MaxStops.equals(conditionName)) {
            return routeDistances;
        }
        //确定的步数将步数相等的过滤出来返回
        List<RouteDistance> result = new ArrayList<>(16);
        if (stops > 0 && ConditionConstants.ExactlyStops.equals(conditionName)) {
            routeDistances.forEach(routeDistance -> {
                if (routeDistance.getSb().length() - 1 == stops) {
                    result.add(routeDistance);
                }
            });
        }
        //确定的最大距离的将距离符合的过滤出来返回
        if (maxDistance > 0 && ConditionConstants.MaxDistance.equals(conditionName)) {
            routeDistances.forEach(routeDistance -> {
                if (routeDistance.getDistance() < maxDistance) {
                    result.add(routeDistance);
                }
            });
        }
        return result;
    }


    /**
     * 输入数据检查 抛参数非法异常
     *
     * @param edges 包含起点终点权重的路径 [source, target, weight]
     */
    private void dataCheck(String[][] edges) {
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


    public static void main(String[] args) {

        String[][] edges = {
                {"A", "B", "5"},
                {"B", "C", "4"},
                {"C", "D", "8"},
                {"D", "C", "8"},
                {"D", "E", "6"},
                {"A", "D", "5"},
                {"C", "E", "2"},
                {"E", "B", "3"},
                {"A", "E", "7"}
        };
        Main main = new Main();
        main.dataCheck(edges);
        printDistance(1, main.certainRouteDistance(edges, "A-B-C"));
        printDistance(2, main.certainRouteDistance(edges, "A-D"));
        printDistance(3, main.certainRouteDistance(edges, "A-D-C"));
        printDistance(4, main.certainRouteDistance(edges, "A-E-B-C-D"));
        printDistance(5, main.certainRouteDistance(edges, "A-E-D"));


        printDistance(6, main.normalRoute(edges, "C", "C", 3, -1, ConditionConstants.MaxStops).size());
        printDistance(7, main.normalRoute(edges, "A", "C", 4, -1, ConditionConstants.ExactlyStops).size());

        Map<String, Integer> result8 = main.dijkstraShortestRoute(edges, "A");
        printDistance(8, result8.get("C"));
        Map<String, Integer> result9 = main.dijkstraShortestRoute(edges, "B");
        printDistance(9, result9.get("B"));

        printDistance(10, main.normalRoute(edges, "C", "C", -1, 30, ConditionConstants.MaxDistance).size());


    }

    private static void printDistance(int number, int distance) {
        System.out.print("Output #" + number + ": ");
        if (distance == Integer.MAX_VALUE) {
            System.out.println("NO SUCH ROUTE");
        } else {
            System.out.println(distance);
        }
    }
}
