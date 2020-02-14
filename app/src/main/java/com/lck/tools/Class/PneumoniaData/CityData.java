package com.lck.tools.Class.PneumoniaData;

/**
 * created by lucky on 2020/2/13
 * 市级疫情数据
 */
public class CityData {
    private String cityName;//市
    private int currentConfirmedCount;//当前确诊人数
    private int confirmedCount;//确诊人数（确诊人数以此为准）
    private int suspectedCount;//疑似病例
    private int curedCount;//治愈病例
    private int deadCount;//死亡病例
    private String locationId;//行政区划代码
    private Boolean isProvince = false;//省级

    public Boolean getProvince() {
        return isProvince;
    }

    public void setProvince(Boolean province) {
        isProvince = province;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
