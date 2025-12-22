<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="../include/importTags.jsp" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title><c:out value="${pageTitle}"/></title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet"/>

    <link rel="stylesheet" href="${ctx}/css/style.css"/>

    <spring:url var="localeFr" value="">
        <spring:param name="lang" value="fr"/>
    </spring:url>

    <spring:url var="localeEn" value="">
        <spring:param name="lang" value="en"/>
    </spring:url>

</head>
<body>
<!-- Cart Slider -->
<div class="offcanvas offcanvas-end" tabindex="-1" id="cartOffcanvas" aria-labelledby="cartOffcanvasLabel">
    <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="cartOffcanvasLabel">
            <i class="bi bi-cart3"></i> <spring:message code="navbar.cart"/>
        </h5>
        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas"
                aria-label="<spring:message code="cart.close"/>"></button>
    </div>
    <div class="offcanvas-body d-flex flex-column">

        <!-- Liste des produits du panier -->
        <c:if test="${not empty cartItems}">
            <ul class="list-group list-group-flush mb-3">
                <c:forEach var="item" items="${cartItems}">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <div class="d-flex align-items-center gap-2">
                            <img src="${ctx}/products/${item.product.id}/image"
                                 alt="${item.product.name}"
                                 style="width:50px;height:50px;object-fit:cover;"
                                 onerror="this.src='https://placehold.jp/50x50.png';">

                            <div>
                                <div class="fw-semibold"><c:out value="${item.product.name}"/></div>
                                <form action="${ctx}/cart/update/${item.product.id}" method="post"
                                      class="d-flex align-items-center gap-2 auto-submit-form">
                                    <input type="number"
                                           name="quantity"
                                           value="${item.quantity}"
                                           min="1"
                                           step="1"
                                           class="form-control form-control-sm"
                                           style="width: 70px; text-align:center;"
                                           oninput="this.value = Math.floor(this.value)"
                                           onchange="this.form.submit();"/>
                                </form>
                            </div>
                        </div>


                        <div class="d-flex flex-column align-items-end">
            <span class="fw-semibold">
                €<fmt:formatNumber value="${item.product.price * item.quantity}" type="number"
                                   minFractionDigits="2"/>
            </span>
                            <form action="${ctx}/cart/remove/${item.product.id}" method="post" class="mt-1">
                                <button type="submit" class="btn btn-sm btn-outline-danger"
                                        title="<spring:message code="cart.removeItem"/>">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </form>
                        </div>
                    </li>
                </c:forEach>
            </ul>


            <div class="d-flex justify-content-between fw-bold mb-3">
                <span>Total :</span>
                <span>€<fmt:formatNumber value="${cartTotal}" type="number" minFractionDigits="2"/></span>
            </div>

            <a href="${ctx}/orders/checkout" class="btn btn-dark w-100">
                <spring:message code="cart.checkout"/>
            </a>
        </c:if>

        <c:if test="${empty cartItems}">
            <div class="text-center my-auto py-5">
                <i class="bi bi-cart-x fs-1 text-muted mb-3"></i>
                <h5 class="text-muted mb-2"><spring:message code="cart.empty.message"/></h5>
                <p class="text-muted mb-4"><spring:message code="cart.empty.seeProducts"/></p>
                <a href="${ctx}/products" class="btn btn-outline-dark">
                    <i class="bi bi-bag"></i> <spring:message code="cart.empty.addProducts"/>
                </a>
            </div>
        </c:if>

    </div>
</div>

