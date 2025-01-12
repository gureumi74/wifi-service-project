package wifiService.domain.wifi;

import wifiService.domain.history.History;
import wifiService.domain.history.HistoryRepository;
import wifiService.global.DataSourceConfig;

import java.util.ArrayList;
import java.util.List;

public class WifiInfoService {
    private final WifiInfoRepository wifiInfoRepository = new WifiInfoRepository();
    private final HistoryRepository historyRepository = new HistoryRepository();

    // 가까운 와이파이 20개를 찾고 저장
    public void find20AndSaveWifiInfo(double lat, double lnt, Integer historyId) {
        // 히스토리 정보를 통해 가까운 20개의 wifi 정보를 불러오고 DB에 저장
        List<WifiInfo> list = wifiInfoRepository.find20WifiInfo(lat, lnt);

        for (WifiInfo wifiInfo : list) {
            wifiInfo.setHistoryId(historyId);
            wifiInfoRepository.saveWifiInfo(wifiInfo);
        }
        System.out.println("20개 데이터 저장 완료");
    }

    // 가까운 와이파이 20개 정보 보기
    public List<WifiInfoDto> wifiInfo20(Integer historyId) {
        List<WifiInfo> wifiInfoList = wifiInfoRepository.find20WifiInfoByHistoryId(historyId);
        List<WifiInfoDto> wifiInfoDtoList = new ArrayList<>();
        for (WifiInfo wifiInfo : wifiInfoList) {
            WifiInfoDto wifiInfoDto = new WifiInfoDto();
            wifiInfoDto.setId(wifiInfo.getId());
            wifiInfoDto.setHistory(historyRepository.findHistoryById(wifiInfo.getHistoryId()));
            wifiInfoDto.setWifi(wifiInfoRepository.findWifiById(wifiInfo.getWifiId()));
            wifiInfoDto.setDistance(wifiInfo.getDistance());
            wifiInfoDto.setSearchedAt(wifiInfo.getSearchedAt());
            wifiInfoDtoList.add(wifiInfoDto);
        }
        System.out.println(wifiInfoList.size());

        return wifiInfoDtoList;
    }
}
