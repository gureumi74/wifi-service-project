<%@ page import="wifiService.domain.bookmark.BookmarkService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String historyIdStr = request.getParameter("historyId");
    String bookmarkIdStr = request.getParameter("bookmarkId");
    if (historyIdStr != null && bookmarkIdStr != null) {
        Integer historyId = Integer.parseInt(historyIdStr);
        Integer bookmarkId = Integer.parseInt(bookmarkIdStr);

        BookmarkService bookmarkService = new BookmarkService();
        bookmarkService.insertBookmarkHistory(bookmarkId, historyId);
    }
%>
<script>
    alert("북마크 정보를 추가했습니다.");
    history.back();
</script>