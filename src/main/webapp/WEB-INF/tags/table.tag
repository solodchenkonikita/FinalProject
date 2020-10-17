<%@tag pageEncoding="UTF-8"%>
<%@tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="timetable" required="true" type="com.epam.entity.Timetable"%>

<td>${timetable.timeslot.startTime}</td>
<td>${timetable.timeslot.endTime}</td>
<td>${timetable.master.firstName}</td>
<td>${timetable.master.lastName}</td>