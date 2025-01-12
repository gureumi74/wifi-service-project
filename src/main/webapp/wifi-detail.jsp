<%@ page import="java.util.List" %>
<%@ page import="wifiService.domain.wifi.WifiInfoDetail" %>
<%@ page import="wifiService.domain.wifi.WifiInfoService" %>
<%@ page import="wifiService.domain.history.History" %>
<%@ page import="wifiService.domain.history.HistoryService" %>
<%@ page import="wifiService.domain.bookmark.BookmarkGroup" %>
<%@ page import="wifiService.domain.bookmark.BookmarkService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src="index.js" defer></script>
    <title>와이파이 상세정보</title>
    <style>
        #wifiService {
        font-family: Arial, Helvetica, sans-serif;
        border-collapse: collapse;
        width: 100%;
        }

        #wifiService td, #wifiService th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #wifiService tr:nth-child(even){background-color: #f2f2f2;}

        #wifiService tr:hover {background-color: #ddd;}

        #wifiService th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #04AA6D;
            color: white;
        }

        #wifiService td.centered {
            text-align: center;
            vertical-align: middle;
            background-color: #f9f9f9;
            color: #555;
            height: 50px; /* 높이 설정으로 시각적으로 중앙 정렬 */
        }
    </style>
</head>
<body>
<%
    Integer id = Integer.parseInt(request.getParameter("id"));
    WifiInfoDetail wifiInfoDetail = null;

    if (id != null) {
        WifiInfoService wifiInfoService = new WifiInfoService();
        wifiInfoDetail = wifiInfoService.findWifiInfoById(id);
    }
    BookmarkService bookmarkService = new BookmarkService();
    List<BookmarkGroup> bookmarkList = bookmarkService.viewBookmarkGroup();
%>
<h1>와이파이 상세 정보</h1>
<form id ="locationForm" action="${pageContext.request.contextPath}/wifi-info.jsp" method="get">
    <label for="lat">LAT:</label>
    <input type="text" id="lat" placeholder="0.0" name="lat">
    <label for="lnt">LNT:</label>
    <input type="text" id="lnt" placeholder="0.0" name="lnt">
    <button type="button" onclick="getLocation()">내 위치 가져오기</button>
    <button type="submit">근처 WIFI 정보 보기</button>
</form>
<p>
    <a href="${pageContext.request.contextPath}/">홈</a>
    <a href="${pageContext.request.contextPath}/history.jsp">위치 히스토리 목록</a>
    <a href="${pageContext.request.contextPath}/load-api.jsp">Open API 정보 가져오기</a>
    <a href="${pageContext.request.contextPath}/bookmark-view.jsp">즐겨찾기 보기</a>
    <a href="${pageContext.request.contextPath}/bookmark-manage.jsp">즐겨찾기 관리</a>
</p>
<form id="bookmarkForm" action="${pageContext.request.contextPath}/bookmark-add.jsp" onsubmit="return checkBookmarkForm()">
    <select if="bookmarkId" name="bookmarkId">
        <option value="" selected>북마크 그룹 선택</option>
        <% for (BookmarkGroup bookmarkGroup : bookmarkList) { %>
            <option value="<%= bookmarkGroup.getId() %>"><%= bookmarkGroup.getName() %></option>
        <% } %>
    </select>
    <input type="hidden" name="id" value="<%= wifiInfoDetail.getId() %>">
    <button type="submit">즐겨찾기 추가하기</button>
</form>
<script>
    // 북마크 그룹 선택시에만 제출 가능하도록 설정
    function checkBookmarkForm() {
        const bookmarkSelect = document.querySelector('select[name="bookmarkId"]');
        const selectedValue = bookmarkSelect.value;

        if (selectedValue === "") {
            alert("북마크 그룹을 선택해주세요.");
            return false;
        }

        return true;
    }
</script>
<p class="wifi-info">
    <table id="wifiService">
        <tr>
            <th>거리(km)</th>
            <td><%= wifiInfoDetail.getDistance() %></td>
        <tr>
            <th>관리번호</th>
            <td><%= wifiInfoDetail.getWifi().getMgrNo() %></td>
        </tr>
        <tr>
            <th>자치구</th>
            <td><%= wifiInfoDetail.getWifi().getWrdofc() %></td>
        </tr>
        <tr>
            <th>와이파이명</th>
            <td>
                <a href="${pageContext.request.contextPath}/wifi-detail.jsp?id=<%= wifiInfoDetail.getId() %>">
                    <%= wifiInfoDetail.getWifi().getWifiName() %>
                </a>
            </td>
        </tr>
        <tr>
            <th>도로명주소</th>
            <td><%= wifiInfoDetail.getWifi().getAddress1() %></td>
        </tr>
        <tr>
            <th>상세주소</th>
            <td><%= wifiInfoDetail.getWifi().getAddress2() %></td>
        </tr>
        <tr>
            <th>설치위치(층)</th>
            <td><%= wifiInfoDetail.getWifi().getInstlFloor() %></td>
        </tr>
        <tr>
            <th>설치유형</th>
            <td><%= wifiInfoDetail.getWifi().getInstlTy() %></td>
        </tr>
        <tr>
            <th>설치기관</th>
            <td><%= wifiInfoDetail.getWifi().getInstlMby() %></td>
        </tr>
        <tr>
            <th>서비스구분</th>
            <td><%= wifiInfoDetail.getWifi().getSvcSe() %></td>
        </tr>
        <tr>
            <th>망종류</th>
            <td><%= wifiInfoDetail.getWifi().getCmcwr() %></td>
        </tr>
        <tr>
            <th>설치년도</th>
            <td><%= wifiInfoDetail.getWifi().getCnstcYear() %></td>
        </tr>
        <tr>
            <th>실내외구분</th>
            <td><%= wifiInfoDetail.getWifi().getInoutDoor() %></td>
        </tr>
        <tr>
            <th>와이파이접속환경</th>
            <td><%= wifiInfoDetail.getWifi().getRemars() %></td>
        </tr>
        <tr>
            <th>X좌표</th>
            <td><%= wifiInfoDetail.getWifi().getWifiLAT() %></td>
        </tr>
        <tr>
            <th>Y좌표</th>
            <td><%= wifiInfoDetail.getWifi().getWifiLNT() %></td>
        </tr>
        <tr>
            <th>작업일자</th>
            <td><%= wifiInfoDetail.getWifi().getWorkDttm() %></td>
        </tr>
    </table>
</p>
</body>
</html>