package wifiService.domain.wifi;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class WifiInfo {
    private Integer id; // Wifi_Info id
    private Integer historyId; // 히스토리 (위치) 키
    private Integer wifiId; // 와이파이 키
    private String distance; // 거리
    private Timestamp searchedAt; // 검색일시
}
