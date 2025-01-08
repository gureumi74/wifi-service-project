package wifiService.domain.wifi;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import wifiService.domain.location.LocationService;

import java.sql.*;

public class WifiApiService {
    private int startPage;
    private int endPage;
    private String url;

    public int getEndPage() {
        return endPage;
    }

    // API - 전체 wifi 정보 불러오고 DB에 새로 저장하기
    public void wifiApiLoad() {
        LocationService locationService = new LocationService();
        this.url = locationService.sqliteDriveLoad();

        // DB 드롭 및 재생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // 커넥션 생성
            connection = DriverManager.getConnection(url);

            String dropSql = "drop table if exists wifi_api_data";

            preparedStatement = connection.prepareStatement(dropSql);
            preparedStatement.executeUpdate();

            String createSql = "create table wifi_api_data (WIFI_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "X_SWIFI_MGR_NO TEXT, X_SWIFI_WRDOFC TEXT, X_SWIFI_MAIN_NM TEXT, " +
                    "X_SWIFI_ADRES1 TEXT, X_SWIFI_ADRES2 TEXT, X_SWIFI_INSTL_FLOOR TEXT, " +
                    "X_SWIFI_INSTL_TY TEXT, X_SWIFI_INSTL_MBY TEXT, X_SWIFI_SVC_SE TEXT, " +
                    "X_SWIFI_CMCWR TEXT, X_SWIFI_CNSTC_YEAR INTEGER, X_SWIFI_INOUT_DOOR TEXT, " +
                    "X_SWIFI_REMARS3 TEXT, LAT DOUBLE, LNT DOUBLE, WORK_DTTM TEXT, " +
                    "SAVED_AT DATETIME );";

            preparedStatement = connection.prepareStatement(createSql);
            preparedStatement.executeUpdate();
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

        this.startPage = 1;
        this.endPage = 1000;
        saveWifiDB();
    }

    public void saveWifiDB() {
        while (true) {
            // API URL 설정
            String apiKey = System.getenv("API_KEY");
            String apiUrl = "http://openapi.seoul.go.kr:8088/" + apiKey + "/json/TbPublicWifiInfo/" +
                    this.startPage + "/" + this.endPage + "/";
//            System.out.println(apiUrl); // 콘솔 확인용

            // OkHttpClient API 호출
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(apiUrl)
                    .build();

            try (Response response = client.newCall(request).execute()){
                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().string();

                    // 파싱
                    JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
                    JsonObject wifiInfo = jsonObject.getAsJsonObject("TbPublicWifiInfo");
                    JsonArray rows = wifiInfo.getAsJsonArray("row");

                    // 공통 정보
                    int listTotalCount = wifiInfo.get("list_total_count").getAsInt();

                    // 데이터 확인 (콘솔 확인용)
//                    System.out.println("총 데이터 건수 : " + listTotalCount);

                    Connection connection = null;
                    PreparedStatement preparedStatement = null;

                    try {
                        for (int i = 0; i < rows.size(); i++) {
                            // 커넥션 객체 생성
                            connection = DriverManager.getConnection(this.url);

                            // sql 쿼리 (api 정보를 불러올 때마다 DB를 초기화해서 재저장)
                            String sql = "insert into wifi_api_data (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, " +
                                    "X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, " +
                                    "X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM, SAVED_AT) " +
                                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

                            // Json 객체 하나씩 가져오기
                            JsonObject wifiObject = rows.get(i).getAsJsonObject();

                            preparedStatement = connection.prepareStatement(sql);
                            preparedStatement.setString(1, wifiObject.get("X_SWIFI_MGR_NO").getAsString());
                            preparedStatement.setString(2, wifiObject.get("X_SWIFI_WRDOFC").getAsString());
                            preparedStatement.setString(3, wifiObject.get("X_SWIFI_MAIN_NM").getAsString());
                            preparedStatement.setString(4, wifiObject.get("X_SWIFI_ADRES1").getAsString());
                            preparedStatement.setString(5, wifiObject.get("X_SWIFI_ADRES2").getAsString());
                            preparedStatement.setString(6, wifiObject.get("X_SWIFI_INSTL_FLOOR").getAsString());
                            preparedStatement.setString(7, wifiObject.get("X_SWIFI_INSTL_TY").getAsString());
                            preparedStatement.setString(8, wifiObject.get("X_SWIFI_INSTL_MBY").getAsString());
                            preparedStatement.setString(9, wifiObject.get("X_SWIFI_SVC_SE").getAsString());
                            preparedStatement.setString(10, wifiObject.get("X_SWIFI_CMCWR").getAsString());
                            preparedStatement.setString(11, wifiObject.get("X_SWIFI_CNSTC_YEAR").getAsString());
                            preparedStatement.setString(12, wifiObject.get("X_SWIFI_INOUT_DOOR").getAsString());
                            preparedStatement.setString(13, wifiObject.get("X_SWIFI_REMARS3").getAsString());
                            preparedStatement.setDouble(14, Double.parseDouble(wifiObject.get("LAT").getAsString()));
                            preparedStatement.setDouble(15, Double.parseDouble(wifiObject.get("LNT").getAsString()));
                            preparedStatement.setString(16, wifiObject.get("WORK_DTTM").getAsString());
                            preparedStatement.setTimestamp(17, new Timestamp(System.currentTimeMillis())); // 현재 시간 설정 (저장할 때 시간)

                            // 쿼리 실행
                            preparedStatement.executeUpdate();
                        }
                        this.startPage = this.endPage + 1;
                        this.endPage = Math.min(endPage + 1000, listTotalCount);
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
                    if(this.startPage > this.endPage) {
                        System.out.println(this.endPage);
                        break;
                    }
                } else {
                    System.out.println("API 호출 실패 : " + response.code());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("모든 데이터 저장 완료");
    }
}
