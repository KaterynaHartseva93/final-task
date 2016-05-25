<%@ tag language="java" pageEncoding="UTF8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="${pageContext.request.contextPath}" />

<c:if test="${empty sessionScope.user}">
	<div class="sign-container">
		<p><fmt:message key="welcome-label"/>!</p>
		<form class="login-form" autocomplete="off" method="post"
			action="${contextPath}/controller/sign-in">
			<input type="text" placeholder="Email" name="email"
				autocomplete="on" /><br /> <input type="password"
				placeholder="<fmt:message key='password'/>" name="password" autocomplete="off" /><br />
			<input type="submit" value="<fmt:message key='sign-in'/>" /> <fmt:message key="or"/> <a href="#" class="go" data-modal-form-id="sign-up-modal-form">
			<fmt:message key="sign-up"/></a><br />
			<c:if test="${not empty sessionScope.errorMessage}">
				<span class="error-message-span">${sessionScope.errorMessage}</span>
			</c:if>
		</form>
	</div>
	<div class="modal_form width400px" id="sign-up-modal-form">
		<div class="modal_header">
			<p class="modal_header_text">Sign Up</p>
			<p class="modal_close">X</p>
		</div>
		<div class="modal_content">
			<form action="${contextPath}/controller/sign-up" id="sign-up-form" method="post">
				<div class="registration-form-error-area">
					<div class="registration-form-error-message">
					</div>
					<div class="registration-form-error-close-button">
						<a class="registration-form-error-close-button-link" href="#">X</a>
					</div>
				</div>

				<div class="registration-form-item">
					<label>First Name <span class="red-asterix">*</span>:
					</label> <input type="text" name="firstName" autocomplete="off" id="reg-form-first-name"/>
				</div>
				<div class="registration-form-item">
					<label>Last Name <span class="red-asterix">*</span>:
					</label> <input type="text" name="lastName" autocomplete="off" id="reg-form-last-name"/>
				</div>
				<div class="registration-form-item">
					<label>Email <span class="red-asterix">*</span>:
					</label> <input type="text" name="email" autocomplete="off" id="reg-form-email"/>
				</div>
				<div class="registration-form-item">
					<label>Password <span class="red-asterix">*</span>:
					</label> <input type="password" name="password" autocomplete="off" id="reg-form-password"/>
				</div>
				<div class="registration-form-item">
					<label>Repeat Password <span class="red-asterix">*</span>:
					</label> <input type="password" name="repeatPassword" autocomplete="off" id="reg-form-confirm-password"/>
				</div>
				<div class="registration-form-item">
					<label>Gender:</label>
					<fieldset class="radio-button-area">
						<div class="radio-item">
							<label for="male-radio">Male</label> <input id="male-radio"
								type="radio" checked="checked" name="gender" value="male"/>
						</div>
						<div class="radio-item">
							<label for="female-radio">Female</label> <input id="female-radio"
								type="radio" name="gender" value="female"/>
						</div>
					</fieldset>
				</div>
				<div class="registration-form-item">
					<input type="submit" value="Sign Up" />
				</div>
			</form>
		</div>
	</div>
</c:if>
<c:remove var="errorMessage" scope="session" />
