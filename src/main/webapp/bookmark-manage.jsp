<%@ page import="java.util.List" %>
<%@ page import="wifiService.domain.bookmark.BookmarkGroup" %>
<%@ page import="wifiService.domain.bookmark.BookmarkService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src="index.js" defer></script>
    <title>북마크 그룹</title>
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
    // 북마크 그룹 리스트 가져오기
    List<BookmarkGroup> groupList = bookmarkService.viewBookmarkGroup();

%>
<h1>북마크 그룹 정보</h1>
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
<form action="${pageContext.request.contextPath}/bookmark-group-create.jsp">
    <button type="summit">북마크 그룹 이름 추가</button>
</form>
<p class="wifi-info">
    <table id="wifiService">
        <tr>
            <th>ID</th>
            <th>북마크 이름</th>
            <th>순서</th>
            <th>등록일자</th>
            <th>수정일자</th>
            <th>비고</th>
        </tr>
        <tbody>
            <% if (!groupList.isEmpty()) {
                for (BookmarkGroup bookmarkGroup : groupList) {
                    out.write("<tr>");
                    out.write("<td>" + bookmarkGroup.getId() + "</td>");
                    out.write("<td>" + bookmarkGroup.getName() + "</td>");
                    out.write("<td>" + bookmarkGroup.getNo() + "</td>");
                    out.write("<td>" + bookmarkGroup.getCreatedAt() + "</td>");
                    if(bookmarkGroup.getUpdatedAt() != null) {
                        out.write("<td>" + bookmarkGroup.getUpdatedAt() + "</td>");
                    } else {
                        out.write("<td> </td>");
                    }
                    %>
                    <td>
                   <a href="${pageContext.request.contextPath}/bookmark-group-update.jsp?id=<%= bookmarkGroup.getId() %>">수정 </a>
                   <a href="${pageContext.request.contextPath}/bookmark-group-delete.jsp?id=<%= bookmarkGroup.getId() %>">삭제</a>
                    </td>
                    <%
                    out.write("</tr>");
                }
            } else {
                out.write("<tr> <td colspan='17' class='centered'>북마크 그룹 정보가 존재하지 않습니다.</td> </tr>");
            } %>
        </tbody>
    </table>
</p>
</body>
</html>