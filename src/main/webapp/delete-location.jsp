<%@ page import="wifiService.domain.location.LocationService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String requestStr = request.getParameter("locationId");
    if (requestStr != null) {
        Integer locationId = Integer.parseInt(request.getParameter("locationId"));
        LocationService locationService = new LocationService();
        locationService.deleteLocation(locationId);
    }
%>
<script>
    alert("위치 정보를 삭제하였습니다.");
    location.href='${pageContext.request.contextPath}/history.jsp';
</script>