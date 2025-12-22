<%@ include file="include/importTags.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
    <title><spring:message code="admin.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet"/>
</head>
<body class="bg-light">

<div class="container py-4">

    <div class="d-flex align-items-center justify-content-between mb-4">
        <h1 class="h4 m-0"><i class="bi bi-ticket-perforated me-2"></i><spring:message code="admin.codes"/></h1>
        <a class="btn btn-outline-secondary btn-sm" href="<spring:url value='/home'/>">
            <i class="bi bi-arrow-left"></i> <spring:message code="admin.back"/>
        </a>
    </div>

    <c:if test="${not empty successCode}">
        <div class="alert alert-success">
            <spring:message code="${successCode}" arguments="${successArgs}"/>
        </div>
    </c:if>
    <c:if test="${not empty errorCode}">
        <div class="alert alert-danger">
            <spring:message code="${errorCode}" arguments="${errorArgs}"/>
        </div>
    </c:if>


    <div class="card mb-4">
        <div class="card-header fw-semibold">
            <i class="bi bi-plus-circle me-2"></i><spring:message code="admin.new"/>
        </div>
        <div class="card-body">
            <form:form modelAttribute="discount" method="post" action="${ctx}/admin/promo-codes" cssClass="row g-3">

                <div class="col-md-4">
                    <label for="code" class="form-label"><spring:message code="admin.code"/></label>
                    <form:input path="code" id="code" maxlength="64" cssClass="form-control"/>
                    <form:errors path="code" cssClass="text-danger small"/>
                </div>

                <div class="col-md-3">
                    <label for="discountField" class="form-label"><spring:message code="admin.discount"/></label>
                    <div class="input-group">
                        <form:input path="discount" id="discountField" type="number" step="0.01" min="0" cssClass="form-control"/>
                        <span class="input-group-text">%</span>
                    </div>
                    <form:errors path="discount" cssClass="text-danger small"/>
                </div>

                <div class="col-md-3 d-flex align-items-end">
                    <div class="form-check">
                        <form:checkbox path="valid" id="isValid" cssClass="form-check-input"/>
                        <label class="form-check-label" for="isValid"><spring:message code="admin.active"/></label>
                    </div>
                </div>

                <div class="col-12">
                    <button class="btn btn-dark">
                        <i class="bi bi-save"></i> <spring:message code="admin.create"/>
                    </button>
                </div>
            </form:form>
        </div>
    </div>

    <div class="card">
        <div class="card-header fw-semibold">
            <i class="bi bi-list-ul me-2"></i><spring:message code="admin.existing"/>
        </div>
        <div class="card-body p-0">
            <c:choose>
                <c:when test="${empty promoCodes}">
                    <div class="p-4 text-muted"><spring:message code="admin.none"/></div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-hover align-middle m-0">
                            <thead class="table-light">
                            <tr>
                                <th><spring:message code="admin.code"/></th>
                                <th><spring:message code="admin.discount"/></th>
                                <th><spring:message code="admin.status.active"/></th>
                                <th class="text-end"><spring:message code="admin.actions"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="p" items="${promoCodes}">
                                <tr>
                                    <td class="fw-semibold"><c:out value="${p.code}"/></td>
                                    <td><fmt:formatNumber value="${p.discount}" minFractionDigits="0" maxFractionDigits="2"/>%</td>
                                    <td>
                                        <c:if test="${p.valid}">
                                            <span class="badge text-bg-success"><spring:message code="admin.status.active"/></span>
                                        </c:if>
                                        <c:if test="${!p.valid}">
                                            <span class="badge text-bg-secondary"><spring:message code="admin.status.inactive"/></span>
                                        </c:if>
                                    </td>
                                    <td class="text-end">
                                        <form:form method="post" action="${ctx}/admin/promo-codes/${p.code}/delete"
                                                   cssClass="d-inline"
                                                   onsubmit="return confirm('Etes-vous sûr de vouloir supprimer ce code promo ?');">
                                            <button class="btn btn-sm btn-outline-danger">
                                                <i class="bi bi-trash"></i>
                                            </button>
                                        </form:form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
