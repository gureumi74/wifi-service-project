package wifiService.domain.wifi;

import lombok.Data;
import wifiService.domain.history.History;

import java.sql.Timestamp;

@Data
public class WifiInfoDetail {
    private Integer id; // Wifi_Info id
    private History history; // 히스토리 (위치)
    private Wifi wifi; // 와이파이 정보
    private String distance; // 거리
    private Timestamp searchedAt; // 검색일시
}
