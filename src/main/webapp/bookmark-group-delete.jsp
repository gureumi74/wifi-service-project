<%@ page import="wifiService.domain.bookmark.BookmarkService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Integer bookmarkId = Integer.parseInt(request.getParameter("id"));
    if (bookmarkId != null) {
        BookmarkService bookmarkService = new BookmarkService();
        bookmarkService.deleteBookmarkGroup(bookmarkId);
    }
%>
<script>
    alert("북마크 그룹 삭제 완료!");
    location.href='${pageContext.request.contextPath}/bookmark-manage.jsp';
</script>