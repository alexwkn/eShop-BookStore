<%@ include file="include/importTags.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container my-5 text-center">
    <h1 class="text-danger mb-4">❌ <spring:message code="payment.failed"/></h1>
    <p><spring:message code="payment.errorMessage"/></p>

    <div class="mt-4 d-flex justify-content-center gap-3">
        <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary">
            <spring:message code="payment.backHome"/>
        </a>
        <a href="${pageContext.request.contextPath}/orders/checkout" class="btn btn-primary">
            <spring:message code="payment.retry"/>
        </a>
    </div>
</div>
