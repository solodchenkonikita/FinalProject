package com.epam.service;

import com.epam.entity.Timetable;
import com.epam.exception.DBException;

import java.sql.Date;
import java.util.List;

/**
 * Master timetable service interface.
 *
 * @author Solodchenko Nikita
 *
 */
public interface MasterTimetableManager {

    /**
     * Get master timetables by date.
     *
     * @param date specified date.
     * @param masterId specified master id.
     * @return list of timetables.
     */
    List<Timetable> getMasterTimetableByDate(Date date, int masterId) throws DBException;

    /**
     * Change booking status to done.
     *
     * @param bookingId specified booking id.
     * @return true if change status, otherwise false.
     */
    boolean setBookingDone(int bookingId);
}
