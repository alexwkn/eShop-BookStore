<%@ include file="include/importTags.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container my-5 text-center">
    <h1 class="text-success mb-4">✅ <spring:message code="payment.success"/></h1>
    <p><spring:message code="payment.thankMessage"/> !</p>

    <div class="mt-4">
        <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">
            <spring:message code="payment.backHome"/>
        </a>
    </div>
</div>
