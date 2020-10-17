package com.epam.dao;

import com.epam.entity.Timetable;
import com.epam.exception.DBException;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public interface AdministratorTimetableDao {

    List<Timetable> getTimetableWithBookingByDate(Date date, int start, int limit) throws DBException;

    int getTimetableWithBookingByDateCount(Date date) throws DBException;

    boolean setBookingPaid(int bookingId);

    boolean deleteBookingFromTimetable(Connection connection, int bookingId);

    boolean deleteBooking(Connection connection, int bookingId);

    List<Timetable> getMasterFreeTimetableByDate(Date date, int masterId) throws DBException;

}
