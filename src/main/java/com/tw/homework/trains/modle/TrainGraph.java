package com.tw.homework.trains.modle;

import javax.xml.soap.Node;
import java.util.List;
import java.util.Map;

/**
 * 描述铁路的有向图对象
 *
 * @author changwentao
 */
public class TrainGraph {
    private int V;  // 节点数
    private int E;  // 边的数目
    private List<CityNode>[] adj; // 邻接表矩阵
    private Map<String, CityNode> nodes;//图中所有的节点
    private Map<String, Integer> vectors;//图中所有的向量


    public void addVector(String vector) {

    }

}
