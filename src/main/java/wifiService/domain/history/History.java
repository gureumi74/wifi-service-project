package wifiService.domain.history;

import wifiService.domain.wifi.Wifi;

import java.sql.Timestamp;

public class History {
    private Integer historyId;
    private Integer locationId;
    private Integer wifiId;
    private Wifi wifi;
    private String distance;
    private Timestamp searchedAt;
    private Integer bookmarkId; // 북마크 ID

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getWifiId() {
        return wifiId;
    }

    public void setWifiId(Integer wifiId) {
        this.wifiId = wifiId;
    }

    public Wifi getWifi() {
        return wifi;
    }

    public void setWifi(Wifi wifi) {
        this.wifi = wifi;
    }

    public Timestamp getSearchedAt() {
        return searchedAt;
    }

    public void setSearchedAt(Timestamp searchedAt) {
        this.searchedAt = searchedAt;
    }

    public Integer getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(Integer bookmarkId) {
        this.bookmarkId = bookmarkId;
    }
}
