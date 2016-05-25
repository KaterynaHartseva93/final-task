<%@ include file="/WEB-INF/jspf/page.jspf"%>
<%@ include file="/WEB-INF/jspf/taglib.jspf"%>
<%@ taglib uri="http://store.kateryna_hartseva.epam.com" prefix="store"%>

<c:set var="contextPath" scope="request"
	value="${pageContext.request.contextPath}" />

<html>
<head>
<!--[if IE]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
<title><fmt:message key="user-management"/></title>
<%@ include file="/WEB-INF/jspf/static.jspf"%>
</head>
<body>
	<div id="wrapper">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<div id="main-container">
			<div class="left-column" id="left-sidebar">
				<!-- Print menu here -->
				<store:menu pageName="users" />
			</div>
			<div id="main-content-container">
				<store:page-info />
				<div id="main-content">
					<div style="padding: 20px 40px;">
						<p class="header-text-style order_header"><fmt:message key="users"/></p>
						<c:choose>
							<c:when test="${not empty requestScope.users}">
								<table id="user-table">
									<tr>
										<td class="user-table-td-head"><fmt:message key="first-name"/></td>
										<td class="user-table-td-head"><fmt:message key="last-name"/></td>
										<td class="user-table-td-head"><fmt:message key="email"/></td>
										<td class="user-table-td-head"><fmt:message key="role"/></td>
										<td class="user-table-td-head"><fmt:message key="status"/></td>
										<td class="user-table-td-head"><fmt:message key="gender"/></td>
										<td class="user-table-td-head"><fmt:message key="action"/></td>
									</tr>
									<c:forEach items="${requestScope.users}" var="theUser">
										<tr>
											<td class="user-table-td">${theUser.firstName}</td>
											<td class="user-table-td">${theUser.lastName}</td>
											<td class="user-table-td">${theUser.email}</td>
											<td class="user-table-td"><fmt:message key="${theUser.role.value}"/></td>
											<td class="user-table-td-status"><fmt:message key="${theUser.status.value}"/></td>
											<td class="user-table-td"><fmt:message key="${theUser.gender.value}"/></td>
											<td class="user-table-td">
												<form method="post">
													<input type="hidden" name="userId" value="${theUser.id}">
													<input type="hidden" name="status"
														value="${store:getOppositeStatus(theUser.status).value}">
													<c:choose>
														<c:when test="${theUser.status.value eq 'active'}">
															<input type="submit" value="<fmt:message key='block'/>" class="button" />
														</c:when>
														<c:otherwise>
															<input type="submit" value="<fmt:message key='activate'/>"  class="button" />
														</c:otherwise>
													</c:choose>
												</form>
											</td>
										</tr>
									</c:forEach>
								</table>
							</c:when>
							<c:otherwise>
								<p id="some-text-style"> No users yet. </p>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			<div class="right-column" id="right-sidebar">
				<store:login-form />
			</div>
		</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>