package com.lck.tools.Class.PneumoniaData;

import java.util.ArrayList;
import java.util.Map;

/**
 * created by lucky on 2020/2/13
 */
public class PneumoniaData {
    private String date;//更新日期
    private ArrayList<ProvinceData> provinceDataArrayList;//疫情数据
    private Map<String,Integer> provinceMap;//省份对应疫情数据index(为了查找具体省份的数据)
    private int sum_confirmedCount;//确诊人数
    private int sum_suspectedCount;//疑似人数
    private int sum_curedCount;//治愈人数
    private int sum_deadCount;//死亡人数

    public int getSum_confirmedCount() {
        return sum_confirmedCount;
    }

    public void setSum_confirmedCount(int sum_confirmedCount) {
        this.sum_confirmedCount = sum_confirmedCount;
    }

    public int getSum_suspectedCount() {
        return sum_suspectedCount;
    }

    public void setSum_suspectedCount(int sum_suspectedCount) {
        this.sum_suspectedCount = sum_suspectedCount;
    }

    public int getSum_curedCount() {
        return sum_curedCount;
    }

    public void setSum_curedCount(int sum_curedCount) {
        this.sum_curedCount = sum_curedCount;
    }

    public int getSum_deadCount() {
        return sum_deadCount;
    }

    public void setSum_deadCount(int sum_deadCount) {
        this.sum_deadCount = sum_deadCount;
    }

    public Map<String, Integer> getProvinceMap() {
        return provinceMap;
    }

    public void setProvinceMap(Map<String, Integer> provinceMap) {
        this.provinceMap = provinceMap;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ProvinceData> getProvinceDataArrayList() {
        return provinceDataArrayList;
    }

    public void setProvinceDataArrayList(ArrayList<ProvinceData> provinceDataArrayList) {
        this.provinceDataArrayList = provinceDataArrayList;
    }
}
