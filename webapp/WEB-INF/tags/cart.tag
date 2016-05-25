<%@ tag language="java" pageEncoding="UTF8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if
	test="${empty sessionScope.user || sessionScope.user.role.value eq 'client'}">
	<div id="cart">
		<div id="cart-inner-container">
			<div id="cart-header">
				<i class="fa fa-shopping-bag fa-lg"></i> <fmt:message key="cart"/>
			</div>
			<div id="cart-content">
				<c:choose>
					<c:when test="${not empty sessionScope.cart && !sessionScope.cart.isEmpty()}">
						<fmt:message key="cart-contains"/> <span id="cart-size-span">${sessionScope.cart.getCount()}</span>
							<fmt:message key="product-s"/><br />
						<fmt:message key="worth"/> $<span id="cart-total-price-span">${sessionScope.cart.getTotalPrice()}</span>.
					</c:when>
					<c:otherwise>
						<fmt:message key="empty-cart"/>.
			</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</c:if>
