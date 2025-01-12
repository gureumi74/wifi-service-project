package wifiService.domain.wifi;

import wifiService.global.DataSourceConfig;

import java.sql.*;

public class WifiApiRepository {
    private final String url;

    // 호출시 SQLite 드라이버 로드
    public WifiApiRepository() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        this.url = dataSourceConfig.sqliteDriveLoad();
    }

    // Wifi 테이블 초기화
    public void dropAndCreateTable() {
        String dropSql = "DROP TABLE IF EXISTS WIFI_API";
        String createSql = "CREATE TABLE WIFI_API (WIFI_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "X_SWIFI_MGR_NO TEXT, X_SWIFI_WRDOFC TEXT, X_SWIFI_MAIN_NM TEXT, " +
                "X_SWIFI_ADRES1 TEXT, X_SWIFI_ADRES2 TEXT, X_SWIFI_INSTL_FLOOR TEXT, " +
                "X_SWIFI_INSTL_TY TEXT, X_SWIFI_INSTL_MBY TEXT, X_SWIFI_SVC_SE TEXT, " +
                "X_SWIFI_CMCWR TEXT, X_SWIFI_CNSTC_YEAR INTEGER, X_SWIFI_INOUT_DOOR TEXT, " +
                "X_SWIFI_REMARS3 TEXT, LAT DOUBLE, LNT DOUBLE, WORK_DTTM TEXT);";



        try (Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement()) {
            // 테이블 삭제 수행
            statement.executeUpdate(dropSql);
            // 테이블 생성 수행
            statement.executeUpdate(createSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Wifi API 정보 저장
    public void save(Wifi wifi) {
        String sql = "INSERT INTO WIFI_API (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, " +
                "X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, " +
                "X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, wifi.getMgrNo());
            preparedStatement.setString(2, wifi.getWrdofc());
            preparedStatement.setString(3, wifi.getWifiName());
            preparedStatement.setString(4, wifi.getAddress1());
            preparedStatement.setString(5, wifi.getAddress2());
            preparedStatement.setString(6, wifi.getInstlFloor());
            preparedStatement.setString(7, wifi.getInstlTy());
            preparedStatement.setString(8, wifi.getInstlMby());
            preparedStatement.setString(9, wifi.getSvcSe());
            preparedStatement.setString(10, wifi.getCmcwr());
            preparedStatement.setInt(11, wifi.getCnstcYear());
            preparedStatement.setString(12, wifi.getInoutDoor());
            preparedStatement.setString(13, wifi.getRemars());
            preparedStatement.setDouble(14, wifi.getWifiLAT());
            preparedStatement.setDouble(15, wifi.getWifiLNT());
            preparedStatement.setString(16, wifi.getWorkDttm());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 특정 wifi 정보 가져오기
    public Wifi findWifiById(Integer id) {
        // sql 쿼리
        String sql = "SELECT * FROM WIFI_API WHERE WIFI_ID = ?";
        Wifi wifi = null;

        // 커넥션 객체 생성
        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement  = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                ResultSet rs = preparedStatement.executeQuery();

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wifi;
    }

    // 총 데이터 개수 조회 (화면에 출력하기 위한 메서드)
    public int getWifiCnt() {
        String sql = "SELECT COUNT(*) AS count FROM WIFI_API";

        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 데이터 개수를 가져오지 못했을 경우 0 반환
        return 0;
    }
}
