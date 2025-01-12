<%@ page import="wifiService.domain.history.HistoryService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String requestStr = request.getParameter("id");
    if (requestStr != null) {
        Integer historyId = Integer.parseInt(request.getParameter("id"));
        HistoryService historyService = new HistoryService();
        historyService.deleteHistory(historyId);
    }
%>
<script>
    alert("위치 정보를 삭제하였습니다.");
    location.href='${pageContext.request.contextPath}/history.jsp';
</script>