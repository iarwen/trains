package com.tw.homework.trains.modle;

import java.util.List;

/**
 * 描述每个城市节点
 *
 * @author changwentao
 */
public class CityNode {
    /**
     * 城市名称
     */
    private String name;
    /**
     * 可达的下一个城市的列表
     */
    private List<CityNode> nexts;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityNode> getNexts() {
        return nexts;
    }

    public void setNexts(List<CityNode> nexts) {
        this.nexts = nexts;
    }
}
