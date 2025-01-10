package wifiService.domain.history;

import wifiService.global.DataSourceConfig;

import java.sql.*;
import java.util.List;

public class HistoryService {
    public void insertHistory(List<History> locationList) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        String url = dataSourceConfig.sqliteDriveLoad();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(url);

            for (int i = 0; i < locationList.size(); i++) {
                Integer locationId = locationList.get(i).getLocationId();
                Integer wifiId = locationList.get(i).getWifiId();
                String distance = locationList.get(i).getDistance();

                String insertSql = "insert into HISTORY (LOCATION_ID, WIFI_ID, DISTANCE, SEARCHED_AT) " +
                        "values (?, ?, ?, ?);";

                preparedStatement = connection.prepareStatement(insertSql);
                preparedStatement.setInt(1, locationId);
                preparedStatement.setInt(2, wifiId);
                preparedStatement.setString(3, distance);
                preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

                preparedStatement.executeUpdate();
            }

            System.out.println("20개 데이터 저장 완료");
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
