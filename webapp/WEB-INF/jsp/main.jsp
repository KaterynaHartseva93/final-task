<%@ include file="/WEB-INF/jspf/page.jspf"%>
<%@ include file="/WEB-INF/jspf/taglib.jspf"%>

<c:set var="contextPath" scope="request"
	value="${pageContext.request.contextPath}" />

<html>
<head>
<!--[if IE]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
<title><fmt:message key="main"/></title>
<%@ include file="/WEB-INF/jspf/static.jspf"%>
</head>
<body>
	<div id="wrapper">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<div id="main-container">
			<div class="left-column" id="left-sidebar">
				<!-- Print menu here -->
				<store:menu pageName="main" />
			</div>
			<div id="main-content-container">
				<store:page-info />
				<div id="main-content">
					<store:display-products headerClass="color-green-blue"
						headerText="new-clothes" productsList="${requestScope.newest}" />
					<store:display-products headerClass="color-orange"
						headerText="popular"
						productsList="${requestScope.popular}" />
				</div>

			</div>
			<div class="right-column" id="right-sidebar">
				<store:cart />
				<store:login-form />
			</div>
		</div>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>