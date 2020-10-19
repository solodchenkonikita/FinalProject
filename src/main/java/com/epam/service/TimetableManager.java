package com.epam.service;

import com.epam.entity.Timetable;
import com.epam.exception.DBException;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Timetable service interface.
 *
 * @author Nikita Solodchenko
 */
public interface TimetableManager {

    /**
     * Get timetables by date.
     *
     * @param date specified date.
     * @param time specified time.
     * @param start start position in timetable.
     * @param limit limit for getting number of timetables.
     * @return list of timetables.
     */
    List<Timetable> getTimetable(Date date, Time time, int start, int limit) throws DBException;

    /**
     * Get dates.
     *
     * @param date specified date.
     * @return list of dates.
     */
    List<Date> getDates(Date date) throws DBException;

    /**
     * Get timetables by date and master id.
     *
     * @param date specified date.
     * @param time specified time.
     * @param id specified master id.
     * @param start start position in timetable.
     * @param limit limit for getting number of timetables.
     * @return list of timetables.
     */
    List<Timetable> getTimetableByDateAndMasterId(Date date, int id, Time time, int start, int limit) throws DBException;

    /**
     * Get timetables by date and master id count.
     *
     * @param date specified date.
     * @param time specified time.
     * @param id specified master id.
     * @return count of timetables.
     */
    int getTimetableByDateAndMasterIdCount(Date date, Time time, int id) throws DBException;

    /**
     * Get timetables by date and sort by master name.
     *
     * @param date specified date.
     * @param time specified time.
     * @param start start position in timetable.
     * @param limit limit for getting number of timetables.
     * @return list of timetables.
     */
    List<Timetable> getTimetableByDateAndSortByMasterName(Date date, Time time, int start, int limit) throws DBException;

    /**
     * Get timetables by date and sort by master name count.
     *
     * @param date specified date.
     * @param time specified time.
     * @return count of timetables.
     */
    int getTimetableByDateCount(Date date, Time time) throws DBException;

    /**
     * Get timetables by date and sort by master mark.
     *
     * @param date specified date.
     * @param time specified time.
     * @param start start position in timetable.
     * @param limit limit for getting number of timetables.
     * @return list of timetables.
     */
    List<Timetable> getTimetableByDateAndSortByMasterMark(Date date, Time time, int start, int limit) throws DBException;

    /**
     * Get timetables by date and service.
     *
     * @param date specified date.
     * @param time specified time.
     * @param serviceId specified service id.
     * @param start start position in timetable.
     * @param limit limit for getting number of timetables.
     * @return list of timetables.
     */
    List<Timetable> getTimetableByDateAndService(Date date, int serviceId, Time time, int start, int limit) throws DBException;

    /**
     * Get timetables by date and service count.
     *
     * @param date specified date.
     * @param time specified time.
     * @param id specified service id.
     * @return count of timetables.
     */
    int getTimetableByDateAndServiceCount(Date date, Time time, int id) throws DBException;

    /**
     * Create booking.
     *
     * @param serviceId specified service id.
     * @param clientId specified client id.
     * @return booking id.
     */
    void createBooking(int serviceId, int clientId, int timetableId) throws DBException;

}
