<%@ include file="include/importTags.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<div class="container py-4">
    <h1 class="mb-4">
        <c:out value="${pageTitle != null ? pageTitle : 'Products'}"/>
        <c:if test="${not empty currentCategoryId}">
            <small class="text-muted">
                <c:forEach var="c" items="${categoriesNames}">
                    <c:if test="${c.id == currentCategoryId}">
                        <c:out value="${c.name}"/>
                    </c:if>
                </c:forEach>
            </small>
        </c:if>
    </h1>

    <c:choose>
        <c:when test="${empty products}">
            <div class="alert alert-info">No products to display.</div>
        </c:when>
        <c:otherwise>
            <div class="product-columns">
                <c:forEach var="p" items="${products}">
                    <a href="${ctx}/products/${p.id}"
                       class="product-card text-decoration-none text-dark js-open-product"
                       data-id="${p.id}"
                       data-name="${p.name}"
                       data-author="${p.author}"
                       data-desc="${fn:escapeXml(p.description)}"
                       data-price="${p.price}"
                       data-img="${ctx}/products/${p.id}/image">
                        <div class="card shadow-sm mb-4">
                            <img class="card-img-top"
                                 src="${ctx}/products/${p.id}/image"
                                 alt="${p.name}"
                                 onerror="this.style.display='none'"/>
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title mb-1">${p.name}</h5>
                                <p class="card-text text-muted small mb-3">
                                    <c:out value="${p.author}"/>
                                </p>

                                <p class="card-text text-muted small mb-3">
                                    <c:out value="${p.description}"/>
                                </p>
                                <div class="mt-auto d-flex justify-content-between align-items-center">
                                    <span class="fw-bold">${p.price} €</span>
                                </div>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<style>
    .product-columns {
        column-count: 3;
        column-gap: 1.5rem;
    }

    .product-card {
        display: inline-block;
        width: 100%;
    }

    @media (max-width: 992px) {
        .product-columns {
            column-count: 2;
        }
    }

    @media (max-width: 576px) {
        .product-columns {
            column-count: 1;
        }
    }

</style>

<div class="modal fade" id="productModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg modal-dialog-scrollable">
        <div class="modal-content">
            <div class="modal-header">
                <h5 id="pm-title" class="modal-title"></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <div class="row g-3">
                    <div class="col-md-5 d-flex justify-content-center align-items-start">
                        <img id="pm-img" alt="" class="img-fluid rounded border" style="max-height:360px;">
                    </div>
                    <div class="col-md-12">
                        <h6 id="pm-author" class="text-secondary"></h6>
                    </div>
                    <div class="col-md-7">
                        <p id="pm-desc" class="text-muted"></p>
                        <div class="d-flex align-items-center gap-3 mt-3">
                            <div id="pm-price" class="fw-bold fs-4"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <form id="pm-add-form" method="post" action="">
                    <div class="d-flex align-items-center gap-2">
                        <label for="quantity" class="form-label mb-0"><spring:message code="products.quantity"/> :</label>
                        <input type="number"
                               name="quantity"
                               id="quantity"
                               value="1"
                               min="1"
                               class="form-control"
                               style="width: 80px;" />

                        <button type="submit" class="btn btn-dark">
                            <i class="bi bi-cart-plus"></i> <spring:message code="products.addToCart"/>
                        </button>
                    </div>
                </form>

                <button class="btn btn-outline-secondary" data-bs-dismiss="modal"><spring:message code="products.close"/></button>
            </div>
        </div>
    </div>
</div>

<script>
    (function () {
        const modalEl = document.getElementById('productModal');
        const formEl = document.getElementById('pm-add-form');

        document.querySelectorAll('.js-open-product').forEach(a => {
            a.addEventListener('click', (e) => {
                if (e.metaKey || e.ctrlKey) return;
                e.preventDefault();

                const id = a.dataset.id;
                const name = a.dataset.name || '';
                const author = a.dataset.author || '';
                const desc = a.dataset.desc || '';
                const price = a.dataset.price || '';
                const img = a.dataset.img || '';

                document.getElementById('pm-title').textContent = name;
                document.getElementById('pm-author').textContent = author;
                document.getElementById('pm-desc').textContent = desc;
                document.getElementById('pm-price').textContent = price + ' €';
                document.getElementById('pm-img').src = img;

                formEl.action = '${ctx}/cart/add/' + id;

                const modal = bootstrap.Modal.getOrCreateInstance(modalEl);
                modal.show();
            });
        });
    })();
</script>