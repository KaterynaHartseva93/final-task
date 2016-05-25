<%@ tag language="java" pageEncoding="UTF8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="store" uri="http://store.kateryna_hartseva.epam.com"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ attribute name="productsList" required="true" type="java.util.List"%>
<%@ attribute name="headerText" required="true"%>
<%@ attribute name="headerClass" required="true"%>

<div class="products-container">
	<div class="products-inner-container">
		<p class="${headerClass}"><fmt:message key="${headerText}"/></p>
		<c:choose>
			<c:when test="${empty productsList}">
				<i id="some-text-style"><fmt:message key="no-clothes"/></i>
			</c:when>
			<c:otherwise>
				<c:forEach items="${productsList}" var="productItem">
					<div class="product-content">
						<div class="product-name">
							<a href="#" class="inspect-product"
								data-product-id="${productItem.id}"
								data-modal-form-id="product-info-modal-form">${productItem.name}</a>
						</div>
						<div class="product-image">
							<img width="250" height="220" alt="Product image" src="${pageContext.request.contextPath}/images?imageIdentifier=${productItem.info.imageIdentifier}">
						</div>
						<div class="product-sizes">
							<fmt:message key="sizes"/>:
							<c:forEach items="${productItem.availableSizes}"
								var="productSize">
								<span class="product-size">${productSize}</span>
							</c:forEach>
						</div>
						<div class="product-price">
							<span><fmt:message key="price"/>:</span> <span class="product-price-certain">${productItem.price}</span>
						</div>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<div class="modal_form width630px" id="product-info-modal-form">
	<div class="modal_header">
		<p class="modal_header_text"><fmt:message key="det-info"/></p>
		<p class="modal_close">X</p>
	</div>
	<div class="modal_content">
		<div class="product-detailed-info-container">
			<form method="post"
				action="${pageContext.request.contextPath}/controller/edit-product">
				<div id="product-detailed-info-image"></div>
				<div class="product-detailed-info-text">
					<table class="product-information-table">
						<tr>
							<td><fmt:message key="name"/></td>
							<td id="product-name-td"></td>
						</tr>
						<tr>
							<td><fmt:message key="price"/></td>
							<td id="product-price-td"></td>
						</tr>
						<tr>
							<td><fmt:message key="brand"/></td>
							<td id="product-brand-td"></td>
						</tr>
						<tr>
							<td><fmt:message key="category"/></td>
							<td id="product-category-td"></td>
						</tr>
					</table>
				</div>
				<div class="product-detailed-info-actions">
					<input type="hidden" id="product-identifier"
						name="id_product" />
					<div id="product-detailed-info-sizes"></div>
					<div id="product-detailed-info-add-to-cart"></div>
				</div>
			</form>
		</div>
	</div>
</div>