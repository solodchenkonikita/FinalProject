package com.epam.service.impl;

import com.epam.dao.MasterTimetableDao;
import com.epam.dao.impl.MasterTimetableDaoImpl;
import com.epam.entity.Timetable;
import com.epam.exception.DBException;
import com.epam.service.MasterTimetableManager;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.List;

public class MasterTimetableManagerImpl implements MasterTimetableManager {

    private final static Logger LOG = Logger.getLogger(MasterTimetableDaoImpl.class);

    MasterTimetableDao masterTimetableDao = new MasterTimetableDaoImpl();

    @Override
    public List<Timetable> getMasterTimetableByDate(Date date, int masterId) throws DBException {
        return masterTimetableDao.getMasterTimetableByDate(date, masterId);
    }

    @Override
    public boolean setBookingDone(int bookingId) {
        return masterTimetableDao.setBookingDone(bookingId);
    }
}
