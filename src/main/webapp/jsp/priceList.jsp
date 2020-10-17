<%@ include file="/jspf/directive/page.jspf" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="css/bootstrap.min.css"/>
</head>

<body>
<%@ include file="/jspf/header.jspf" %>


<div class="right_bar ">
    <div class="tab-content ">
        <div class="tab-pane fade show active container" id="lorem" role="tabpanel">
            <table class="table table-bordered table-striped table-hover w-auto h-auto text-center">
                <thead>
                <tr>
                    <th><fmt:message key="priseList.service"/></th>
                    <th><fmt:message key="priseList.price"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="service" items="${serviceList}">
                    <tr>
                        <td>${service.serviceName}</td>
                        <td>${service.price}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
        <c:forEach var="page" items="${pageList}">
            <li class="page-item"><a class="page-link" href="controller?command=priceList&page=${page}">${page}</a></li>
        </c:forEach>
    </ul>
</nav>

<script src="js/jquery-3.2.1.min.js"></script>
</body>
</html>
