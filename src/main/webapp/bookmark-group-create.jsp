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
    String bookmarkGroupName = request.getParameter("bookmarkGroupName");
    String bookmarkGroupNo = request.getParameter("bookmarkGroupNo");

    // 모든 파라미터가 존재할 때만 처리
    if (bookmarkGroupName != null && bookmarkGroupNo != null && !bookmarkGroupName.isEmpty() && !bookmarkGroupNo.isEmpty()) {
        int groupNo = Integer.parseInt(bookmarkGroupNo);

        BookmarkService bookmarkService = new BookmarkService();
        bookmarkService.createBookmark(bookmarkGroupName, groupNo);

        // 성공 메시지 출력 및 북마크 페이지로 이동
        out.println("<script>alert('북마크 그룹이 생성되었습니다.'); location.href='" + request.getContextPath() + "/bookmark-manage.jsp';</script>");
    }
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
<form id="bookmarkForm" action="${pageContext.request.contextPath}/bookmark-group-create.jsp" onsubmit="return bookmarkForm()">
    <table id="wifiService">
        <tr>
            <th>북마크 이름</th>
            <td><input type="text" id="bookmarkGroupName" name="bookmarkGroupName"></td>
        </tr>
        <tr>
            <th>순서</th>
            <td><input type="text" id="bookmarkGroupNo" name="bookmarkGroupNo"></td>
        </tr>
        <td colspan="15" class="centered">
            <a href="${pageContext.request.contextPath}/bookmark-manage.jsp">돌아가기</a>
            <button type="submit">추가</button>
        </td>
    </table>

</form>
<script>
    // 값이 모두 들어왔을 때만 create 전송하기
    function bookmarkForm() {
        const bookmarkGroupName = document.getElementById("bookmarkGroupName").value.trim();
        const bookmarkGroupNo = document.getElementById("bookmarkGroupNo").value.trim();

        if (!bookmarkGroupName) {
            alert("북마크 이름을 입력해주세요");
            return false;
        }

        if (!bookmarkGroupNo) {
            alert("순서를 입력해주세요.");
            return false;
        }

        if (isNaN(bookmarkGroupNo)) {
            alert("순서는 숫자로 입력해야 합니다.");
            return false;
        }

        return true;
    }
</script>
</body>
</html>