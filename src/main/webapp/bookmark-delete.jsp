<%@ page import="wifiService.domain.bookmark.BookmarkService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Integer bookmarkId = Integer.parseInt(request.getParameter("id"));
    if (bookmarkId != null) {
        BookmarkService bookmarkService = new BookmarkService();
        bookmarkService.deleteBookmark(bookmarkId);
    }
%>
<script>
    alert("북마크 삭제 완료!");
    location.href='${pageContext.request.contextPath}/bookmark-view.jsp';
</script>