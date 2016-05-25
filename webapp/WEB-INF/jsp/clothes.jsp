<%@ include file="/WEB-INF/jspf/page.jspf"%>
<%@ include file="/WEB-INF/jspf/taglib.jspf"%>
<c:set var="contextPath" scope="request"
	value="${pageContext.request.contextPath}" />
<html>
<head>
<!--[if IE]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
<title><fmt:message key="clothes" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/jspf/static.jspf"%>
</head>
<body>
	<div id="wrapper">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<div class="left-column" id="left-sidebar">
			<store:menu pageName="clothes" />
			<div id="clothes-criteria-area">
				<div class="clothes-criteria-header">
					<fmt:message key="find-by-criteria" />
				</div>
				<div class="clothes-criteria-content">
					<form method="get">
						<div class="clothes-criteria-item">
							<div class="clothes-search-criteria-brand">
								<p class="criteria-header">
									<fmt:message key="brand" />
								</p>
								<c:forEach items="${requestScope.brands}" var="brandItem">
									<input class="brand-checkbox-to-copy"
										data-brand-id="${brandItem.id}"
										data-brand-name="${brandItem.name}" 
										type="checkbox" name="brand"
										value="${brandItem.id}"
										${store:containsInArray(requestScope.chosenBrands, brandItem.id) ? 'checked' : ''} />${brandItem.name}<br />
								</c:forEach>
							</div>
						</div>
						<div class="criteria-selection">
							<p class="criteria-header">
								<fmt:message key="category" />
							</p>
							<select name="category" class="select-for-choose">
								<option value=""><fmt:message key="select-category" /></option>
								<c:forEach items="${requestScope.categories}" var="category">
									<option class="category-option-to-copy"
										data-category-id="${category.id}"
										data-category-name="<fmt:message key='${category.name.toLowerCase()}' />"
										value="${category.id}"
										${(requestScope.chosenCategory eq category.id)  ? 'selected' : ''}><fmt:message
											key="${category.name.toLowerCase()}" /></option>
								</c:forEach>
							</select>
						</div>
						<div class="criteria-selection">
							<p class="criteria-header">
								<fmt:message key="find-by-name" />
							</p>
							<input type="text" name="namePart"
								value="${requestScope.chosenNamePart}" />
						</div>
						<div class="criteria-selection">
							<p class="criteria-header">
								<fmt:message key="price" />
							</p>
							<div id="price-criteria-search">
								<i><fmt:message key="from" /></i> <input
									class="price-criteria-search" type="text" name="minPrice"
									value="${requestScope.chosenMinPrice}" /> <i><fmt:message
										key="to" /></i> <input class="price-criteria-search" type="text"
									name="maxPrice" value="${requestScope.chosenMaxPrice}" />
							</div>
						</div>
						<div class="criteria-selection">
							<p class="criteria-header">
								<fmt:message key="sort-by" />
							</p>
							<select name="orderBy" class="select-for-choose">
								<option value="">...</option>
								<option value="name"
									${(requestScope.chosenOrderName eq 'name')  ? 'selected' : ''}><fmt:message
										key="sort-by-name" /></option>
								<option value="price"
									${(requestScope.chosenOrderName eq 'price')  ? 'selected' : ''}><fmt:message
										key="sort-by-price" /></option>
								<option value="date"
									${(requestScope.chosenOrderName eq 'data')  ? 'selected' : ''}><fmt:message
										key="sort-by-creation-date" /></option>
							</select>
						</div>
						<div class="criteria-selection">
							<p class="criteria-header">
								<fmt:message key="order" />
							</p>
							<select name="type" class="select-for-choose">
								<option value="">...</option>
								<option value="asc"
									${(requestScope.chosenOrderType eq 'asc')  ? 'selected' : ''}><fmt:message
										key="order-ascending" /></option>
								<option value="desc"
									${(requestScope.chosenOrderType eq 'desc')  ? 'selected' : ''}><fmt:message
										key="order-descending" /></option>
							</select>
						</div>
						<input type="submit" value="<fmt:message key="search-button"/>"
							id="seach-by-criteas-button" />
					</form>
				</div>
			</div>
		</div>
		<div id="main-content-container">
			<store:page-info />
			<div id="main-content">
				<div class="product-control-panel">
					<store:pagination />
				</div>
				<store:display-products headerClass="color-green-blue"
					headerText="clothes" productsList="${requestScope.clothes}" />
			</div>
		</div>
		<div class="right-column" id="right-sidebar">
			<store:cart />
			<store:login-form />
			<store:display-product-actions />
		</div>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>