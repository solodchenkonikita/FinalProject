package com.epam.dao;

import com.epam.entity.Timetable;
import com.epam.exception.DBException;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * Master timetable dao interface.
 *
 * @author Solodchenko Nikita
 *
 */
public interface MasterTimetableDao {

    /**
     * Get master timetables by date.
     *
     * @param connection connection to database.
     * @param date specified date.
     * @param masterId specified master id.
     * @return list of timetables.
     */
    List<Timetable> getMasterTimetableByDate(Connection connection, Date date, int masterId) throws DBException;

    /**
     * Change booking status to done.
     *
     * @param connection connection to database.
     * @param bookingId specified booking id.
     * @return true if change status, otherwise false.
     */
    boolean setBookingDone(Connection connection, int bookingId);
}
