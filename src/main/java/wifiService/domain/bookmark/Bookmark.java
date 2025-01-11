package wifiService.domain.bookmark;

import wifiService.domain.wifi.Wifi;

import java.sql.Timestamp;

public class Bookmark {
    private Integer bookmarkId;
    private Timestamp createdAt;
    private Integer groupId;
    private String groupName;
    private Integer historyId;
    private String wifiName;

    public Integer getBookmarkId() {
        return bookmarkId;
    }

    public void setBookMarkId(Integer bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createAt) {
        this.createdAt = createAt;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }
}
