package wifiService.domain.history;

import java.sql.Timestamp;

public class History {
    private Integer historyId;
    private Integer locationId;
    private Integer wifiId;
    private String distance;
    private Timestamp searchedAt;

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

    public Timestamp getSearchedAt() {
        return searchedAt;
    }

    public void setSearchedAt(Timestamp searchedAt) {
        this.searchedAt = searchedAt;
    }
}
