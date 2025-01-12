package wifiService.domain.history;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class History {
    private Integer id; // 히스토리 id
    private double LAT; // 위도
    private double LNT; // 경도
    private Timestamp searchedAt; // 검색일시
}
