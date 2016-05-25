<%@ tag language="java" pageEncoding="UTF8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="store" uri="http://store.kateryna_hartseva.epam.com"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ attribute name="pageName" required="true"
	description="Name of the page."%>

<ul class="main-menu">
	<c:forEach items="${applicationScope['access'].pageView}"
		var="pageItem">
		<c:if
			test="${pageItem.role.toString() eq store:getRole(pageContext.request).value}">
			<c:forEach items="${pageItem.pageCategories.pageCategories}"
				var="category">
				<store:li condition="${pageName eq category.pageCategoryKey}"
					cssClass="active-menu-item">
					<a href="${contextPath}${category.pageCategoryPath}">
						<fmt:message key="${category.pageCategoryKey}"/></a>
				</store:li>
			</c:forEach>
		</c:if>
	</c:forEach>
</ul>