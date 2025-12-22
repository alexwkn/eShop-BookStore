<%@ include file="include/importTags.jsp" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<spring:url var="registerAction" value="/register"/>
<spring:url var="loginUrl" value="/login"/>

<spring:message var="registerTitle" code="register.title"/>
<spring:message var="registerUsername" code="register.username"/>
<spring:message var="registerEmail" code="register.email"/>
<spring:message var="registerPassword" code="register.password"/>
<spring:message var="registerAddress" code="register.address"/>
<spring:message var="registerPhone" code="register.phone"/>
<spring:message var="registerSubmit" code="register.submit"/>
<spring:message var="registerLogin" code="register.login"/>

<html lang="fr">
<head>
  <link type="text/css" href="<spring:url value='/css/style.css'/>" rel="stylesheet">
</head>

<body>
<div class="form-wrapper">
  <h2 class="form-title">${registerTitle}</h2>

  <form:form modelAttribute="user" method="post" action="${registerAction}" class="form">

    <div class="form-field">
      <form:label path="username">${registerUsername}</form:label>
      <form:input path="username" id="username" cssClass="form-input"/>
      <spring:bind path="user.username">
        <c:if test="${status.error}">
          <div class="form-alert form-alert-error">${status.errorMessages[0]}</div>
        </c:if>
      </spring:bind>
    </div>

    <div class="form-field">
      <form:label path="email">${registerEmail}</form:label>
      <form:input path="email" id="email" type="email" cssClass="form-input"/>
      <spring:bind path="user.email">
        <c:if test="${status.error}">
          <div class="form-alert form-alert-error">${status.errorMessages[0]}</div>
        </c:if>
      </spring:bind>
    </div>

    <div class="form-field">
      <form:label path="password">${registerPassword}</form:label>
      <form:password path="password" id="password" cssClass="form-input"/>
      <spring:bind path="user.password">
        <c:if test="${status.error}">
          <div class="form-alert form-alert-error">${status.errorMessages[0]}</div>
        </c:if>
      </spring:bind>
    </div>

    <div class="form-field">
      <form:label path="address">${registerAddress}</form:label>
      <form:textarea path="address" id="address" rows="3" cssClass="form-input"/>
      <spring:bind path="user.address">
        <c:if test="${status.error}">
          <div class="form-alert form-alert-error">${status.errorMessages[0]}</div>
        </c:if>
      </spring:bind>
    </div>

    <div class="form-field">
      <form:label path="phoneNumber">${registerPhone}</form:label>
      <form:input path="phoneNumber" id="phone" type="tel" cssClass="form-input"/>
      <spring:bind path="user.phoneNumber">
        <c:if test="${status.error}">
          <div class="form-alert form-alert-error">${status.errorMessages[0]}</div>
        </c:if>
      </spring:bind>
    </div>

    <form:button type="submit" class="form-button">${registerSubmit}</form:button>
  </form:form>
</div>

<a type="submit" class="form-button" href="${loginUrl}">${registerLogin}</a>

</body>
</html>
