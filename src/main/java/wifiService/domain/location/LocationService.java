package wifiService.domain.location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationService {
    // sqlite JDBC 드라이버 로드
    public String sqliteDriveLoad() {
        String path = System.getenv("SQLITE_PATH");
        String url = "jdbc:sqlite:" + path;

        try {
            // SQLite JDBC 드라이버 로드
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return url;
    }

    // 위치 정보 전체 보기 기능
    public List<Location> viewList() {
        List<Location> locationList = new ArrayList<>();
        String url = sqliteDriveLoad();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        // 커넥션 객체 생성
        try {
            connection = DriverManager.getConnection(url);

            // sql 쿼리
            String sql = "select * from locations";

            preparedStatement = connection.prepareStatement(sql);

            // 쿼리 실행
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Integer locationId = rs.getInt("location_id");
                double LAT = rs.getDouble("LAT");
                double LNT = rs.getDouble("LNT");

                Location location = new Location();
                location.setId(locationId);
                location.setLAT(LAT);
                location.setLNT(LNT);

                // list에 추가
                locationList.add(location);
                System.out.println(locationId + "번 위치 정보: " + LAT + " " + LNT + " ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return locationList;
    }
}
