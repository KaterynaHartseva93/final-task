<%@ include file="/WEB-INF/jspf/page.jspf"%>
<%@ include file="/WEB-INF/jspf/taglib.jspf"%>
<%@ taglib uri="http://store.kateryna_hartseva.epam.com" prefix="store"%>

<c:set var="contextPath" scope="request"
	value="${pageContext.request.contextPath}" />

<html>
<head>
<!--[if IE]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
<title><fmt:message key="checkout"/></title>
<%@ include file="/WEB-INF/jspf/static.jspf"%>
</head>
<body>
	<div id="wrapper">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<div id="main-container">
			<div class="left-column" id="left-sidebar">
				<!-- Print menu here -->
				<store:menu pageName="cart" />
			</div>
			<div id="main-content-container">
				<store:page-info />
				<div id="main-content">
					<div style="padding: 20px 40px;">
						<c:choose>
							<c:when test="${empty sessionScope.cart}">
								<label id="some-text-style"> <fmt:message key="no-items"/>. </label>
							</c:when>
							<c:otherwise>
								<p class="header-text-style cart_header"><fmt:message key="checkout"/></p>
								<table id="user-table">
									<tr>
										<td class="user-table-td-head"><fmt:message key="name"/></td>
										<td class="user-table-td-head"><fmt:message key="size"/></td>
										<td class="user-table-td-head"><fmt:message key="price"/></td>
										<td class="user-table-td-head"><fmt:message key="count"/></td>
										<td class="user-table-td-head"><fmt:message key="total-price"/></td>
									</tr>
									<c:forEach items="${sessionScope.cart.getItems().keySet()}"
										var="prod">
										<tr>
											<td class="user-table-td">${prod.product.name}</td>
											<td class="user-table-td">${prod.size}</td>
											<td class="user-table-td"
												id="product-item-price-${prod.hashCode()}">$${prod.product.price}</td>
											<td class="user-table-td">
												${sessionScope.cart.getItems().get(prod)}</td>
											<td class="user-table-td"
												id="total-price-for-position-${prod.hashCode()}">${store:round(sessionScope.cart.getItems().get(prod) * prod.product.price)}</td>
										</tr>
									</c:forEach>
								</table>
								<br/>
								<div id="checkout-cont">
									<a href="${contextPath}/controller/cart" class="button" id="checkout-button">BACK</a>
									<a href="${contextPath}/controller/make-order" class="button" id="checkout-button">MAKE ORDER</a>
								</div>
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