<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>공공 와이파이 데이터 저장</title>
  <script>
    setTimeout(function() {
        window.location.href = "${pageContext.request.contextPath}/load-api-complete.jsp"
    }, 500);
  </script>
  <style>
    body {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
    }
  </style>
</head>
<body>
<h1>데이터를 저장하고 있습니다...</h1>
<p>데이터를 모두 저장하기 전까지 시간이 소요될 수 있습니다.</p>
<a href="${pageContext.request.contextPath}/">홈 으로 가기</a>
</body>
</html>
