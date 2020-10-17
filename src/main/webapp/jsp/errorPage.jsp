<%@ page isErrorPage="true" %>
<%@ include file="/jspf/directive/page.jspf"%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="css/bootstrap.min.css"/>
</head>

<body>
<%@ include file="/jspf/header.jspf"%>


<div class="jumbotron">
    <h2 class="text-center">
        <fmt:message key="error.title"/>
    </h2>

    <c:if test="${not empty errorMessage}">
        <h3 class="text-center">${errorMessage}</h3>
    </c:if>
</div>


<script src="js/bootstrap.min.js"></script>
<script src="js/jquery-3.2.1.min.js"></script>
</body>
</html>