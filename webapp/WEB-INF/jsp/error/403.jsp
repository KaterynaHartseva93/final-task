<%@ page isErrorPage="true"%>
<%@ include file="/WEB-INF/jspf/page.jspf"%>
<%@ include file="/WEB-INF/jspf/taglib.jspf"%>

<c:set var="contextPath" scope="request"
	value="${pageContext.request.contextPath}" />

<html>
<c:set var="title" value="Error" scope="page" />
<head>
<title>403 | Unauthorized</title>
<%@ include file="/WEB-INF/jspf/static.jspf"%>
</head>
<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div class="main-error">
		<p>403 Forbidden</p>
		<h2 class="error">Unfortunately, you have no permission to
			execute this request.</h2>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>