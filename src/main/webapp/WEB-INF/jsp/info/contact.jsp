<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container my-5">
    <h1 class="mb-4"><spring:message code="contact.title"/></h1>

    <div class="row g-4">
        <div class="col-md-6">
            <h2 class="h5"><spring:message code="contact.subtitle"/></h2>
            <p class="text-muted"><spring:message code="contact.text"/></p>

            <div class="mb-3">
                <label for="name" class="form-label"><spring:message code="contact.form.name"/></label>
                <input id="name" name="name" type="text" class="form-control" required/>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label"><spring:message code="contact.form.email"/></label>
                <input id="email" name="email" type="email" class="form-control" required/>
            </div>

            <div class="mb-3">
                <label for="message" class="form-label"><spring:message code="contact.form.message"/></label>
                <textarea id="message" name="message" class="form-control" rows="4" required></textarea>
            </div>

            <button type="submit" class="btn btn-primary">
                <spring:message code="contact.form.submit"/>
            </button>
        </div>

        <div class="col-md-6">
            <h2 class="h5"><spring:message code="contact.details"/></h2>
            <p class="text-muted"><spring:message code="contact.details.text"/></p>
            <ul class="list-unstyled">
                <li><strong><spring:message code="contact.phone"/>:</strong> +32 123 456 789</li>
                <li><strong>Email:</strong> contact@bookstore.com</li>
                <li><strong><spring:message code="contact.address"/>:</strong> Rue de l’Innovation 42, 1000 Brussels, Belgium</li>
            </ul>
        </div>
    </div>
</div>
