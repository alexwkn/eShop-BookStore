<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />


<section class="hero py-5">
    <div class="container">
        <div class="row align-items-center gy-4">

            <div class="col-lg-5">
                <h1 class="display-5 fw-bold mb-3">
                    <spring:message code="hero.title"/>
                </h1>
                <p class="lead mb-4">
                    <spring:message code="hero.lead"/>
                </p>
                <a href="${ctx}/products" class="btn btn-success btn-lg">
                    <spring:message code="hero.cta"/>
                </a>
            </div>


            <div class="col-lg-7 text-center d-flex justify-content-center gap-3">
                <img
                        src='<spring:url value="/images/presentation1.png"/>'
                        class="img-fluid rounded-3 shadow-lg"
                        style="max-width: 30%;"
                        alt="Présentation 1">

                <img
                        src='<spring:url value="/images/presentation2.png"/>'
                        class="img-fluid rounded-3 shadow-lg"
                        style="max-width: 30%;"
                        alt="Présentation 2">

                <img
                        src='<spring:url value="/images/presentation3.png"/>'
                        class="img-fluid rounded-3 shadow-lg"
                        style="max-width: 30%;"
                        alt="Présentation 3">
            </div>

        </div>
    </div>


</section>
