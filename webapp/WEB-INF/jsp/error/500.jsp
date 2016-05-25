<%@ page isErrorPage="true"%>
<%@ include file="/WEB-INF/jspf/page.jspf"%>
<%@ include file="/WEB-INF/jspf/taglib.jspf"%>

<c:set var="contextPath" scope="request"
	value="${pageContext.request.contextPath}" />
<c:set var="code"
	value="${requestScope['javax.servlet.error.status_code']}" />
<c:set var="message"
	value="${requestScope['javax.servlet.error.message']}" />
<c:set var="exception"
	value="${requestScope['javax.servlet.error.exception']}" />

<html>
<c:set var="title" value="Error" scope="page" />
<head>
<title>500 | Server error</title>
<%@ include file="/WEB-INF/jspf/static.jspf"%>
</head>
<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div class="main-error">
		<p>500 Server error</p>
		<h2 class="error">
			<c:if test="${not empty code}">
				Error code: ${code}<br/>
			</c:if>
			<c:if test="${not empty message}">
				${message}<br/>
			</c:if>
			<%-- if we get this page using forward --%>
			<c:if test="${not empty requestScope.errorMessage}">
				${requestScope.errorMessage}<br/>
			</c:if>
		</h2>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>