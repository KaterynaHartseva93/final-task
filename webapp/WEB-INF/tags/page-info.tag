<%@ include file="/WEB-INF/jspf/taglib.jspf"%>

<c:if test="${not empty sessionScope.pageErrorMessage}">
	<div class="page-message-area" id="page-error-message-area">
		<div class="page-message-area-left">
			${sessionScope.pageErrorMessage}</div>
		<div class="page-message-area-right">
			<a class="page-message-close-link" href="#"
				data-close-target="page-error-message-area">X</a>
		</div>
		<c:remove var="pageErrorMessage" scope="session" />
	</div>
</c:if>

<c:if test="${not empty sessionScope.pageSuccessMessage}">
	<div class="page-message-area" id="page-success-message-area">
		<div class="page-message-area-left">
			${sessionScope.pageSuccessMessage}</div>
		<div class="page-message-area-right">
			<a class="page-message-close-link" href="#"
				data-close-target="page-success-message-area">X</a>
		</div>
		<c:remove var="pageSuccessMessage" scope="session" />
	</div>
</c:if>