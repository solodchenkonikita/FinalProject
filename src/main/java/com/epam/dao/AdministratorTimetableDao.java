package com.epam.dao;

import com.epam.entity.Timetable;
import com.epam.exception.DBException;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * Administrator timetable dao interface.
 *
 * @author Solodchenko Nikita
 *
 */
public interface AdministratorTimetableDao {

    /**
     * Get booked timetables by date.
     *
     * @param connection connection to database.
     * @param date specified date.
     * @param start start position in timetable.
     * @param limit limit for getting number of timetables.
     * @return list of timetables.
     */
    List<Timetable> getTimetableWithBookingByDate(Connection connection, Date date, int start, int limit) throws DBException;

    /**
     * Get booked timetables count.
     *
     * @param connection connection to database.
     * @param date specified date.
     * @return count of timetables.
     */
    int getTimetableWithBookingByDateCount(Connection connection, Date date) throws DBException;

    /**
     * Change booking status to paid.
     *
     * @param connection connection to database.
     * @param bookingId specified booking id.
     * @return true if change status, otherwise false.
     */
    boolean setBookingPaid(Connection connection, int bookingId);

    /**
     * Delete booking from timetable.
     *
     * @param connection connection to database.
     * @param bookingId specified booking id.
     * @return true if delete booking  from timetable, otherwise false.
     */
    boolean deleteBookingFromTimetable(Connection connection, int bookingId);

    /**
     * Delete booking.
     *
     * @param connection connection to database.
     * @param bookingId specified booking id.
     * @return true if delete booking, otherwise false.
     */
    boolean deleteBooking(Connection connection, int bookingId);

    /**
     * Get master free timetables by date.
     *
     * @param connection connection to database.
     * @param date specified date.
     * @param masterId specified master id.
     * @return list of timetables.
     */
    List<Timetable> getMasterFreeTimetableByDate(Connection connection, Date date, int masterId) throws DBException;

}
