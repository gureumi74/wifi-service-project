<%@ page import="java.util.List" %>
<%@ page import="wifiService.domain.wifi.Wifi" %>
<%@ page import="wifiService.domain.location.LocationService" %>
<%@ page import="wifiService.domain.wifi.WifiApiService" %>
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
    <title>Title</title>
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
    String wifiIdStr = request.getParameter("wifiId");
    String historyIdStr = request.getParameter("historyId");
    Wifi wifi = null;
    History history = null;

    if (wifiIdStr != null && historyIdStr != null) {
        Integer wifiId = Integer.parseInt(wifiIdStr);
        Integer historyId = Integer.parseInt(historyIdStr);
        WifiApiService wifiApiService = new WifiApiService();
        wifi = wifiApiService.getWifiInfo(wifiId);
        HistoryService historyService = new HistoryService();
        history = historyService.getHistoryById(historyId);
    } else {
        System.out.println("디테일 정보 불러오기 실패");
    }

    BookmarkService bookmarkService = new BookmarkService();
    List<BookmarkGroup> bookmarkList = bookmarkService.viewBookmarkGroup();
    System.out.println(bookmarkList.get(0).getName());

    System.out.println(wifi.getWifiName());
%>
<h1>와이파이 정보 구하기</h1>
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
<form id="bookmarkForm" action="${pageContext.request.contextPath}/bookmark-add.jsp">
    <select if="bookmarkId" name="bookmarkId">
    <%
        for (BookmarkGroup bookmarkGroup : bookmarkList) {
    %>
        <option value="<%= bookmarkGroup.getGroupId() %>"><%= bookmarkGroup.getName() %></option>
    <%
        }
    %>
    </select>
    <input type="hidden" name="historyId" value="<%= history.getHistoryId() %>">
    <button type="submit">즐겨찾기 추가하기</button>
</form>
<p class="wifi-info">
    <table id="wifiService">
        <tr>
            <th>거리(km)</th>
            <td><%= history.getDistance() %></td>
        <tr>
            <th>관리번호</th>
            <td><%= wifi.getMgrNo() %></td>
        </tr>
        <tr>
            <th>자치구</th>
            <td><%= wifi.getWrdofc() %></td>
        </tr>
        <tr>
            <th>와이파이명</th>
            <td><%= wifi.getWifiName() %></td>
        </tr>
        <tr>
            <th>도로명주소</th>
            <td><%= wifi.getAddress1() %></td>
        </tr>
        <tr>
            <th>상세주소</th>
            <td><%= wifi.getAddress2() %></td>
        </tr>
        <tr>
            <th>설치위치(층)</th>
            <td><%= wifi.getInstlFloor() %></td>
        </tr>
        <tr>
            <th>설치유형</th>
            <td><%= wifi.getInstlTy() %></td>
        </tr>
        <tr>
            <th>설치기관</th>
            <td><%= wifi.getInstlMby() %></td>
        </tr>
        <tr>
            <th>서비스구분</th>
            <td><%= wifi.getSvcSe() %></td>
        </tr>
        <tr>
            <th>망종류</th>
            <td><%= wifi.getCmcwr() %></td>
        </tr>
        <tr>
            <th>설치년도</th>
            <td><%= wifi.getCnstcYear() %></td>
        </tr>
        <tr>
            <th>실내외구분</th>
            <td><%= wifi.getInoutDoor() %></td>
        </tr>
        <tr>
            <th>와이파이접속환경</th>
            <td><%= wifi.getRemars() %></td>
        </tr>
        <tr>
            <th>X좌표</th>
            <td><%= wifi.getWifiLAT() %></td>
        </tr>
        <tr>
            <th>Y좌표</th>
            <td><%= wifi.getWifiLNT() %></td>
        </tr>
        <tr>
            <th>작업일자</th>
            <td><%= wifi.getWorkDttm() %></td>
        </tr>
    </table>
</p>
</body>
</html>