package com.lck.tools.Class.PneumoniaData;

import java.util.ArrayList;
import java.util.Map;

/**
 * created by lucky on 2020/2/13
 */
public class PneumoniaData {
    private String date;//更新日期
    private ArrayList<ProvinceData> provinceDataArrayList;//疫情数据
    private Map<String,Integer> provinceMap;//省份对应疫情数据index

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
