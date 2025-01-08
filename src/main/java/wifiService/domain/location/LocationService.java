package wifiService.domain.location;

import wifiService.global.DataSourceConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationService {
    // 위치 정보 전체 보기 기능
    public List<Location> viewList() {
        List<Location> locationList = new ArrayList<>();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        String url = dataSourceConfig.sqliteDriveLoad();

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

    // 위치 정보 저장 기능 (입력받은 좌표값 DB에 저장)
    public void searchLocation(double lat, double lnt) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        String url = dataSourceConfig.sqliteDriveLoad();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        // 커넥션 객체 생성
        try {
            connection = DriverManager.getConnection(url);

            // sql 쿼리
            String sql = "insert into locations (lat, lnt, saved_at) " +
                "values (?, ?, ?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, lat);
            preparedStatement.setDouble(2, lnt);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                System.out.println(lat + ", " + lnt + " 위치 정보 저장 완료");
            } else {
                System.out.println("저장 실패");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
    }
}
