package com.epam.dao;

import com.epam.entity.Timetable;
import com.epam.exception.DBException;

import java.sql.Connection;
import java.util.List;

public interface ClientBookingDao {

    List<Timetable> getTimetableWithUserBooking(Connection connection, int userId, int start, int limit) throws DBException;

    int getUserBookingCount(Connection connection, int clientId) throws DBException;

    boolean setMarkInBooking(Connection connection, int bookingId, int mark);

    List<Integer> masterMarks(Connection connection, int masterId) throws DBException;

    boolean setMarkToMaster(Connection connection, int masterId, double mark);

    boolean setComment(Connection connection, int bookingId, String comment);
}
