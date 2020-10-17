package com.epam.service;

import com.epam.entity.Timetable;
import com.epam.exception.DBException;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface TimetableManager {

    /**
     * Get free timetables from database.
     *
     * @return all timetables.
     */
    List<Timetable> getTimetable(Date date, Time time, int start, int limit) throws DBException;

    List<Date> getDates(Date date) throws DBException;

    List<Timetable> getTimetableByDateAndMasterId(Date date, int id, Time time, int start, int limit) throws DBException;

    int getTimetableByDateAndMasterIdCount(Date date, Time time, int id) throws DBException;

    List<Timetable> getTimetableByDateAndSortByMasterName(Date date, Time time, int start, int limit) throws DBException;

    int getTimetableByDateCount(Date date, Time time) throws DBException;

    List<Timetable> getTimetableByDateAndSortByMasterMark(Date date, Time time, int start, int limit) throws DBException;

    List<Timetable> getTimetableByDateAndService(Date date, int serviceId, Time time, int start, int limit) throws DBException;

    int getTimetableByDateAndServiceCount(Date date, Time time, int id) throws DBException;

    void createBooking(int serviceId, int clientId, int timetableId) throws DBException;

}
