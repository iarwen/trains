package com.tw.homework.trains;

import java.util.*;

public class Main {

    private int routeDistance(String[][] edges, String route) {
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
     * Given a list of (source, target, weight) edge pairs, return the shortest distance from s to any
     * other nodes in the graph. Any unreachable node has a distance of Integer.MAX_VALUE.
     *
     * @param edges List of tuple representation of edges containing [source, target weight].
     * @param s     Start node of the shortest path tree
     * @return Shortest path from s to other nodes in the graph.
     */
    private Map<String, Integer> dijkstraShortestRoute(String[][] edges, Object s) {
        Map<String, List<Object[]>> adjMap = new HashMap<>();
        for (String[] edge : edges) {
            adjMap.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(new Object[]{edge[1], edge[2]});
        }

        Map<String, Integer> dist = new HashMap<>();
        PriorityQueue<Object[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(edge1 -> (Integer) edge1[1]));
        minHeap.offer(new Object[]{s, 0});

        while (!minHeap.isEmpty()) {
            Object[] curr = minHeap.poll();
            if (dist.containsKey(curr[0])) continue;
            dist.put((String) curr[0], (Integer) curr[1]);
            if (adjMap.containsKey(curr[0])) {
                for (Object[] edge : adjMap.get(curr[0])) {
                    minHeap.offer(new Object[]{edge[0], new Integer(edge[1].toString()) + new Integer(curr[1].toString())});
                }
            }
        }

        return dist;
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
        printDistance(1, main.routeDistance(edges, "A-B-C"));
        printDistance(2, main.routeDistance(edges, "A-D"));
        printDistance(3, main.routeDistance(edges, "A-D-C"));
        printDistance(4, main.routeDistance(edges, "A-E-B-C-D"));
        printDistance(5, main.routeDistance(edges, "A-E-D"));


        Map<String, Integer> r = main.dijkstraShortestRoute(edges, "B");
        System.out.println(r.get("E"));
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
