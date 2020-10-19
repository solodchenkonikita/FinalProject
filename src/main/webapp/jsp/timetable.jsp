<%@ taglib prefix="user" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/CustomTag.tld" prefix="custom"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="css/bootstrap.min.css"/>
</head>

<body>
<%@ include file="/jspf/header.jspf" %>

<div class="container">
    <a class="navbar-text"><fmt:message key="timetable.sort"/></a>
    <form action="controller" method="GET">
        <input type="hidden" name="command" value="timetable"/>
        <div class="btn-group">
            <!-- Кнопка -->
            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown">
                <fmt:message key="timetable.chooseDate"/>
            </button>
            <!-- Меню -->
            <div class="dropdown-menu">
                <c:forEach var="date" items="${dateList}">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="date" id=${date} value="${date}" required autofocus>
                        <label class="form-check-label" for=${date}>
                                <custom:date date="${date}"/>
                        </label>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="btn-group">
            <!-- Кнопка -->
            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown">
                <fmt:message key="timetable.sortOption"/>
            </button>
            <!-- Меню -->
            <div class="dropdown-menu">
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="sortOption" id="masters" value="masters">
                    <label class="form-check-label" for="masters">
                        <fmt:message key="timetable.sortByMaster"/>
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="sortOption" id="marks" value="marks">
                    <label class="form-check-label" for="marks">
                        <fmt:message key="timetable.sortByMark"/>
                    </label>
                </div>
            </div>
        </div>
        <button class="btn btn-sm btn-success text-uppercase" type="submit"><fmt:message key="table.submit"/></button>
    </form>
</div>

<div class="container">
    <a class="navbar-text"><fmt:message key="timetable.filterMaster"/></a>
    <form action="controller" method="GET">
        <input type="hidden" name="command" value="timetable"/>
        <div class="btn-group">
            <!-- Кнопка -->
            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown">
                <fmt:message key="timetable.chooseDate"/>
            </button>
            <!-- Меню -->
            <div class="dropdown-menu">
                <c:forEach var="date" items="${dateList}">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="date" id=${date}2 value="${date}" required autofocus>
                        <label class="form-check-label" for=${date}2>
                            <custom:date date="${date}"/>
                        </label>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="btn-group">
            <!-- Кнопка -->
            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown">
                <fmt:message key="timetable.chooseMaster"/>
            </button>
            <!-- Меню -->
            <div class="dropdown-menu">
                <c:forEach var="master" items="${mastersList}">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="filterByMaster" id=${master.id}0
                               value=${master.id}>
                        <label class="form-check-label" for=${master.id}0>
                                ${master.firstName} ${master.lastName}
                        </label>
                    </div>
                </c:forEach>
            </div>
        </div>
        <button class="btn btn-sm btn-success text-uppercase" type="submit"><fmt:message key="table.submit"/></button>
    </form>
</div>

<div class="container">
    <a class="navbar-text"><fmt:message key="timetable.filterService"/></a>
    <form action="controller" method="GET">
        <input type="hidden" name="command" value="timetable"/>
        <div class="btn-group">
            <!-- Кнопка -->
            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown">
                <fmt:message key="timetable.chooseDate"/>
            </button>
            <!-- Меню -->
            <div class="dropdown-menu">
                <c:forEach var="date" items="${dateList}">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="date" id=${date}2 value="${date}" required autofocus>
                        <label class="form-check-label" for=${date}2>
                            <custom:date date="${date}"/>
                        </label>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="btn-group">
            <!-- Кнопка -->
            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown">
                <fmt:message key="timetable.chooseService"/>
            </button>
            <!-- Меню -->
            <div class="dropdown-menu">
                <c:forEach var="service" items="${serviceList}">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="filterByService" id=${service.id}0
                               value=${service.id}>
                        <label class="form-check-label" for=${service.id}0>
                                ${service.serviceName}
                        </label>
                    </div>
                </c:forEach>
            </div>
        </div>
        <button class="btn btn-sm btn-success text-uppercase" type="submit"><fmt:message key="table.submit"/></button>
    </form>
</div>

<div class="container ">
    <div class="tab-content ">
        <div class="tab-pane fade show active" id="lorem" role="tabpanel">
            <table class="table table-bordered table-striped table-hover w-auto h-auto text-center ">
                <thead>
                <tr>
                    <th><fmt:message key="table.day"/></th>
                    <th><fmt:message key="table.startTime"/></th>
                    <th><fmt:message key="table.endTime"/></th>
                    <th><fmt:message key="registration.firstName"/></th>
                    <th><fmt:message key="registration.lastName"/></th>

                    <c:if test="${not empty user && user.role == 'client'}">
                        <th><fmt:message key="table.booking"/></th>
                    </c:if>


                </tr>
                </thead>
                <tbody>
                <c:forEach var="timetable" items="${timetableList}">
                    <tr>
                        <td><custom:date date="${timetable.date}"/></td>
                        <user:table timetable="${timetable}"></user:table>
                        <c:if test="${not empty user && user.role == 'client'}">
                            <td>
                                <div class="container">
                                    <form action="controller" method="POST">
                                        <input type="hidden" name="command" value="booking"/>
                                        <input type="hidden" name="timetableId" value="${timetable.id}"/>

                                        <div class="btn-group">
                                            <!-- Кнопка -->
                                            <button type="button" class="btn btn-secondary dropdown-toggle"
                                                    data-toggle="dropdown">
                                                <fmt:message key="timetable.chooseService"/>
                                            </button>
                                            <!-- Меню -->
                                            <div class="dropdown-menu">
                                                <c:forEach var="serviceMap" items="${servicesByMaster}">
                                                    <c:if test="${serviceMap.key == timetable.master.id}">
                                                        <c:forEach var="serviceMaster" items="${serviceMap.value}">

                                                                <div class="form-check">
                                                                    <input class="form-check-input" type="radio"
                                                                           name="serviceId" id=${timetable.id + serviceMaster.id}2
                                                                           value="${serviceMaster.id}" required>
                                                                    <label class="form-check-label" for=${timetable.id + serviceMaster.id}2>
                                                                            ${serviceMaster.serviceName}
                                                                    </label>
                                                                </div>

                                                        </c:forEach>
                                                    </c:if>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <button class="btn btn-sm btn-success text-uppercase" type="submit"><fmt:message key="table.submit"/>
                                        </button>
                                    </form>
                                </div>
                            </td>
                        </c:if>
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
            <li class="page-item"><a class="page-link" href="${query}&page=${page}">${page}</a></li>
        </c:forEach>
    </ul>
</nav>

<script src="js/popper.js"></script>
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
