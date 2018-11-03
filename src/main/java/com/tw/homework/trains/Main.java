package com.tw.homework.trains;

import com.tw.homework.trains.conditions.ConditionConstants;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        //初始化录入数据
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
        RouteSearchEngine engine = new RouteSearchEngine();
        //检查数据
        engine.dataCheck(edges);
        //使用键值对的方式获取指定路径的距离
        printDistance(1, engine.certainRouteDistance(edges, "A-B-C"));
        printDistance(2, engine.certainRouteDistance(edges, "A-D"));
        printDistance(3, engine.certainRouteDistance(edges, "A-D-C"));
        printDistance(4, engine.certainRouteDistance(edges, "A-E-B-C-D"));
        printDistance(5, engine.certainRouteDistance(edges, "A-E-D"));

        //非最短路径的遍历
        printDistance(6, engine.maxStopRoute(edges, "C", "C", 3).size());
        printDistance(7, engine.exactlyStopsRoute(edges, "A", "C", 4).size());

        //迪杰斯特拉最小堆计算最短距离
        Map<String, Integer> result8 = engine.dijkstraShortestRoute(edges, "A");
        printDistance(8, result8.get("C"));
        Map<String, Integer> result9 = engine.dijkstraShortestRoute(edges, "B");
        printDistance(9, result9.get("B"));

        //非最短路径的遍历
        printDistance(10, engine.maxDistanceRoute(edges, "C", "C", 30).size());

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
