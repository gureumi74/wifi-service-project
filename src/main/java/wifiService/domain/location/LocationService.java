package wifiService.domain.location;

import wifiService.domain.history.History;
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
    public List<History> searchLocation(double lat, double lnt) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        String url = dataSourceConfig.sqliteDriveLoad();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<History> distanceList = new ArrayList<>();

        // 커넥션 객체 생성
        try {
            connection = DriverManager.getConnection(url);

            // sql 쿼리
            String insertSql = "insert into locations (lat, lnt, saved_at) " +
                "values (?, ?, ?)";

            preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setDouble(1, lat);
            preparedStatement.setDouble(2, lnt);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                System.out.println(lat + ", " + lnt + " 위치 정보 저장 완료");
            } else {
                System.out.println("저장 실패");
            }

            // 저장된 위치의 Location_ID 가져오기
            String getLastIdSql = "select last_insert_rowid() as LOCATION_ID";
            preparedStatement = connection.prepareStatement(getLastIdSql);
            rs = preparedStatement.executeQuery();
            int locationId = 0;
            if (rs.next()) {
                locationId = rs.getInt("LOCATION_ID");
                System.out.println("저장된 위치 ID : " + locationId);
            } else {
                System.out.println("위치 ID 조회 실패");
                return distanceList;
            }

            // sql 쿼리 (가장 가까운 wifi 20개 정보를 뽑는 쿼리, 두 좌표의 거리 구하기)
            String distanceSql = "with temp_coords as (select ?  as mylat, ? as mylnt) " +
                    "select *, (6371 * acos(cos(radians(tc.mylat)) * " +
                    "cos(radians(wad.lat)) * cos(radians(wad.lnt) - radians(tc.mylnt)) + " +
                    "sin(radians(tc.mylat)) * sin(radians(wad.lat)))) as distance " +
                    "from wifi_api_data wad " +
                    "join temp_coords tc " +
                    "order by distance asc " +
                    "limit 20;";

            preparedStatement = connection.prepareStatement(distanceSql);
            preparedStatement.setDouble(1, lat);
            preparedStatement.setDouble(2, lnt);

            // 쿼리 실행
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Integer wifiId = rs.getInt("WIFI_ID");
                double distance = rs.getDouble("DISTANCE");

                History history = new History();
                history.setLocationId(locationId);
                history.setWifiId(wifiId);
                history.setDistance(String.format("%.4f", distance));

                // 가까운 20개의 정보를 list 에 저장 후 반환
                distanceList.add(history);
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

            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return distanceList;
    }
}
