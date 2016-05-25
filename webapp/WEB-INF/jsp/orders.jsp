<%@ include file="/WEB-INF/jspf/page.jspf"%>
<%@ include file="/WEB-INF/jspf/taglib.jspf"%>

<c:set var="contextPath" scope="request"
	value="${pageContext.request.contextPath}" />

<html>
<head>
<!--[if IE]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
<title><fmt:message key="orders"/></title>
<%@ include file="/WEB-INF/jspf/static.jspf"%>
</head>
<body>
	<div id="wrapper">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<div>
			<div class="left-column" id="left-sidebar">
				<store:menu pageName="orders" />
			</div>
			<div id="main-content-container">
				<store:page-info />
				<div id="main-content">
					<div style="padding: 20px 40px;">
						<p class="header-text-style order_header"><fmt:message key="orders"/></p>
						<c:choose>
							<c:when test="${not empty requestScope.orders}">
								<table id="user-table">
									<tr>
										<c:if
											test="${store:getRole(pageContext.request).value eq 'admin'}">
											<td class="user-table-td-head"><fmt:message key="user"/></td>
										</c:if>
										<td class="user-table-td-head"><fmt:message key="date"/></td>
										<td class="user-table-td-head"><fmt:message key="status"/></td>
										<td class="user-table-td-head"><fmt:message key="total-price"/></td>
										<c:if
											test="${store:getRole(pageContext.request).value eq 'admin'}">
											<td class="user-table-td-head"><fmt:message key="action"/></td>
										</c:if>
									</tr>
									<c:forEach items="${requestScope.orders}" var="order">
										<tr>
											<c:if
												test="${store:getRole(pageContext.request).value eq 'admin'}">
												<td class="user-table-td">${order.ownerEmail}</td>
											</c:if>
											<td class="user-table-td">${store:printDate(order.date)}</td>
											<td class="user-table-td"><fmt:message key="${order.status.value}"/></td>
											<td class="user-table-td">$ ${order.getTotalPrice()}</td>
											<c:if
												test="${store:getRole(pageContext.request).value eq 'admin'}">
												<td class="user-table-td">
												<c:choose>
													<c:when test="${order.status.value eq 'registered'}">
														<a class="button" href="${contextPath}/controller/change-order-status?status=PAID&id_order=${order.id}"><fmt:message key="accept"/></a> 
														<a class="red-button" href="${contextPath}/controller/change-order-status?status=CANCELLED&id_order=${order.id}"><fmt:message key="decline"/></a>
													</c:when>
													<c:otherwise>
														<fmt:message key="no-actions"/>
													</c:otherwise>
												</c:choose>
												</td>
											</c:if>
										</tr>
									</c:forEach>
								</table>
							</c:when>
							<c:otherwise>
								<p id="some-text-style"><fmt:message key="no-orders"/></p>
							</c:otherwise>
						</c:choose>
					</div>
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