<nav class="navbar navbar-expand-md bg-light border-bottom">
    <div class="container">

        <a class="navbar-brand d-flex align-items-center gap-2" href="<spring:url value="/home"/>"
           aria-label="<spring:message code="navbar.home"/>">
            <img src='<spring:url value="/images/logo.png"/>' alt="Logo" height="65"/>
            <span class="fw-semibold">Book Store</span>
        </a>


        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar"
                aria-controls="mainNavbar" aria-expanded="false" aria-label="Ouvrir le menu">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="mainNavbar">
            <!-- Centre : Home + Produits -->
            <ul class="navbar-nav mx-auto mb-2 mb-md-0">
                <li class="nav-item">
                    <a class="nav-link" href="<spring:url value="/home"/>"><i class="bi bi-house-door"></i>
                        <spring:message code="navbar.home"/> </a>
                </li>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="productsDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-bag"></i> <spring:message code="navbar.products"/>
                    </a>

                    <ul class="dropdown-menu" aria-labelledby="productsDropdown">
                        <c:forEach var="c" items="${categoriesNames}">
                            <li>
                                <a class="dropdown-item" href="${ctx}/products/category/${c.id}">
                                    <c:out value="${c.name}"/>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </li>

            </ul>


            <ul class="navbar-nav ms-auto align-items-md-center gap-md-2">

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle d-flex align-items-center gap-1" href="#" id="langDropdown"
                       role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-globe2"></i> <span class="d-md-inline d-none"><spring:message
                            code="navbar.language"/></span>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="langDropdown">
                        <li><a class="dropdown-item" href="${localeFr}"><spring:message code="locale.button.fr"/></a>
                        </li>
                        <li><a class="dropdown-item" href="${localeEn}"><spring:message code="locale.button.en"/></a>
                        </li>

                    </ul>
                </li>
                <sec:authorize access="!isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link d-flex align-items-center gap-1" href="<spring:url value="/login"/>">
                            <i class="bi bi-person-circle"></i>
                            <span class="d-md-inline d-none"><spring:message code="navbar.account"/></span>
                        </a>
                    </li>
                </sec:authorize>

                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal.username" var="username"/>

                    <li class="nav-item">
                <span class="nav-link d-flex align-items-center gap-1">
                  <i class="bi bi-person-circle"></i>
                  <span class="d-md-inline d-none"><spring:message code="navbar.welcomeMessage"/> ${username}</span>
                </span>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link d-flex align-items-center gap-1" href="<spring:url value="/authenticated"/>">
                            <i class="bi bi-gear"></i>
                            <span class="d-md-inline d-none"><spring:message code="navbar.myaccount"/></span>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link d-flex align-items-center gap-1" href="<spring:url value="/logout"/>">
                            <i class="bi bi-box-arrow-right"></i>
                            <span class="d-md-inline d-none"><spring:message code="navbar.logout"/></span>
                        </a>
                    </li>
                </sec:authorize>

                <sec:authorize access="hasRole('ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link d-flex align-items-center gap-1"
                           href="<spring:url value='/admin'/>">
                            <i class="bi bi-ticket-perforated"></i>
                            <span class="d-md-inline d-none">
                                    <spring:message code="navbar.admin.promo"/>
                                </span>
                        </a>
                    </li>
                </sec:authorize>

                <li class="nav-item">
                    <a class="nav-link position-relative d-flex align-items-center gap-1"
                       href="#"
                       data-bs-toggle="offcanvas"
                       data-bs-target="#cartOffcanvas"
                       aria-controls="cartOffcanvas">
                        <i class="bi bi-cart3"></i>
                        <span class="d-md-inline d-none"><spring:message code="navbar.cart"/></span>
                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-dark">
            <c:out value="${cartCount != null ? cartCount : 0}"/>
        </span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>


<main>
    <tiles:insertAttribute name="main-content"/>

</main>


<footer class="mt-auto py-4 bg-light border-top">
    <div class="container">
        <div class="row">

            <div class="col-md-4 mb-3">
                <a href="<spring:url value="/home"/>" class="d-flex align-items-center gap-2 mb-2 text-decoration-none">
                    <img src='<spring:url value="/images/logo.png"/>' alt="Logo" height="36"/>
                    <span class="fw-semibold">BookStore</span>
                </a>
                <p class="text-muted small">
                    <spring:message code="footer.description"/>
                </p>
            </div>

            <div class="col-md-4 mb-3">
                <ul class="list-unstyled">
                    <li><a href="${ctx}/info/contact" class="text-muted text-decoration-none"><spring:message
                            code="footer.contact"/></a></li>
                    <li><a href="${ctx}/info/privacy-policy" class="text-muted text-decoration-none"><spring:message
                            code="footer.privacy"/></a></li>
                    <li><a href="${ctx}/info/about-us" class="text-muted text-decoration-none"><spring:message
                            code="footer.about"/></a></li>
                </ul>
            </div>

            <!-- Réseaux sociaux -->
            <div class="col-md-4 mb-3">
                <div class="d-flex gap-3">
                    <h6 class="fw-bold text-dark"><spring:message code="footer.follow"/></h6>
                    <a href="#" class="text-muted fs-4"><i class="bi bi-facebook"></i></a>
                    <a href="#" class="text-muted fs-4"><i class="bi bi-instagram"></i></a>
                    <a href="#" class="text-muted fs-4"><i class="bi bi-twitter"></i></a>
                    <a href="#" class="text-muted fs-4"><i class="bi bi-youtube"></i></a>
                </div>
            </div>
        </div>

        <div class="border-top pt-3 mt-3 text-center small text-muted">
            <spring:message code="footer.copyright"/>.
        </div>
    </div>
</footer>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
