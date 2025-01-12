<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="wifiService.domain.wifi.WifiApiService" %>
<%@ page import="wifiService.domain.wifi.WifiApiRepository" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>공공 와이파이 데이터 저장</title>
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
<%
    WifiApiService wifiApiService = new WifiApiService();
    WifiApiRepository wifiApiRepository = new WifiApiRepository();
    wifiApiService.loadAndSaveWifiData();
    int wifiCnt = wifiApiRepository.getWifiCnt();
%>
<h1><%= wifiCnt %>개의 WIFI 정보를 정상적으로 저장하였습니다. </h1>
<a href="${pageContext.request.contextPath}/">홈 으로 가기</a>
</body>
</html>