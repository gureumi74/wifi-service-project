<%@ page import="java.util.List" %>
<%@ page import="wifiService.domain.bookmark.BookmarkDetail" %>
<%@ page import="wifiService.domain.bookmark.BookmarkService" %>
<%@ page import="wifiService.domain.history.History" %>
<%@ page import="wifiService.domain.history.HistoryService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src="index.js" defer></script>
    <title>북마크</title>
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
    BookmarkService bookmarkService = new BookmarkService();
    List<BookmarkDetail> list = bookmarkService.viewBookmark();
%>
<h1>북마크 정보</h1>
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
            <th>북마크 이름</th>
            <th>와이파이명</th>
            <th>등록일자</th>
            <th>비고</th>
        </tr>
        <tbody>
            <% if (!list.isEmpty()) {
                for (BookmarkDetail bookmarkDetail : list) {
                    out.write("<tr>");
                    out.write("<td>" + bookmarkDetail.getId() + "</td>");
                    out.write("<td>" + bookmarkDetail.getBookmarkGroup().getName() + "</td>");
                    %>
                    <td>
                        <a href="${pageContext.request.contextPath}/wifi-detail.jsp?id=<%= bookmarkDetail.getWifiInfoDetail().getId() %>">
                            <%= bookmarkDetail.getWifiInfoDetail().getWifi().getWifiName() %>
                        </a>
                    </td>
                    <%
                    out.write("<td>" + bookmarkDetail.getCreatedAt() + "</td>");
                    %>
                    <td>
                   <a href="${pageContext.request.contextPath}/bookmark-delete.jsp?id=<%= bookmarkDetail.getId() %>">삭제</a>
                    </td>
                    <%
                    out.write("</tr>");
                }
            } else {
                out.write("<tr> <td colspan='17' class='centered'>북마크 정보가 존재하지 않습니다.</td> </tr>");
            }
            %>
        </tbody>
    </table>
</p>
</body>
</html>