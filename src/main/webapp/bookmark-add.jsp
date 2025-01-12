<%@ page import="wifiService.domain.bookmark.BookmarkService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Integer wifiInfoId = Integer.parseInt(request.getParameter("id"));
    Integer bookmarkId = Integer.parseInt(request.getParameter("bookmarkId"));
    if (wifiInfoId != null && bookmarkId != null) {
        BookmarkService bookmarkService = new BookmarkService();
        bookmarkService.createBookmark(bookmarkId, wifiInfoId);
    }
%>
<script>
    alert("북마크 정보를 추가했습니다.");
    history.back();
</script>