<%@ tag description="Smart LI tag." %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="cssClass" required="true" description="CSS class name." %>
<%@ attribute name="condition" required="true" description="Condition." %>

<c:choose>
    <c:when test="${condition}">
        <li class="${cssClass}">
            <jsp:doBody/>
        </li>
    </c:when>
    <c:otherwise>
        <li>
            <jsp:doBody/>
        </li>
    </c:otherwise>
</c:choose>