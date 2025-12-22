<%@ include file="include/importTags.jsp" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<spring:message var="loginEmail" code="login.email"/>
<spring:message var="loginPassword" code="login.password"/>

<html lang="fr">
<head>
    <link type="text/css" href="<spring:url value='/css/style.css'/>" rel="stylesheet">
</head>
<div class="form-wrapper">
    <h2 class="form-title"><spring:message code="login.title"/></h2>

    <c:if test="${param.error != null}">
        <div class="form-alert form-alert-error">
            <spring:message code="login.error"/>
        </div>
    </c:if>

    <form:form modelAttribute="user" method="POST" class="form">
        <form:input path="username" placeholder="${loginEmail}" cssClass="form-input" />
        <form:password path="password" placeholder="${loginPassword}" cssClass="form-input" />
        <form:button type="submit" class="form-button">
            <spring:message code="login.submit"/>
        </form:button>
    </form:form>

</div>
<a type="submit" class="form-button" href="<c:url value='/register'/>">
    <spring:message code="login.register"/>
</a>
</body>
</div>

</html>
