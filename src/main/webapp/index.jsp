<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <script src="index.js" defer></script>
    <title>공공 와이파이 서비스 프로젝트</title>
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
<h1>와이파이 정보 구하기</h1>
<form id ="locationForm">
    <label for="lat">LAT:</label>
    <input type="text" id="lat" placeholder="0.0" name="lat">
    <label for="lnt">LNT:</label>
    <input type="text" id="lnt" placeholder="0.0" name="lnt">
    <button type="button" onclick="getLocation()">내 위치 가져오기</button>
    <button id="wifi-info">근처 WIFI 정보 보기</button>
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
            <th>거리(Km)</th>
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
        <tr>
            <td colspan="17" class="centered">위치 정보를 입력한 후에 조회해 주세요</td>
        </tr>
    </table>
</p>
</body>
</html>


