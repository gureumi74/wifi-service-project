<%@ page import="java.util.List" %>
<%@ page import="wifiService.domain.location.Location" %>
<%@ page import="wifiService.domain.location.LocationService" %>
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
    LocationService locationService = new LocationService();
    List<Location> locationList = locationService.viewList();
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
<p class="wifi-info">
    <table id="wifiService">
        <tr>
            <th>ID</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>조회일자</th>
            <th>비고</th>
        </tr>
        <tbody>
            <%
                for (Location location : locationList) {
            %>
            <tr>
                <td><%= location.getId() %></td>
                <td><%= location.getLAT() %></td>
                <td><%= location.getLNT() %></td>
                <td><%= location.getSavedAt() %></td>
                <td>
                    <form action="${pageContext.request.contextPath}/delete-location.jsp">
                        <input type="hidden" name="locationId" value="<%= location.getId() %>">
                        <button type="submit">삭제<button>
                    </form>
                </td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</p>
</body>
<script>
    function deleteLocation() {
        // 삭제 완료 메시지 팝업
        var confirmDelete = confirm("삭제가 완료되었습니다.")

        if (confirmDelete) {
            // 삭제 확인 시 페이지 새로 고침
            setTimeout(function() {
                location.reload(); // 새로고침
            }, 2000);
        }
        return true;
    }
</script>
</html>