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
        <input type="hidden" name="command" value="masterPage"/>
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
                    <th><fmt:message key="registration.firstName"/></th>
                    <th><fmt:message key="registration.lastName"/></th>
                    <th><fmt:message key="table.service"/></th>
                    <th><fmt:message key="table.payment"/></th>
                    <th><fmt:message key="table.progress"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="timetable" items="${timetableList}">
                    <tr>
                        <td><custom:date date="${timetable.date}"/></td>
                        <td>${timetable.timeslot.startTime}</td>
                        <td>${timetable.timeslot.endTime}</td>
                        <td>${timetable.booking.client.firstName}</td>
                        <td>${timetable.booking.client.lastName}</td>
                        <td>${timetable.booking.service.serviceName}</td>
                        <td>
                            <c:choose>
                                <c:when test="${timetable.booking.paymentStatus == 'confirmed'}">
                                    <fmt:message key="table.confirmed"/>
                                </c:when>
                                <c:when test="${timetable.booking.paymentStatus == 'notConfirmed'}">
                                    <fmt:message key="table.notConfirmed"/>
                                </c:when>
                                <c:otherwise>
                                    ${timetable.booking.paymentStatus}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${timetable.booking.progressStatus == 'inProgress' && date == timetable.date}">
                                    <form action="controller" method="POST">
                                        <input type="hidden" name="command" value="masterPage"/>
                                        <input type="hidden" name="bookingId" value="${timetable.booking.id}"/>
                                        <input type="hidden" name="status" value="done"/>
                                        <button class="btn btn-primary btn-block text-uppercase" type="submit"><fmt:message key="table.done"/></button>
                                    </form>
                                </c:when>
                                <c:when test="${timetable.booking.progressStatus == 'done'}">
                                    <fmt:message key="table.done"/>
                                </c:when>
                                <c:otherwise>
                                    ${timetable.booking.progressStatus}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>


<script src="js/popper.js"></script>
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
