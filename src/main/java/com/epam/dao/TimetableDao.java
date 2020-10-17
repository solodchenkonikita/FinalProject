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
    List<Timetable> getTimetable(Date date, Time time, int start, int limit) throws DBException;


    List<Date> getDates(Date date) throws DBException;


    List<Timetable> getTimetableByDateAndMasterId(Date date, int masterId, Time time, int start, int limit) throws DBException;

    int getTimetableByDateAndMasterIdCount(Date date, Time time, int masterId) throws DBException;

    List<Timetable> getTimetableByDateAndSortByMasterName(Date date, Time time, int start, int limit) throws DBException;

    int getTimetableByDateCount(Date date, Time time) throws DBException;

    List<Timetable> getTimetableByDateAndSortByMasterMark(Date date, Time time, int start, int limit) throws DBException;


    List<Timetable> getTimetableByDateAndService(Date date, int serviceId, Time time, int start, int limit) throws DBException;

    int getTimetableByDateAndServiceCount(Date date, Time time, int serviceId) throws DBException;

    int createBooking(Connection connection, int serviceId, int clientId) throws DBException;

    boolean addBookingToTimetable(Connection connection, int timetableId, int bookingId);


}
