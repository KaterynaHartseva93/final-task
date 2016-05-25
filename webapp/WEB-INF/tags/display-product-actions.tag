<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="store" uri="http://store.kateryna_hartseva.epam.com"%>

<c:if test="${store:getRole(pageContext.request).value eq 'admin'}">
	<div class="product-actions-area">
		<a href="#" class="button add-product-action-button"
			data-modal-form-id="add-product-container"><fmt:message key="add-new"/></a>
	</div>
	<div class="modal_form width480px" id="add-product-container">
		<div class="modal_header">
			<p class="modal_header_text"><fmt:message key="add-new-product"/></p>
			<p class="modal_close">X</p>
		</div>
		<div class="modal_content">
			<div class="product-detailed-info-container">
				<form method="post" enctype="multipart/form-data" 
					action="${pageContext.request.contextPath}/controller/add-product">
					<div class="add-product-item">
						<label><fmt:message key="name"/></label> 
						<input type="text" name="product_name" />
					</div>
					<div class="add-product-item">
						<label><fmt:message key="category"/></label> 
						<select id="add-product-category-select">
						</select>
					</div>
					<div class="add-product-item">
						<label><fmt:message key="brand"/></label>
						<select id="add-product-brand-select">
						</select>
					</div>
					<div class="add-product-item">
						<label><fmt:message key="price"/></label> <input type="text" class="product-price-input"
							maxlength="9" name="product_price" />
					</div>
					<div class="add-product-item">
						<label><fmt:message key="sizes"/></label>
						<div class="add-product-sizes-area">
							<label for="xs_add">XS</label>
							<input id="xs_add" type="checkbox" name="product_size" value="XS" />
							
							<label for="s_add">S</label>
							<input id="s_add" type="checkbox" name="product_size" value="S" /> 
							
							<label for="m_add">M</label>
							<input id="m_add" type="checkbox" name="product_size" value="M" /> 
							
							<label for="l_add">L</label>
							<input id="l_add" type="checkbox" name="product_size" value="L" /> 
							
							<br/>
							
							<label for="xl_add">XL</label>
							<input id="xl_add" type="checkbox" name="product_size" value="XL" /> 
							
							<label for="xxl_add">XXL</label>
							<input id="xxl_add" type="checkbox" name="product_size" value="XXL" />
							
							<label for="xxxl_add">XXXL</label>
							<input id="xxxl_add" type="checkbox" name="product_size" value="XXXL" />
						</div>
					</div>
					<div class="add-product-item">
						<label><fmt:message key="image"/></label> <input type="file" name="product-image" accept="image/png"/>
					</div>
					<div class="add-product-item">
						<input type="submit" value="<fmt:message key="create-button"/>" class="button"/>
					</div>
				</form>
			</div>
		</div>
	</div>
</c:if>