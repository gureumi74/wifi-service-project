package wifiService.domain.history;

import wifiService.global.DataSourceConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {
    private final String url;

    // 호출시 SQLite 드라이버 로드
    public HistoryRepository() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        this.url = dataSourceConfig.sqliteDriveLoad();
    }

    // History 에 위치 저장하고 저장한 키값 반환
    public Integer saveHistory(History history) {
        String sql = "insert into HISTORY (LAT, LNT, SEARCHED_AT) " +
                "values (?, ?, ?);";

        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDouble(1, history.getLAT());
            preparedStatement.setDouble(2, history.getLNT());
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        // 자동 생성된 키 반환하기
                        return generatedKeys.getInt(1);
                    }
                }
            }

            System.out.println("20개 데이터 저장 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 실패시 -1 반환
        return -1;
    }

    // History 전체 조회
    public List<History> findAllHistory() {
        String sql = "SELECT * FROM HISTORY";
        List<History> list = new ArrayList<>();

        // 커넥션 객체 생성
        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                History history = new History();
                history.setId(rs.getInt("HISTORY_ID"));
                history.setLAT(rs.getDouble("LAT"));
                history.setLNT(rs.getDouble("LNT"));
                history.setSearchedAt(rs.getTimestamp("SEARCHED_AT"));
                // list에 추가
                list.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // History Id로 조회
    public History findHistoryById(Integer id) {
        String sql = "SELECT * FROM HISTORY WHERE HISTORY_ID = ?";
        History history = new History();

        // 커넥션 객체 생성
        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                history.setId(id);
                history.setLAT(rs.getDouble("LAT"));
                history.setLNT(rs.getDouble("LNT"));
                history.setSearchedAt(rs.getTimestamp("SEARCHED_AT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }

    // History 삭제
    public void deleteHistory(Integer id) {
        String sql = "DELETE FROM HISTORY WHERE HISTORY_ID = ?";
        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // 외래 키 제약 활성화
            connection.createStatement().execute("PRAGMA FOREIGN_KEYS = ON;");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
