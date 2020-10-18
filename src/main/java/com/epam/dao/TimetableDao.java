package com.epam.dao;

import com.epam.entity.Timetable;
import com.epam.exception.DBException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Work with timetable in database
 *
 * @author Nikita Solodchenko
 */
public interface TimetableDao {

    /**
     * Add user to database.
     *
     * @return timetable from database with free slots.
     */
    List<Timetable> getTimetable(Connection connection, Date date, Time time, int start, int limit) throws DBException;

    List<Date> getDates(Connection connection, Date date) throws DBException;

    List<Timetable> getTimetableByDateAndMasterId(Connection connection, Date date, int masterId, Time time, int start, int limit) throws DBException;

    int getTimetableByDateAndMasterIdCount(Connection connection, Date date, Time time, int masterId) throws DBException;

    List<Timetable> getTimetableByDateAndSortByMasterName(Connection connection, Date date, Time time, int start, int limit) throws DBException;

    int getTimetableByDateCount(Connection connection, Date date, Time time) throws DBException;

    List<Timetable> getTimetableByDateAndSortByMasterMark(Connection connection, Date date, Time time, int start, int limit) throws DBException;

    List<Timetable> getTimetableByDateAndService(Connection connection, Date date, int serviceId, Time time, int start, int limit) throws DBException;

    int getTimetableByDateAndServiceCount(Connection connection, Date date, Time time, int serviceId) throws DBException;

    int createBooking(Connection connection, int serviceId, int clientId) throws DBException;

    boolean addBookingToTimetable(Connection connection, int timetableId, int bookingId);

}
