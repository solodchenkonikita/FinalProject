package com.epam.service;

import com.epam.entity.Timetable;
import com.epam.exception.DBException;

import java.util.List;

public interface ClientTimetableManager {

    List<Timetable> getTimetableWithUserBooking(int userId, int start, int limit) throws DBException;

    int getUserBookingCount(int clientId) throws DBException;

    boolean gradeMaster(int bookingId, int mark, int masterId) throws DBException;

    boolean setComment(int bookingId, String comment);

}
