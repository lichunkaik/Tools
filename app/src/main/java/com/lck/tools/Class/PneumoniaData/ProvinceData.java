package com.lck.tools.Class.PneumoniaData;

import java.util.ArrayList;
import java.util.Date;

/**
 * created by lucky on 2020/2/13
 * 省级疫情数据
 */
public class ProvinceData {
    private String provinceName;//省
    private String provinceShortName;//简称
    private int currentConfirmedCount;//当前确诊人数
    private int confirmedCount;//确诊人数（确诊人数以此为准）
    private int suspectedCount;//疑似病例
    private int curedCount;//治愈病例
    private int deadCount;//死亡病例
    private String comment;//评论
    private String locationId;//行政区划代码
    private ArrayList<CityData> cities;//城市数据
    private Boolean isProvince = true;//省级

    public Boolean getProvince() {
        return isProvince;
    }

    public void setProvince(Boolean province) {
        isProvince = province;
    }

    public ArrayList<CityData> getCities() {
        return cities;
    }

    public void setCities(ArrayList<CityData> cities) {
        this.cities = cities;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceShortName() {
        return provinceShortName;
    }

    public void setProvinceShortName(String provinceShortName) {
        this.provinceShortName = provinceShortName;
    }

    public int getCurrentConfirmedCount() {
        return currentConfirmedCount;
    }

    public void setCurrentConfirmedCount(int currentConfirmedCount) {
        this.currentConfirmedCount = currentConfirmedCount;
    }

    public int getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(int confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public int getSuspectedCount() {
        return suspectedCount;
    }

    public void setSuspectedCount(int suspectedCount) {
        this.suspectedCount = suspectedCount;
    }

    public int getCuredCount() {
        return curedCount;
    }

    public void setCuredCount(int curedCount) {
        this.curedCount = curedCount;
    }

    public int getDeadCount() {
        return deadCount;
    }

    public void setDeadCount(int deadCount) {
        this.deadCount = deadCount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
