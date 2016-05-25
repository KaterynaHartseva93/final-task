<%@ page isErrorPage="true"%>
<%@ include file="/WEB-INF/jspf/page.jspf"%>
<%@ include file="/WEB-INF/jspf/taglib.jspf"%>

<c:set var="contextPath" scope="request"
	value="${pageContext.request.contextPath}" />

<html>
<c:set var="title" value="Error" scope="page" />
<head>
<title>404 | Not found</title>
<%@ include file="/WEB-INF/jspf/static.jspf"%>
</head>
<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<div class="main-error">
		<p>404 Not Found</p>
		<h2 class="error">Unfortunately, the requested resource is not found. Please, check your spelling and provide a valid resource.</h2>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>