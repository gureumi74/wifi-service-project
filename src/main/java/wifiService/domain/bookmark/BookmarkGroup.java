package wifiService.domain.bookmark;

import java.sql.Timestamp;

public class BookmarkGroup {
    private Integer groupId; // 북마크 그룹 아이디
    private String name; // 북마크 이름
    private Integer bookmarkNo; // 순서
    private Timestamp createdAt; // 생성 날짜
    private Timestamp updatedAt; // 업데이트 날짜

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBookmarkNo() {
        return bookmarkNo;
    }

    public void setBookmarkNo(Integer bookmarkNo) {
        this.bookmarkNo = bookmarkNo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

}
