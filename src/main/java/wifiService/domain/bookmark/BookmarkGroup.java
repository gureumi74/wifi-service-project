package wifiService.domain.bookmark;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BookmarkGroup {
    private Integer id; // 북마크 그룹 아이디
    private String name; // 북마크 그룹 이름
    private Integer no; // 순서
    private Timestamp createdAt; // 생성일시
    private Timestamp updatedAt; // 수정일시
}
