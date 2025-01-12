package wifiService.domain.history;

import wifiService.global.DataSourceConfig;

import java.util.List;

public class HistoryService {
    private final HistoryRepository historyRepository = new HistoryRepository();

    // 위치 정보 검색 (입력받은 좌표값을 DB에 저장하기), historyId 반환
    public Integer searchLocation(double lat, double lnt) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        String url = dataSourceConfig.sqliteDriveLoad();

        History history = new History();
        history.setLAT(lat);
        history.setLNT(lnt);

        return historyRepository.saveHistory(history);
    }

    // History 보기 기능
    public List<History> viewHistory() {
        return historyRepository.findAllHistory();
    }
}
