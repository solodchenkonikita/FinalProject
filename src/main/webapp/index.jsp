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
    <h1 class="display-3 text-center"><fmt:message key="mainPage.title"/></h1>
    <p class="lead text-center"><fmt:message key="mainPage.title2"/></p>
    <p></p>
</div>

<div class="row marketing">
    <div class="col-lg-1"></div>
    <div class="col-lg-5">
        <h4><fmt:message key="mainPage.info"/></h4>
        <p><fmt:message key="mainPage.address"/><br>
            <fmt:message key="mainPage.phone"/>: +372 222 2222<br>
            <fmt:message key="mainPage.email"/>: info@gmail.com</p>
    </div>

    <div class="col-lg-5">
        <h4><fmt:message key="mainPage.openTime"/>:</h4>
        <p><fmt:message key="mainPage.days"/>: 10.00 - 20.00</p>
    </div>
</div>

<script src="js/bootstrap.min.js"></script>
<script src="js/jquery-3.2.1.min.js"></script>
</body>
</html>