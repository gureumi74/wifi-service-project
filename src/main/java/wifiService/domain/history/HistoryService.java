package wifiService.domain.history;

import wifiService.domain.location.Location;
import wifiService.domain.location.LocationService;
import wifiService.domain.wifi.WifiApiService;
import wifiService.global.DataSourceConfig;

import java.sql.*;
import java.util.ArrayList;
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

    public List<History> viewHistory() {
        List<History> historyList = new ArrayList<>();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        String url = dataSourceConfig.sqliteDriveLoad();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        // 커넥션 객체 생성
        try {
            connection = DriverManager.getConnection(url);

            // sql 쿼리
            String sql = "select * from history";

            preparedStatement = connection.prepareStatement(sql);

            // 쿼리 실행
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                History history = new History();
                history.setHistoryId(rs.getInt("HISTORY_ID"));
                history.setLocationId(rs.getInt("LOCATION_ID"));
                history.setDistance(rs.getString("DISTANCE"));
                history.setSearchedAt(rs.getTimestamp("SEARCHED_AT"));
                history.setWifiId(rs.getInt("WIFI_ID"));

                WifiApiService wifiApiService = new WifiApiService();
                history.setWifi(wifiApiService.getWifiInfo(history.getWifiId()));

                // list에 추가
                historyList.add(history);
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

        return historyList;
    }

    public History getHistoryById(Integer historyId) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        String url = dataSourceConfig.sqliteDriveLoad();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        History history = new History();

        // 커넥션 객체 생성
        try {
            connection = DriverManager.getConnection(url);

            // sql 쿼리
            String sql = "select * from history where HISTORY_ID = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, historyId);

            // 쿼리 실행
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                history.setHistoryId(rs.getInt("HISTORY_ID"));
                history.setLocationId(rs.getInt("LOCATION_ID"));
                history.setDistance(rs.getString("DISTANCE"));
                history.setSearchedAt(rs.getTimestamp("SEARCHED_AT"));
                history.setWifiId(rs.getInt("WIFI_ID"));
            } else {
                System.out.println("히스토리 조회 실패");
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
        return history;
    }
}
