package wifiService.domain.wifi;

import wifiService.domain.history.HistoryRepository;

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

    // history Id를 가지고 가까운 와이파이 20개 정보 보기
    public List<WifiInfoDetail> wifiInfo20(Integer historyId) {
        List<WifiInfo> wifiInfoList = wifiInfoRepository.find20WifiInfoByHistoryId(historyId);
        List<WifiInfoDetail> wifiInfoDetailList = new ArrayList<>();
        for (WifiInfo wifiInfo : wifiInfoList) {
            WifiInfoDetail wifiInfoDetail = new WifiInfoDetail();
            wifiInfoDetail.setId(wifiInfo.getId());
            wifiInfoDetail.setHistory(historyRepository.findHistoryById(wifiInfo.getHistoryId()));
            wifiInfoDetail.setWifi(wifiInfoRepository.findWifiById(wifiInfo.getWifiId()));
            wifiInfoDetail.setDistance(wifiInfo.getDistance());
            wifiInfoDetail.setSearchedAt(wifiInfo.getSearchedAt());
            wifiInfoDetailList.add(wifiInfoDetail);
        }
        System.out.println(wifiInfoList.size());

        return wifiInfoDetailList;
    }

    // 와이파이 정보 상세 보기
    public WifiInfoDetail findWifiInfoById(Integer id) {
        WifiInfo wifiInfo = wifiInfoRepository.findWifiInfoById(id);
        WifiInfoDetail wifiInfoDetail = new WifiInfoDetail();

        wifiInfoDetail.setId(id);
        wifiInfoDetail.setWifi(wifiInfoRepository.findWifiById(wifiInfo.getWifiId()));
        wifiInfoDetail.setHistory(historyRepository.findHistoryById(wifiInfo.getHistoryId()));
        wifiInfoDetail.setDistance(wifiInfo.getDistance());
        wifiInfoDetail.setSearchedAt(wifiInfo.getSearchedAt());

        return wifiInfoDetail;
    }
}
