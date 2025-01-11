package wifiService.domain.bookmark;

import java.sql.Timestamp;

public class Bookmark {
    private Integer bookMarkId;
    private Timestamp createAt;
    private Integer groupId;
    private String groupName;
    private Integer historyId;

    public Integer getBookMarkId() {
        return bookMarkId;
    }

    public void setBookMarkId(Integer bookMarkId) {
        this.bookMarkId = bookMarkId;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
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
}
