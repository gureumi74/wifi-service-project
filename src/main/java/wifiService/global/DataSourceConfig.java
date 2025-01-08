package wifiService.global;

public class DataSourceConfig {
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
}
