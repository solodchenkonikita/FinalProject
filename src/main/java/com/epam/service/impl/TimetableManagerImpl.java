package com.epam.service.impl;

import com.epam.dao.TimetableDao;
import com.epam.dao.impl.TimetableDaoImpl;
import com.epam.entity.Timetable;
import com.epam.exception.DBException;
import com.epam.exception.Messages;
import com.epam.service.TimetableManager;
import com.epam.util.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class TimetableManagerImpl implements TimetableManager {

    private final static Logger LOG = Logger.getLogger(TimetableManagerImpl.class);

    TimetableDao timetableDao = new TimetableDaoImpl();

    @Override
    public List<Timetable> getTimetable(Date date, Time time, int start, int limit) throws DBException {
        return timetableDao.getTimetable(date, time, start, limit);
    }

    @Override
    public List<Date> getDates(Date date) throws DBException {
        return timetableDao.getDates(date);
    }

    @Override
    public List<Timetable> getTimetableByDateAndMasterId(Date date, int id, Time time, int start, int limit) throws DBException {
        return timetableDao.getTimetableByDateAndMasterId(date, id, time, start, limit);
    }

    @Override
    public int getTimetableByDateAndMasterIdCount(Date date, Time time, int id) throws DBException {
        return timetableDao.getTimetableByDateAndMasterIdCount(date, time, id);
    }

    @Override
    public List<Timetable> getTimetableByDateAndSortByMasterName(Date date, Time time, int start, int limit) throws DBException {
        return timetableDao.getTimetableByDateAndSortByMasterName(date, time, start, limit);
    }

    @Override
    public int getTimetableByDateCount(Date date, Time time) throws DBException {
        return timetableDao.getTimetableByDateCount(date, time);
    }

    @Override
    public List<Timetable> getTimetableByDateAndSortByMasterMark(Date date, Time time, int start, int limit) throws DBException {
        return timetableDao.getTimetableByDateAndSortByMasterMark(date, time, start, limit);
    }

    @Override
    public List<Timetable> getTimetableByDateAndService(Date date, int serviceId, Time time, int start, int limit) throws DBException {
        return timetableDao.getTimetableByDateAndService(date, serviceId, time, start, limit);
    }

    @Override
    public int getTimetableByDateAndServiceCount(Date date, Time time, int id) throws DBException {
        return timetableDao.getTimetableByDateAndServiceCount(date, time, id);
    }

    @Override
    public void createBooking(int serviceId, int clientId, int timetableId) throws DBException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            int bookingId = timetableDao.createBooking(connection, serviceId, clientId);
            if (bookingId < 0) {
                LOG.error("Cannot create booking");
                throw new DBException(Messages.ERR_CANNOT_CREATE_BOOKING);
            }
            boolean flag = timetableDao.addBookingToTimetable(connection, timetableId, bookingId);
            if (!flag) {
                LOG.error("Cannot add booking to timetable");
                throw new DBException(Messages.ERR_CANNOT_CREATE_BOOKING);
            }
            connection.commit();
        } catch (SQLException | DBException ex) {
            rollback(connection);
            ex.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_CREATE_BOOKING);
        } finally {
            close(connection);
        }
    }

    private void close(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error("Cannot rollback booking");
        }
    }
}
