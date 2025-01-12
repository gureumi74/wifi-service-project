package wifiService.domain.wifi;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class Wifi {
    private Integer id; // wifi ID
    private String mgrNo; // 관리번호
    private String wrdofc; // 자치구
    private String wifiName; // 와이파이명
    private String address1; // 도로명 주소
    private String address2; // 상세 주소
    private String instlFloor; // 설치 위치 (층)
    private String instlTy; // 설치 유형
    private String instlMby; // 설치 기관
    private String svcSe; // 서비스 구분
    private String cmcwr; // 망종류
    private Integer cnstcYear; // 설치년도
    private String inoutDoor; // 실내외 구분
    private String remars; // wifi 접속 환경
    private double wifiLAT; // y 좌표
    private double wifiLNT; // x 좌표
    private String workDttm; // 작업일자
    private Timestamp savedAt; // 저장 일시
}
