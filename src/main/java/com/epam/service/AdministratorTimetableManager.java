package com.epam.service;

import com.epam.entity.Timetable;
import com.epam.exception.DBException;

import java.sql.Date;
import java.util.List;

/**
 * Administrator timetable service interface.
 *
 * @author Solodchenko Nikita
 *
 */
public interface AdministratorTimetableManager {

    /**
     * Get booked timetables by date.
     *
     * @param date specified date.
     * @param start start position in timetable.
     * @param limit limit for getting number of timetables.
     * @return list of timetables.
     */
    List<Timetable> getTimetableWithBookingByDate(Date date, int start, int limit) throws DBException;

    /**
     * Get booked timetables count.
     *
     * @param date specified date.
     * @return count of timetables.
     */
    int getTimetableWithBookingByDateCount(Date date) throws DBException;

    /**
     * Change booking status to paid.
     *
     * @param bookingId specified booking id.
     * @return true if change status, otherwise false.
     */
    boolean setBookingPaid(int bookingId);

    /**
     * Delete booking.
     *
     * @param bookingId specified booking id.
     * @return true if delete booking  from timetable, otherwise false.
     */
    boolean deleteBooking(int bookingId) throws DBException;

    /**
     * Get master free timetables by date.
     *
     * @param date specified date.
     * @param masterId specified master id.
     * @return list of timetables.
     */
    List<Timetable> getMasterFreeTimetableByDate(Date date, int masterId) throws DBException;

    /**
     * Change booking time.
     *
     * @param bookingId specified booking id.
     * @param timetableId specified timetable id.
     * @return true if change booking time, otherwise false.
     */
    boolean changeClientBookingTime(int bookingId, int timetableId) throws DBException;
}
