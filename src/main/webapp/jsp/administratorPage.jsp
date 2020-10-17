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
    <a class="navbar-text"><fmt:message key="timetable.chooseDate"/></a>
    <form action="controller" method="GET">
        <input type="hidden" name="command" value="administratorPage"/>
        <div class="btn-group">
            <!-- Кнопка -->
            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown">
                <fmt:message key="timetable.chooseDate"/>
            </button>
            <!-- Меню -->
            <div class="dropdown-menu">
                <c:forEach var="date" items="${dateList}">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="date" id=${date} value=${date}>
                        <label class="form-check-label" for=${date}>
                                <custom:date date="${date}"/>
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
                    <th><fmt:message key="table.clientName"/></th>
                    <th><fmt:message key="table.masterName"/></th>
                    <th><fmt:message key="table.service"/></th>
                    <th><fmt:message key="table.progress"/></th>
                    <th><fmt:message key="table.payment"/></th>
                    <th><fmt:message key="table.changeTimeslot"/></th>
                    <th><fmt:message key="table.delete"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="timetable" items="${timetableList}">
                    <tr>
                        <td><custom:date date="${timetable.date}"/></td>
                        <td>${timetable.timeslot.startTime}</td>
                        <td>${timetable.timeslot.endTime}</td>
                        <td>${timetable.booking.client.firstName} ${timetable.booking.client.lastName}</td>
                        <td>${timetable.master.firstName} ${timetable.master.lastName}</td>
                        <td>${timetable.booking.service.serviceName}</td>
                        <td>
                            <c:choose>
                                <c:when test="${timetable.booking.progressStatus == 'done'}">
                                    <fmt:message key="table.done"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="table.inProgress"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${timetable.booking.paymentStatus == 'notConfirmed'}">
                                    <form action="controller" method="POST">
                                        <input type="hidden" name="command" value="administratorPage"/>
                                        <input type="hidden" name="action" value="change status"/>
                                        <input type="hidden" name="bookingId" value="${timetable.booking.id}"/>
                                        <button class="btn btn-primary btn-block text-uppercase" type="submit"><fmt:message key="table.confirmed"/>
                                        </button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="table.confirmed"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <div class="container">
                                <form action="controller" method="POST">
                                    <input type="hidden" name="command" value="administratorPage"/>
                                    <input type="hidden" name="action" value="changeSlot"/>
                                    <input type="hidden" name="bookingId" value="${timetable.booking.id}"/>

                                    <div class="btn-group">
                                        <!-- Кнопка -->
                                        <button type="button" class="btn btn-secondary dropdown-toggle"
                                                data-toggle="dropdown">
                                            <fmt:message key="table.timeslot"/>
                                        </button>
                                        <!-- Меню -->
                                        <div class="dropdown-menu">
                                            <c:forEach var="timetableMap" items="${mastersFreeTimetable}">
                                                <c:if test="${timetableMap.key == timetable.master.id}">
                                                    <c:forEach var="timetableMaster" items="${timetableMap.value}">

                                                        <div class="form-check">
                                                            <input class="form-check-input" type="radio"
                                                                   name="newTimetableId" id=${timetable.id + timetableMaster.id}2
                                                                   value="${timetableMaster.id}" required>
                                                            <label class="form-check-label" for=${timetable.id + timetableMaster.id}2>
                                                                    ${timetableMaster.timeslot.startTime}
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
                        <td>
                            <form action="controller" method="POST">
                                <input type="hidden" name="command" value="administratorPage"/>
                                <input type="hidden" name="action" value="delete"/>
                                <input type="hidden" name="bookingId" value="${timetable.booking.id}"/>
                                <button class="btn btn-primary btn-block text-uppercase" type="submit"><fmt:message key="table.delete"/>
                                </button>
                            </form>
                        </td>
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