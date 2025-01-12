<%@ page import="java.util.List" %>
<%@ page import="wifiService.domain.wifi.Wifi" %>
<%@ page import="wifiService.domain.history.HistoryService" %>
<%@ page import="wifiService.domain.wifi.WifiApiService" %>
<%@ page import="wifiService.domain.wifi.WifiInfoDetail" %>
<%@ page import="wifiService.domain.wifi.WifiInfoService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src="index.js" defer></script>
    <title>와이파이 정보</title>
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
    double lat = Double.parseDouble(request.getParameter("lat"));
    double lnt = Double.parseDouble(request.getParameter("lnt"));

    HistoryService historyService = new HistoryService();
    // 위치 저장
    Integer historyId = historyService.searchLocation(lat, lnt);
    // 가까운 와이파이 20개 저장 + 가져오기
    WifiInfoService wifiInfoService = new WifiInfoService();
    wifiInfoService.find20AndSaveWifiInfo(lat, lnt, historyId);
    List<WifiInfoDetail> wifiList = wifiInfoService.wifiInfo20(historyId);
%>
<h1>와이파이 정보</h1>
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
<p class="wifi-info">
    <table id="wifiService">
        <tr>
            <th>거리(km)</th>
            <th>관리번호</th>
            <th>자치구</th>
            <th>와이파이명</th>
            <th>도로명주소</th>
            <th>상세주소</th>
            <th>설치위치(층)</th>
            <th>설치유형</th>
            <th>설치기관</th>
            <th>서비스구분</th>
            <th>망종류</th>
            <th>설치년도</th>
            <th>실내외구분</th>
            <th>WIFI접속환경</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>작업일자</th>
        </tr>
        <tbody>
            <%
                for (WifiInfoDetail wifiInfoDetail: wifiList) {
                    out.write("<tr>");
                    out.write("<td>" + wifiInfoDetail.getDistance() + "</td>");
                    out.write("<td>" + wifiInfoDetail.getWifi().getMgrNo() + "</td>");
                    out.write("<td>" + wifiInfoDetail.getWifi().getWrdofc() + "</td>");
                    %>
                    <td>
                        <a href="${pageContext.request.contextPath}/wifi-detail.jsp?id=<%= wifiInfoDetail.getId() %>">
                            <%= wifiInfoDetail.getWifi().getWifiName() %>
                        </a>
                    </td>
                    <%
                    out.write("<td>" + wifiInfoDetail.getWifi().getAddress1() + "</td>");
                    out.write("<td>" + wifiInfoDetail.getWifi().getAddress2() + "</td>");
                    out.write("<td>" + wifiInfoDetail.getWifi().getInstlFloor() + "</td>");
                    out.write("<td>" + wifiInfoDetail.getWifi().getInstlTy() + "</td>");
                    out.write("<td>" + wifiInfoDetail.getWifi().getInstlMby() + "</td>");
                    out.write("<td>" + wifiInfoDetail.getWifi().getSvcSe() + "</td>");
                    out.write("<td>" + wifiInfoDetail.getWifi().getCmcwr() + "</td>");
                    out.write("<td>" + wifiInfoDetail.getWifi().getCnstcYear() + "</td>");
                    out.write("<td>" + wifiInfoDetail.getWifi().getInoutDoor() + "</td>");
                    out.write("<td>" + wifiInfoDetail.getWifi().getRemars() + "</td>");
                    out.write("<td>" + wifiInfoDetail.getWifi().getWifiLAT() + "</td>");
                    out.write("<td>" + wifiInfoDetail.getWifi().getWifiLNT() + "</td>");
                    out.write("<td>" + wifiInfoDetail.getWifi().getWorkDttm() + "</td>");
                    out.write("</tr>");
                }
            %>
        </tbody>
    </table>
</p>
</body>
</html>