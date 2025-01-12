package wifiService.domain.bookmark;

import wifiService.global.DataSourceConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookmarkRepository {
    private final String url;

    // 호출시 SQLite 드라이버 로드
    public BookmarkRepository() {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        this.url = dataSourceConfig.sqliteDriveLoad();
    }

    public List<BookmarkGroup> findAllBookmarkGroup() {
        String sql = "SELECT * FROM BOOKMARK_GROUP";
        List<BookmarkGroup> bookmarkGroupsList = new ArrayList<>();

        // 커넥션 객체 생성
        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                BookmarkGroup bookmarkGroup = new BookmarkGroup();
                bookmarkGroup.setId(rs.getInt("GROUP_ID"));
                bookmarkGroup.setName(rs.getString("NAME"));
                bookmarkGroup.setNo(rs.getInt("BOOKMARK_NO"));
                bookmarkGroup.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                bookmarkGroup.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));

                // list에 추가
                bookmarkGroupsList.add(bookmarkGroup);
            }

            // 순서대로 정렬 후 반환
            Collections.sort(bookmarkGroupsList, (x, y) -> x.getNo() - y.getNo());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmarkGroupsList;
    }

    // 북마크 그룹 생성
    public void createBookmarkGroup(String name, Integer bookmarkNo) {
        String sql = "INSERT INTO BOOKMARK_GROUP (name, bookmark_no, created_at) " +
                "values (?, ?, ?)";

        // 커넥션 객체 생성
        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, bookmarkNo);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 북마크 그룹 조회 (1개)
    public BookmarkGroup findBookmarkGroupById(Integer id) {
        String sql = "SELECT * FROM BOOKMARK_GROUP WHERE GROUP_ID = ?";
        BookmarkGroup bookmarkGroup = new BookmarkGroup();

        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    bookmarkGroup.setId(rs.getInt("GROUP_ID"));
                    bookmarkGroup.setName(rs.getString("NAME"));
                    bookmarkGroup.setNo(rs.getInt("BOOKMARK_NO"));
                    bookmarkGroup.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                    bookmarkGroup.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmarkGroup;
    }

    // 북마크 그룹 수정
    public void updateBookmarkGroup(Integer id, String newGroupName, Integer newGroupNo) {
        String sql = "UPDATE BOOKMARK_GROUP SET NAME = ?, BOOKMARK_NO = ?, UPDATED_AT = ? WHERE GROUP_ID = ?";

        // 커넥션 객체 생성
        try (Connection connection = DriverManager.getConnection(url);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newGroupName);
            preparedStatement.setInt(2, newGroupNo);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 북마크 그룹 삭제
    public void deleteBookmarkGroup(Integer id) {
        String sql = "DELETE FROM BOOKMARK_GROUP WHERE GROUP_ID = ?";
        // 커넥션 객체 생성
        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            // 외래 키 제약 활성화
            connection.createStatement().execute("pragma foreign_keys = on;");
            preparedStatement.setInt(1, id);

            // 쿼리 실행 (executeUpdate는 결과 반환 x)
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Wifi 북마크 추가
    public void createBookmark(Integer bookmarkId, Integer wifiInfoId) {
        String sql = "INSERT INTO BOOKMARK (GROUP_ID, WIFI_INFO_ID, CREATED_AT) " +
                "VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, bookmarkId);
            preparedStatement.setInt(2, wifiInfoId);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Wifi 북마크 조회
    public List<Bookmark> findAllBookmark() {
        String sql = "SELECT * FROM BOOKMARK";
        List<Bookmark> bookmarkList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                Bookmark bookmark = new Bookmark();
                bookmark.setId(rs.getInt("BOOKMARK_ID"));
                bookmark.setGroupId(rs.getInt("GROUP_ID"));
                bookmark.setWifiInfoId(rs.getInt("WIFI_INFO_ID"));
                bookmark.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                bookmarkList.add(bookmark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmarkList;
    }

    // Wifi 북마크 삭제
    public void deleteBookmark(Integer id) {
        String sql = "DELETE FROM BOOKMARK WHERE BOOKMARK_ID = ?";

        // 커넥션 객체 생성
        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            // 외래 키 제약 활성화
            connection.createStatement().execute("pragma foreign_keys = on;");
            preparedStatement.setInt(1, id);

            // 쿼리 실행 (executeUpdate는 결과 반환 x)
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
