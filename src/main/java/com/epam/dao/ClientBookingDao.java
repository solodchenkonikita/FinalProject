package com.epam.dao;

import com.epam.entity.Timetable;
import com.epam.exception.DBException;

import java.sql.Connection;
import java.util.List;

/**
 * Client booking dao interface.
 *
 * @author Solodchenko Nikita
 *
 */
public interface ClientBookingDao {

    /**
     * Get user booked timetables.
     *
     * @param connection connection to database.
     * @param userId specified user id.
     * @param start start position in timetable.
     * @param limit limit for getting number of timetables.
     * @return list of timetables.
     */
    List<Timetable> getTimetableWithUserBooking(Connection connection, int userId, int start, int limit) throws DBException;

    /**
     * Get user booked timetables count.
     *
     * @param connection connection to database.
     * @param clientId specified client id.
     * @return count of timetables.
     */
    int getUserBookingCount(Connection connection, int clientId) throws DBException;

    /**
     * Set mark in booking.
     *
     * @param connection connection to database.
     * @param bookingId specified booking id.
     * @param mark mark.
     * @return true if set mark, otherwise false.
     */
    boolean setMarkInBooking(Connection connection, int bookingId, int mark);

    /**
     * Get master marks.
     *
     * @param connection connection to database.
     * @param masterId specified master id.
     * @return list of marks.
     */
    List<Integer> masterMarks(Connection connection, int masterId) throws DBException;

    /**
     * Set mark to master.
     *
     * @param connection connection to database.
     * @param masterId specified master id.
     * @param mark mark.
     * @return true if set mark, otherwise false.
     */
    boolean setMarkToMaster(Connection connection, int masterId, double mark);

    /**
     * Set comment in booking.
     *
     * @param connection connection to database.
     * @param bookingId specified booking id.
     * @param comment comment.
     * @return true if set comment, otherwise false.
     */
    boolean setComment(Connection connection, int bookingId, String comment);
}
