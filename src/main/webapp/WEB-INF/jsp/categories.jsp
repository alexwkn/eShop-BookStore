<%@ include file="include/importTags.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<ul>
    <c:forEach var="c" items="${categories}">
        <li><c:out value="${c.name}"/> (id: <c:out value="${c.id}"/>)</li>
    </c:forEach>
</ul>

</body>
</html>