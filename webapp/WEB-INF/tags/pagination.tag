<%@ tag language="java" pageEncoding="UTF8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="store" uri="http://store.kateryna_hartseva.epam.com"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:choose>
	<c:when test="${empty param.page}">
		<c:set var="pageNum" value="1" scope="request"></c:set>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${param.page gt pagesCount}">
				<c:set var="pageNum" value="${pagesCount}" scope="request"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="pageNum" value="${param.page}" scope="request"></c:set>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
<div class="products-pagination-container">
	<span class="products-pagination-text"><fmt:message key="pages"/>:</span> <a href="#"
		class="go_to_page <c:if test='${pageNum eq 1}'>selected_page</c:if>">1</a>
	<c:if test="${pageNum ne 1 && pageNum ne 2}">
		<span>...</span>
	</c:if>
	<c:if test="${pagesCount > 1}">
		<c:forEach var="i" begin="2" end="${pagesCount - 1}">
			<c:if test="${i eq (pageNum - 1) || i eq (pageNum + 1)}">
				<a href="#" class="go_to_page">${i}</a>
			</c:if>
			<c:if test="${i eq pageNum}">
				<a href="#" class="go_to_page selected_page">${i}</a>
			</c:if>
		</c:forEach>
		<c:if test="${pageNum ne (pagesCount - 1) && pageNum ne (pagesCount)}">
			<span>...</span>
		</c:if>
		<c:if test="${pagesCount ne 1}">
			<a href="#"
				class="go_to_page <c:if test='${pagesCount eq pageNum}'>selected_page</c:if>">${pagesCount}</a>
		</c:if>
	</c:if>
</div>
