package com.epam.service;

import com.epam.entity.Timetable;
import com.epam.exception.DBException;

import java.sql.Date;
import java.util.List;

public interface MasterTimetableManager {

    List<Timetable> getMasterTimetableByDate(Date date, int masterId) throws DBException;

    boolean setBookingDone(int bookingId);
}
