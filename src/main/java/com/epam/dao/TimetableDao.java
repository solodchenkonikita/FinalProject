package com.epam.dao;

import com.epam.entity.Timetable;
import com.epam.exception.DBException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Timetable dao interface.
 *
 * @author Nikita Solodchenko
 */
public interface TimetableDao {

    /**
     * Get timetables by date.
     *
     * @param connection connection to database.
     * @param date specified date.
     * @param time specified time.
     * @param start start position in timetable.
     * @param limit limit for getting number of timetables.
     * @return list of timetables.
     */
    List<Timetable> getTimetable(Connection connection, Date date, Time time, int start, int limit) throws DBException;

    /**
     * Get dates.
     *
     * @param connection connection to database.
     * @param date specified date.
     * @return list of dates.
     */
    List<Date> getDates(Connection connection, Date date) throws DBException;

    /**
     * Get timetables by date and master id.
     *
     * @param connection connection to database.
     * @param date specified date.
     * @param time specified time.
     * @param masterId specified master id.
     * @param start start position in timetable.
     * @param limit limit for getting number of timetables.
     * @return list of timetables.
     */
    List<Timetable> getTimetableByDateAndMasterId(Connection connection, Date date, int masterId, Time time, int start, int limit) throws DBException;

    /**
     * Get timetables by date and master id count.
     *
     * @param connection connection to database.
     * @param date specified date.
     * @param time specified time.
     * @param masterId specified master id.
     * @return count of timetables.
     */
    int getTimetableByDateAndMasterIdCount(Connection connection, Date date, Time time, int masterId) throws DBException;

    /**
     * Get timetables by date and sort by master name.
     *
     * @param connection connection to database.
     * @param date specified date.
     * @param time specified time.
     * @param start start position in timetable.
     * @param limit limit for getting number of timetables.
     * @return list of timetables.
     */
    List<Timetable> getTimetableByDateAndSortByMasterName(Connection connection, Date date, Time time, int start, int limit) throws DBException;

    /**
     * Get timetables by date and sort by master name count.
     *
     * @param connection connection to database.
     * @param date specified date.
     * @param time specified time.
     * @return count of timetables.
     */
    int getTimetableByDateCount(Connection connection, Date date, Time time) throws DBException;

    /**
     * Get timetables by date and sort by master mark.
     *
     * @param connection connection to database.
     * @param date specified date.
     * @param time specified time.
     * @param start start position in timetable.
     * @param limit limit for getting number of timetables.
     * @return list of timetables.
     */
    List<Timetable> getTimetableByDateAndSortByMasterMark(Connection connection, Date date, Time time, int start, int limit) throws DBException;

    /**
     * Get timetables by date and service.
     *
     * @param connection connection to database.
     * @param date specified date.
     * @param time specified time.
     * @param serviceId specified service id.
     * @param start start position in timetable.
     * @param limit limit for getting number of timetables.
     * @return list of timetables.
     */
    List<Timetable> getTimetableByDateAndService(Connection connection, Date date, int serviceId, Time time, int start, int limit) throws DBException;

    /**
     * Get timetables by date and service count.
     *
     * @param connection connection to database.
     * @param date specified date.
     * @param time specified time.
     * @param serviceId specified service id.
     * @return count of timetables.
     */
    int getTimetableByDateAndServiceCount(Connection connection, Date date, Time time, int serviceId) throws DBException;

    /**
     * Create booking.
     *
     * @param connection connection to database.
     * @param serviceId specified service id.
     * @param clientId specified client id.
     * @return booking id.
     */
    int createBooking(Connection connection, int serviceId, int clientId) throws DBException;

    /**
     * Add booking booking to timetable.
     *
     * @param connection connection to database.
     * @param timetableId specified timetable id.
     * @param bookingId specified booking id.
     * @return true if add booking, otherwise false.
     */
    boolean addBookingToTimetable(Connection connection, int timetableId, int bookingId);

}
