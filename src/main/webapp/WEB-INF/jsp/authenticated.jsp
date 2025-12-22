<%@ include file="include/importTags.jsp" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<spring:url var="account" value="/authenticated"/>

<%-- Messages pour UserUpdate --%>
<spring:message var="updateTitle" code="update.title"/>
<spring:message var="updateUsername" code="update.username"/>
<spring:message var="updatePassword" code="update.password"/>
<spring:message var="updateConfirmPassword" code="update.confirmPassword"/>
<spring:message var="updateAddress" code="update.address"/>
<spring:message var="updatePhone" code="update.phone"/>
<spring:message var="updateSubmit" code="update.submit"/>

<html lang="fr">
<head>
  <link type="text/css" href="<spring:url value='/css/style.css'/>" rel="stylesheet">
  <title>${updateTitle}</title>
</head>

<body>
<div class="form-wrapper">

  <form:form modelAttribute="userUpdate" method="post" action="${account}" class="form">

    <div class="form-field">
      <form:label path="username">${updateUsername}</form:label>
      <form:input path="username" id="username" cssClass="form-input"/>
      <spring:bind path="userUpdate.username">
        <c:if test="${status.error}">
          <div class="form-alert form-alert-error">${status.errorMessages[0]}</div>
        </c:if>
      </spring:bind>
    </div>

    <div class="form-field">
      <form:label path="password">${updatePassword}</form:label>
      <form:password path="password" id="password" cssClass="form-input"/>
      <spring:bind path="userUpdate.password">
        <c:if test="${status.error}">
          <div class="form-alert form-alert-error">${status.errorMessages[0]}</div>
        </c:if>
      </spring:bind>
    </div>

    <div class="form-field">
      <form:label path="confirmPassword">${updateConfirmPassword}</form:label>
      <form:password path="confirmPassword" id="confirmPassword" cssClass="form-input"/>
      <spring:bind path="userUpdate.confirmPassword">
        <c:if test="${status.error}">
          <div class="form-alert form-alert-error">${status.errorMessages[0]}</div>
        </c:if>
      </spring:bind>
    </div>

    <div class="form-field">
      <form:label path="address">${updateAddress}</form:label>
      <form:textarea path="address" id="address" rows="3" cssClass="form-input"/>
      <spring:bind path="userUpdate.address">
        <c:if test="${status.error}">
          <div class="form-alert form-alert-error">${status.errorMessages[0]}</div>
        </c:if>
      </spring:bind>
    </div>

    <div class="form-field">
      <form:label path="phoneNumber">${updatePhone}</form:label>
      <form:input path="phoneNumber" id="phone" type="tel" cssClass="form-input"/>
      <spring:bind path="userUpdate.phoneNumber">
        <c:if test="${status.error}">
          <div class="form-alert form-alert-error">${status.errorMessages[0]}</div>
        </c:if>
      </spring:bind>
    </div>

    <c:if test="${not empty bindingResult.globalErrors}">
      <div class="form-alert form-alert-error">
        <c:forEach var="error" items="${bindingResult.globalErrors}">
          <div><spring:message code="${error.code}" text="${error.defaultMessage}"/></div>
        </c:forEach>
      </div>
    </c:if>

    <form:button type="submit" class="form-button">${updateSubmit}</form:button>
  </form:form>
</div>

</body>
</html>