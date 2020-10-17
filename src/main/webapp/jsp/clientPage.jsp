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

<div class="container ">
    <div class="tab-content ">
        <div class="tab-pane fade show active" id="lorem" role="tabpanel">
            <table class="table table-bordered table-striped table-hover text-center ">
                <thead>
                <tr>
                    <th><fmt:message key="table.day"/></th>
                    <th><fmt:message key="table.service"/></th>
                    <th><fmt:message key="table.startTime"/></th>
                    <th><fmt:message key="table.endTime"/></th>
                    <th><fmt:message key="registration.firstName"/></th>
                    <th><fmt:message key="registration.lastName"/></th>
                    <th><fmt:message key="table.progress"/></th>
                    <th><fmt:message key="table.payment"/></th>
                    <th><fmt:message key="table.mark"/></th>
                    <th><fmt:message key="table.comment"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="timetable" items="${timetables}">
                    <tr>
                        <td><custom:date date="${timetable.date}"/></td>
                        <td>${timetable.booking.service.serviceName}</td>
                        <user:table timetable="${timetable}"></user:table>
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
                                <c:when test="${timetable.booking.paymentStatus == 'confirmed'}">
                                    <fmt:message key="table.confirmed"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="table.notConfirmed"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${timetable.booking.mark == 0 && date > timetable.date}">
                                    <form action="controller" method="POST">
                                        <input type="hidden" name="command" value="clientPage"/>
                                        <input type="hidden" name="bookingId" value="${timetable.booking.id}"/>
                                        <input type="hidden" name="masterId" value="${timetable.master.id}"/>
                                        <div class="btn-group">
                                            <button type="button" class="btn btn-primary dropdown-toggle"
                                                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <fmt:message key="table.chooseMark"/>
                                            </button>
                                            <div class="dropdown-menu">
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="mark" id=12
                                                           value="1" required>
                                                    <label class="form-check-label" for=12>
                                                        1
                                                    </label>
                                                </div>
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="mark" id=22
                                                           value="2">
                                                    <label class="form-check-label" for=22>
                                                        2
                                                    </label>
                                                </div>
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="mark" id=32
                                                           value="3">
                                                    <label class="form-check-label" for=32>
                                                        3
                                                    </label>
                                                </div>
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="mark" id=42
                                                           value="4">
                                                    <label class="form-check-label" for=42>
                                                        4
                                                    </label>
                                                </div>
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="mark" id=52
                                                           value="5">
                                                    <label class="form-check-label" for=52>
                                                        5
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        <button class="btn btn-sm btn-success text-uppercase" type="submit"><fmt:message
                                                key="table.submit"/>
                                        </button>
                                    </form>
                                </c:when>
                                <c:when test="${timetable.booking.mark == 0 && date <= timetable.date}">
                                    <fmt:message key="table.markStatus"/>
                                </c:when>
                                <c:otherwise>
                                    ${timetable.booking.mark}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${empty timetable.booking.comment && date > timetable.date}">
                                    <form action="controller" method="POST">
                                        <input type="hidden" name="command" value="clientPage"/>
                                        <input type="hidden" name="bookingId" value="${timetable.booking.id}"/>
                                        <input type="text" name="comment"
                                               placeholder="<fmt:message key="table.comment"/>" maxlength="100"
                                               required autofocus/>
                                        <button class="btn btn-primary btn-block text-uppercase" type="submit">
                                            <fmt:message key="table.submit"/></button>
                                    </form>
                                </c:when>
                                <c:when test="${empty timetable.booking.comment && date <= timetable.date}">
                                    <fmt:message key="table.commentStatus"/>
                                </c:when>
                                <c:otherwise>
                                    ${timetable.booking.comment}
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

<nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center">
        <c:forEach var="page" items="${pageList}">
            <li class="page-item"><a class="page-link" href="controller?command=clientPage&page=${page}">${page}</a>
            </li>
        </c:forEach>
    </ul>
</nav>

<script src="js/popper.js"></script>
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>