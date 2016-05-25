<%@ page isErrorPage="true"%>
<%@ include file="/WEB-INF/jspf/page.jspf"%>
<%@ include file="/WEB-INF/jspf/taglib.jspf"%>

<c:set var="contextPath" scope="request"
	value="${pageContext.request.contextPath}" />

<html>
<c:set var="title" value="Error" scope="page" />
<head>
<title>400 | Bad request</title>
<%@ include file="/WEB-INF/jspf/static.jspf"%>
</head>
<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div class="main-error">
		<p>400 Bad request</p>
		<c:choose>
			<c:when test="${not empty sessionScope.pageErrorMessage}">
				<h2 class="error">${sessionScope.pageErrorMessage}</h2>
				<c:remove var="pageErrorMessage" scope="session"/>
			</c:when>
			<c:otherwise>
				<h2 class="error">Invalid searching parameters</h2>
			</c:otherwise>
		</c:choose>
		
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>