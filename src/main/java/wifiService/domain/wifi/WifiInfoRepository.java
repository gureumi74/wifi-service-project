package wifiService.domain.wifi;

import wifiService.global.DataSourceConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiInfoRepository {
    private final String url;

    // 호출시 SQLite 드라이버 로드
    public WifiInfoRepository() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        this.url = dataSourceConfig.sqliteDriveLoad();
    }

    // 특정 위치에 가까운 와이파이 20개의 데이터 조회
    public List<WifiInfo> find20WifiInfo(double lat, double lnt) {
        String sql = "WITH temp_coords AS (SELECT ?  AS mylat, ? AS mylnt) " +
                "SELECT *, (6371 * ACOS(COS(RADIANS(tc.mylat)) * " +
                "COS(RADIANS(wad.lat)) * COS(RADIANS(wad.lnt) - RADIANS(tc.mylnt)) + " +
                "SIN(RADIANS(tc.mylat)) * SIN(RADIANS(wad.lat)))) AS DISTANCE " +
                "FROM WIFI_API wad " +
                "JOIN temp_coords tc " +
                "ORDER BY DISTANCE asc " +
                "LIMIT 20;";
        List<WifiInfo> list = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, lat);
            preparedStatement.setDouble(2, lnt);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Integer wifiId = rs.getInt("WIFI_ID");
                double distance = rs.getDouble("DISTANCE");

                WifiInfo wifiInfo = new WifiInfo();
                wifiInfo.setWifiId(wifiId);
                wifiInfo.setDistance(String.format("%.4f", distance));

                list.add(wifiInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Wifi 정보 저장
    public int saveWifiInfo(WifiInfo wifiInfo) {
        String sql = "INSERT INTO WIFI_INFO (HISTORY_ID, WIFI_ID, DISTANCE, SEARCHED_AT) " +
                "values (?, ?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, wifiInfo.getHistoryId());
            preparedStatement.setInt(2, wifiInfo.getWifiId());
            preparedStatement.setString(3, wifiInfo.getDistance());
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // 자동 생성된 키 반환
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // 20개의 정보 보기
    public List<WifiInfo> find20WifiInfoByHistoryId(Integer historyId) {
        String sql = "SELECT * FROM WIFI_INFO WHERE HISTORY_ID = ?";
        List<WifiInfo> list = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, historyId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                WifiInfo wifiInfo = new WifiInfo();
                wifiInfo.setId(rs.getInt("WIFI_INFO_ID"));
                wifiInfo.setWifiId(rs.getInt("WIFI_ID"));
                wifiInfo.setHistoryId(rs.getInt("HISTORY_ID"));
                wifiInfo.setDistance(rs.getString("DISTANCE"));
                wifiInfo.setSearchedAt(rs.getTimestamp("SEARCHED_AT"));
                list.add(wifiInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Wifi Id 를 가지고 Wifi 정보 가져오기
    public Wifi findWifiById(Integer id) {
        String sql = "SELECT * FROM WIFI_API WHERE WIFI_ID = ?";
        Wifi wifi = null;

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    wifi = new Wifi();
                    wifi.setId(id);
                    wifi.setMgrNo(rs.getString("X_SWIFI_MGR_NO"));
                    wifi.setWrdofc(rs.getString("X_SWIFI_WRDOFC"));
                    wifi.setWifiName(rs.getString("X_SWIFI_MAIN_NM"));
                    wifi.setAddress1(rs.getString("X_SWIFI_ADRES1"));
                    wifi.setAddress2(rs.getString("X_SWIFI_ADRES2"));
                    wifi.setInstlFloor(rs.getString("X_SWIFI_INSTL_FLOOR"));
                    wifi.setInstlTy(rs.getString("X_SWIFI_INSTL_TY"));
                    wifi.setInstlMby(rs.getString("X_SWIFI_INSTL_MBY"));
                    wifi.setSvcSe(rs.getString("X_SWIFI_SVC_SE"));
                    wifi.setCmcwr(rs.getString("X_SWIFI_CMCWR"));
                    wifi.setCnstcYear(rs.getInt("X_SWIFI_CNSTC_YEAR"));
                    wifi.setInoutDoor(rs.getString("X_SWIFI_INOUT_DOOR"));
                    wifi.setRemars(rs.getString("X_SWIFI_REMARS3"));
                    wifi.setWifiLAT(rs.getDouble("LAT"));
                    wifi.setWifiLNT(rs.getDouble("LNT"));
                    wifi.setWorkDttm(rs.getString("WORK_DTTM"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wifi;
    }

    // Wifi-Info 보기
    public WifiInfo findWifiInfoById(Integer id) {
        String sql = "SELECT * FROM WIFI_INFO WHERE WIFI_INFO_ID = ?";
        WifiInfo wifiInfo = null;

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    wifiInfo = new WifiInfo();
                    wifiInfo.setId(id);
                    wifiInfo.setHistoryId(rs.getInt("HISTORY_ID"));
                    wifiInfo.setWifiId(rs.getInt("WIFI_ID"));
                    wifiInfo.setDistance(rs.getString("DISTANCE"));
                    wifiInfo.setSearchedAt(rs.getTimestamp("SEARCHED_AT"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wifiInfo;
    }
}
