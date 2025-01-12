package wifiService.domain.bookmark;

import lombok.Data;
import wifiService.domain.wifi.WifiInfoDetail;

import java.sql.Timestamp;

@Data
public class BookmarkDetail {
    private Integer id; // 북마크 id
    private BookmarkGroup bookmarkGroup; // 북마크 그룹
    private WifiInfoDetail wifiInfoDetail; // wifi Info 상세 정보
    private Timestamp createdAt; // 생성일시
}
