package wifiService.domain.history;

import wifiService.domain.location.Location;
import wifiService.global.DataSourceConfig;

import java.sql.*;

public class HistoryService {
    public void insertHistory(Location location) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        String url = dataSourceConfig.sqliteDriveLoad();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(url);

            // sql 쿼리 (가장 가까운 wifi 20개 정보를 뽑는 쿼리, 두 좌표의 거리 구하기)
            String sql = "with temp_coords as (select " + location.getLAT() + " as mylat, " + location.getLNT() + " as mylnt) " +
                    "select *, (6371 * acos(cos(radians(tc.mylat)) * " +
                    "cos(radians(wad.lat)) * cos(radians(wad.lnt) - radians(tc.mylnt)) + " +
                    "sin(radians(tc.mylat)) * sin(radians(wad.lat)))) as distance " +
                    "from wifi_api_data wad " +
                    "join temp_coords tc " +
                    "order by distance asc " +
                    "limit 20;";

            preparedStatement = connection.prepareStatement(sql);

            // 쿼리 실행
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Integer wifiId = rs.getInt("WIFI_ID");
                double distance = rs.getDouble("DISTANCE");

                String insertSql = "insert into HISTORY (LOCATION_ID, WIFI_ID, DISTANCE, SEARCHED_AT) " +
                        "values (?, ?, ?, ?);";

                preparedStatement = connection.prepareStatement(insertSql);
                preparedStatement.setInt(1, location.getId());
                preparedStatement.setInt(2, wifiId);
                preparedStatement.setString(3, String.format("%.4f", distance));
                preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

                preparedStatement.executeUpdate();
            }

            System.out.println("20개 데이터 저장 완료");
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
    }
}
