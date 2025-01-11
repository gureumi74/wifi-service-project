package wifiService.domain.bookmark;

import wifiService.domain.history.History;
import wifiService.domain.wifi.Wifi;
import wifiService.global.DataSourceConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookmarkService {
    public List<BookmarkGroup> viewBookmarkGroup() {
        List<BookmarkGroup> bookmarkGroupsList = new ArrayList<>();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        String url = dataSourceConfig.sqliteDriveLoad();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        // 커넥션 객체 생성
        try {
            connection = DriverManager.getConnection(url);

            // sql 쿼리
            String sql = "select * from bookmark_group";

            preparedStatement = connection.prepareStatement(sql);

            // 쿼리 실행
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                BookmarkGroup bookmarkGroup = new BookmarkGroup();
                bookmarkGroup.setGroupId(rs.getInt("GROUP_ID"));
                bookmarkGroup.setName(rs.getString("NAME"));
                bookmarkGroup.setBookmarkNo(rs.getInt("BOOKMARK_NO"));
                bookmarkGroup.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                bookmarkGroup.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));

                // list에 추가
                bookmarkGroupsList.add(bookmarkGroup);
            }

            // 순서대로 정렬 후 반환
            Collections.sort(bookmarkGroupsList, (x, y) -> x.getBookmarkNo() - y.getBookmarkNo());
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

        return bookmarkGroupsList;
    }

    public void createBookmark(String name, Integer bookmarkNo) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        String url = dataSourceConfig.sqliteDriveLoad();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        // 커넥션 객체 생성
        try {
            connection = DriverManager.getConnection(url);

            // sql 쿼리
            String insertSql = "insert into BOOKMARK_GROUP (name, bookmark_no, created_at) " +
                    "values (?, ?, ?)";

            preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, bookmarkNo);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                System.out.println(name + " 북마크 그룹 생성 완료");
            } else {
                System.out.println("북마크 생성 실패");
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

    public void insertBookmarkHistory(Integer bookmarkId, Integer historyId) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        String url = dataSourceConfig.sqliteDriveLoad();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        // 커넥션 객체 생성
        try {
            connection = DriverManager.getConnection(url);

            // sql 쿼리
            String insertSql = "insert into BOOKMARK (CREATED_AT, GROUP_ID, HISTORY_ID) " +
                    "values (?, ?, ?)";

            preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(2, bookmarkId);
            preparedStatement.setInt(3, historyId);

            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                System.out.println("북마크 추가 완료");
            } else {
                System.out.println("북마크 추가 실패");
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

    // 북마크 id 를 가지고 북마크 정보 가져오기
    public BookmarkGroup getBookmarkById(Integer bookmarkGroupId) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        String url = dataSourceConfig.sqliteDriveLoad();

        BookmarkGroup bookmarkGroup = new BookmarkGroup();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        // 커넥션 객체 생성
        try {
            connection = DriverManager.getConnection(url);

            // sql 쿼리
            String sql = "select * from BOOKMARK_GROUP where GROUP_ID = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookmarkGroupId);

            // 쿼리 실행
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                bookmarkGroup.setGroupId(rs.getInt("GROUP_ID"));
                bookmarkGroup.setName(rs.getString("NAME"));
                bookmarkGroup.setBookmarkNo(rs.getInt("BOOKMARK_NO"));
                bookmarkGroup.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                bookmarkGroup.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));
            } else {
                System.out.println("북마크 그룹 정보 불러오기 성공");
                return null;
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

        return bookmarkGroup;
    }
}
