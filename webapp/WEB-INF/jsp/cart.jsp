<%@ include file="/WEB-INF/jspf/page.jspf"%>
<%@ include file="/WEB-INF/jspf/taglib.jspf"%>
<%@ taglib uri="http://store.kateryna_hartseva.epam.com" prefix="store"%>

<c:set var="contextPath" scope="request"
	value="${pageContext.request.contextPath}" />

<html>
<head>
<!--[if IE]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
<title><fmt:message key="cart"/></title>
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
					<div id="container-for-cart">
						<p class="header-text-style cart_header"><fmt:message key="cart"/></p>
						<c:choose>
							<c:when test="${empty sessionScope.cart || sessionScope.cart.isEmpty()}">
								<p id="some-text-style"><fmt:message key="no-items"/>.</p>
							</c:when>
							<c:otherwise>
								<table id="user-table">
									<tr>
										<td class="user-table-td-head">Delete</td>
										<td class="user-table-td-head">Name</td>
										<td class="user-table-td-head">Size</td>
										<td class="user-table-td-head">Price</td>
										<td class="user-table-td-head" style="width: 120px;">Count</td>
										<td class="user-table-td-head">Total Price</td>
									</tr>
									<c:forEach items="${sessionScope.cart.getItems().keySet()}"
										var="prod">
										<tr id="row-for-product-${prod.hashCode()}" data-product-id-item="${prod.product.id}">
											<td class="user-table-td"><a href="#"
												class="delete-position-from-table"
												data-product-item-hash="${prod.hashCode()}">X</a></td>
											<td class="user-table-td">${prod.product.name}</td>
											<td class="user-table-td" id="product-size-item-${prod.hashCode()}">${prod.size}</td>
											<td class="user-table-td"
												id="product-item-price-${prod.hashCode()}">${prod.product.price}</td>
											<td class="user-table-td">
												<div class="incdec">
													<a href="#" class="sign-button dec-sign-button"
														data-product-item-hash="${prod.hashCode()}">-</a> <input
														class="class-for-count-value"
														id="cart-item-input-${prod.hashCode()}"
														value="${sessionScope.cart.getItems().get(prod)}" /> <a
														href="#" class="sign-button inc-sign-button"
														data-product-item-hash="${prod.hashCode()}">+</a>
												</div>
											</td>
											<td class="user-table-td"
												id="total-price-for-position-${prod.hashCode()}">${store:round(sessionScope.cart.getItems().get(prod) * prod.product.price)}</td>
										</tr>
									</c:forEach>
								</table>
								<br />
								<c:choose>
									<c:when
										test="${store:getRole(pageContext.request).value == 'client'}">
										<div id="checkout-cont">
											<a href="${contextPath}/controller/checkout" class="button"
												id="checkout-button">CHECKOUT</a>
										</div>
									</c:when>
									<c:otherwise>
										<p id="some-text-style">You are not authorized to make
											order.</p>
									</c:otherwise>
								</c:choose>
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