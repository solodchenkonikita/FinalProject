package com.epam.dao;

import com.epam.entity.Timetable;
import com.epam.exception.DBException;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public interface MasterTimetableDao {

    List<Timetable> getMasterTimetableByDate(Connection connection, Date date, int masterId) throws DBException;

    boolean setBookingDone(Connection connection, int bookingId);
}
