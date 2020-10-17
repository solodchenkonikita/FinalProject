package com.epam.service;

import com.epam.entity.Timetable;
import com.epam.exception.DBException;

import java.sql.Date;
import java.util.List;

public interface AdministratorTimetableManager {

    List<Timetable> getTimetableWithBookingByDate(Date date, int start, int limit) throws DBException;

    int getTimetableWithBookingByDateCount(Date date) throws DBException;

    boolean setBookingPaid(int bookingId);

    boolean deleteBooking(int bookingId) throws DBException;

    List<Timetable> getMasterFreeTimetableByDate(Date date, int masterId) throws DBException;

    boolean changeClientBookingTime(int bookingId, int timetableId) throws DBException;
}
