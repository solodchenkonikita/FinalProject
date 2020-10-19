package com.epam.service;

import com.epam.entity.Timetable;
import com.epam.exception.DBException;

import java.util.List;

/**
 * Client booking service interface.
 *
 * @author Solodchenko Nikita
 *
 */
public interface ClientTimetableManager {

    /**
     * Get user booked timetables.
     *
     * @param userId specified user id.
     * @param start start position in timetable.
     * @param limit limit for getting number of timetables.
     * @return list of timetables.
     */
    List<Timetable> getTimetableWithUserBooking(int userId, int start, int limit) throws DBException;

    /**
     * Get user booked timetables count.
     *
     * @param clientId specified client id.
     * @return count of timetables.
     */
    int getUserBookingCount(int clientId) throws DBException;

    /**
     * Grade master.
     *
     * @param bookingId specified booking id.
     * @param mark mark.
     * @param masterId specified master id.
     * @return true if grade master, otherwise false.
     */
    boolean gradeMaster(int bookingId, int mark, int masterId) throws DBException;

    /**
     * Set comment in booking.
     *
     * @param bookingId specified booking id.
     * @param comment comment.
     * @return true if set comment, otherwise false.
     */
    boolean setComment(int bookingId, String comment);

}
