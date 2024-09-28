<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</head>
<body>
<c:if test="${not empty cookie.emailCookie}">
    <p>User Email:, <c:out value="${cookie.emailCookie.value}" />!</p>
</c:if>

<h1>List of albums</h1>
<p>
    <a href="download?action=checkUser&amp;productCode=8601">
        86 (the band) - True Life Songs and Pictures
    </a><br>
    <a href="download?action=checkUser&amp;productCode=pf01">
        Paddlefoot The First CD
    </a><br>
    <a href="download?action=checkUser&amp;productCode=pf02">
        Paddlefoot The Second CD
    </a><br>
    <a href="download?action=checkUser&amp;productCode=jr01">
        Joe Rut Genuine Wood Grained Finish
    </a>
</p>
</body>
</html>
