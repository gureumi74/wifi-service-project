package wifiService.domain.wifi;

import java.sql.Timestamp;

public class Wifi {
    private Integer wifiId;
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

    public Integer getWifiId() {
        return wifiId;
    }

    public void setWifiId(Integer wifiId) {
        this.wifiId = wifiId;
    }

    public String getMgrNo() {
        return mgrNo;
    }

    public void setMgrNo(String mgrNo) {
        this.mgrNo = mgrNo;
    }

    public String getWrdofc() {
        return wrdofc;
    }

    public void setWrdofc(String wrdofc) {
        this.wrdofc = wrdofc;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getInstlFloor() {
        return instlFloor;
    }

    public void setInstlFloor(String instlFloor) {
        this.instlFloor = instlFloor;
    }

    public String getInstlTy() {
        return instlTy;
    }

    public void setInstlTy(String instlTy) {
        this.instlTy = instlTy;
    }

    public String getInstlMby() {
        return instlMby;
    }

    public void setInstlMby(String instlMby) {
        this.instlMby = instlMby;
    }

    public String getSvcSe() {
        return svcSe;
    }

    public void setSvcSe(String svcSe) {
        this.svcSe = svcSe;
    }

    public String getCmcwr() {
        return cmcwr;
    }

    public void setCmcwr(String cmcwr) {
        this.cmcwr = cmcwr;
    }

    public Integer getCnstcYear() {
        return cnstcYear;
    }

    public void setCnstcYear(Integer cnstcYear) {
        this.cnstcYear = cnstcYear;
    }

    public String getInoutDoor() {
        return inoutDoor;
    }

    public void setInoutDoor(String inoutDoor) {
        this.inoutDoor = inoutDoor;
    }

    public String getRemars() {
        return remars;
    }

    public void setRemars(String remars) {
        this.remars = remars;
    }

    public double getWifiLAT() {
        return wifiLAT;
    }

    public void setWifiLAT(double wifiLAT) {
        this.wifiLAT = wifiLAT;
    }

    public double getWifiLNT() {
        return wifiLNT;
    }

    public void setWifiLNT(double wifiLNT) {
        this.wifiLNT = wifiLNT;
    }

    public String getWorkDttm() {
        return workDttm;
    }

    public void setWorkDttm(String workDttm) {
        this.workDttm = workDttm;
    }

    public Timestamp getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(Timestamp savedAt) {
        this.savedAt = savedAt;
    }
}
