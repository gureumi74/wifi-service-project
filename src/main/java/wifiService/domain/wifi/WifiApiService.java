package wifiService.domain.wifi;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import wifiService.domain.location.LocationService;
import wifiService.global.DataSourceConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiApiService {
    private final WifiApiRepository wifiApiRepository = new WifiApiRepository();

    // 응답 정보를 하나씩 받아오기
    public JsonArray fetchWifiData(String apiUrl) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().
                url(apiUrl).
                build();

        try (Response response = client.newCall(request).execute()) {
            // 응답이 성공적일 때
            if (response.isSuccessful() && response.body() != null) {
                String jsonData = response.body().string();
                // 파싱
                JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
                JsonObject wifiInfo = jsonObject.getAsJsonObject("TbPublicWifiInfo");
                return wifiInfo.getAsJsonArray("row");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 파싱한 정보 Wifi 에 담기
    public Wifi parseWifi(JsonObject wifiObject) {
        Wifi wifi = new Wifi();
        wifi.setMgrNo(wifiObject.get("X_SWIFI_MGR_NO").getAsString());
        wifi.setWrdofc(wifiObject.get("X_SWIFI_WRDOFC").getAsString());
        wifi.setWifiName(wifiObject.get("X_SWIFI_MAIN_NM").getAsString());
        wifi.setAddress1(wifiObject.get("X_SWIFI_ADRES1").getAsString());
        wifi.setAddress2(wifiObject.get("X_SWIFI_ADRES2").getAsString());
        wifi.setInstlFloor(wifiObject.get("X_SWIFI_INSTL_FLOOR").getAsString());
        wifi.setInstlTy(wifiObject.get("X_SWIFI_INSTL_TY").getAsString());
        wifi.setInstlMby(wifiObject.get("X_SWIFI_INSTL_MBY").getAsString());
        wifi.setSvcSe(wifiObject.get("X_SWIFI_SVC_SE").getAsString());
        wifi.setCmcwr(wifiObject.get("X_SWIFI_CMCWR").getAsString());
        wifi.setCnstcYear(wifiObject.get("X_SWIFI_CNSTC_YEAR").getAsInt());
        wifi.setInoutDoor(wifiObject.get("X_SWIFI_INOUT_DOOR").getAsString());
        wifi.setRemars(wifiObject.get("X_SWIFI_REMARS3").getAsString());
        wifi.setWifiLAT(wifiObject.get("LAT").getAsDouble());
        wifi.setWifiLNT(wifiObject.get("LNT").getAsDouble());
        wifi.setWorkDttm(wifiObject.get("WORK_DTTM").getAsString());
        wifi.setSavedAt(new Timestamp(System.currentTimeMillis()));
        return wifi;
    }

    // API 호출 및 데이터 저장
    public void loadAndSaveWifiData() {
        int startPage = 1;
        int endPage = 1000;
        String apiKey = System.getenv("API_KEY");
        String apiUrlBase = "http://openapi.seoul.go.kr:8088/" + apiKey + "/json/TbPublicWifiInfo/%d/%d";

        // 새로 저장하기 위한 테이블 초기화
        wifiApiRepository.dropAndCreateTable();

        while (true) {
            String apiUrl = String.format(apiUrlBase, startPage, endPage);

            JsonArray wifiDataArray = fetchWifiData(apiUrl);
            // 저장할 객체가 더 이상 없으면 break;
            if (wifiDataArray == null || wifiDataArray.isEmpty()) {
                break;
            }

            // 1부터 1000까지의 JSON 객체를 하나씩 돌면서 DB에 저장
            wifiDataArray.forEach(x -> {
                Wifi wifi = parseWifi(x.getAsJsonObject());
                wifiApiRepository.save(wifi);
            });

            startPage += 1000;
            endPage += 1000;
        }

        System.out.println("API 저장 완료");
    }
}
