package wifiService.domain.bookmark;

import wifiService.domain.wifi.WifiInfoRepository;
import wifiService.domain.wifi.WifiInfoService;

import java.util.ArrayList;
import java.util.List;

public class BookmarkService {
    private final BookmarkRepository bookmarkRepository = new BookmarkRepository();
    private final WifiInfoRepository wifiInfoRepository = new WifiInfoRepository();
    private final WifiInfoService wifiInfoService = new WifiInfoService();

    public List<BookmarkGroup> viewBookmarkGroup() {
        return bookmarkRepository.findAllBookmarkGroup();
    }

    public void createBookmark(String name, Integer no) {
        bookmarkRepository.createBookmarkGroup(name, no);
    }

    public BookmarkGroup findBookmarkGroupById(Integer id) {
        return bookmarkRepository.findBookmarkGroupById(id);
    }

    // 북마크 Wifi 추가
    public void createBookmark(Integer bookmarkId, Integer wifiInfoId) {
        bookmarkRepository.createBookmark(bookmarkId, wifiInfoId);
    }

    // 북마크 Wifi 조회
    public List<BookmarkDetail> viewBookmark() {
        List<Bookmark> bookmarkList = bookmarkRepository.findAllBookmark();
        List<BookmarkDetail> bookmarkDetailList = new ArrayList<>();

        for (Bookmark bookmark : bookmarkList) {
            BookmarkDetail bookmarkDetail = new BookmarkDetail();
            bookmarkDetail.setId(bookmark.getId());
            bookmarkDetail.setBookmarkGroup(bookmarkRepository.findBookmarkGroupById(bookmark.getGroupId()));
            bookmarkDetail.setWifiInfoDetail(wifiInfoService.findWifiInfoById(bookmark.getWifiInfoId()));
            bookmarkDetail.setCreatedAt(bookmark.getCreatedAt());
            bookmarkDetailList.add(bookmarkDetail);
        }

        return bookmarkDetailList;
    }

    // 북마크 그룹 수정
    public void updateBookmarkGroup(Integer id, String newGroupName, Integer newGroupNo) {
        bookmarkRepository.updateBookmarkGroup(id, newGroupName, newGroupNo);
    }

    // 북마크 그룹 삭제
    public void deleteBookmarkGroup(Integer id) {
        bookmarkRepository.deleteBookmarkGroup(id);
    }

    // 북마크 Wifi 삭제
    public void deleteBookmark(Integer id) {
        bookmarkRepository.deleteBookmark(id);
    }
}
