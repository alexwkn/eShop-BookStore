<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container my-5">
    <h1 class="mb-4"><spring:message code="privacy.title"/></h1>

    <p class="text-muted">
        <spring:message code="privacy.lastupdate" arguments="15/08/2025"/>
    </p>

    <h2 class="h5 mt-4"><spring:message code="privacy.section1"/></h2>
    <p><spring:message code="privacy.section1.text"/></p>

    <h2 class="h5 mt-4"><spring:message code="privacy.section2"/></h2>
    <ul>
        <li><spring:message code="privacy.section2.item1"/></li>
        <li><spring:message code="privacy.section2.item2"/></li>
        <li><spring:message code="privacy.section2.item3"/></li>
        <li><spring:message code="privacy.section2.item4"/></li>
    </ul>

    <h2 class="h5 mt-4"><spring:message code="privacy.section3"/></h2>
    <p><spring:message code="privacy.section3.text"/></p>

    <h2 class="h5 mt-4"><spring:message code="privacy.section4"/></h2>
    <p><spring:message code="privacy.section4.text"/></p>

    <h2 class="h5 mt-4"><spring:message code="privacy.section5"/></h2>
    <p><spring:message code="privacy.section5.text"/></p>
</div>
