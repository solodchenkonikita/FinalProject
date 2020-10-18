package com.epam.service.impl;

import com.epam.dao.AdministratorTimetableDao;
import com.epam.dao.TimetableDao;
import com.epam.dao.impl.AdministratorTimetableDaoImpl;
import com.epam.dao.impl.TimetableDaoImpl;
import com.epam.entity.Timetable;
import com.epam.exception.DBException;
import com.epam.exception.Messages;
import com.epam.service.AdministratorTimetableManager;
import com.epam.util.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AdministratorTimetableManagerImpl implements AdministratorTimetableManager {

    private final static Logger LOG = Logger.getLogger(AdministratorTimetableManagerImpl.class);

    AdministratorTimetableDao administratorTimetableDao = new AdministratorTimetableDaoImpl();
    TimetableDao timetableDao = new TimetableDaoImpl();

    @Override
    public List<Timetable> getTimetableWithBookingByDate(Date date, int start, int limit) throws DBException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            return administratorTimetableDao.getTimetableWithBookingByDate(connection, date, start, limit);
        } finally {
            close(connection);
        }
    }

    @Override
    public int getTimetableWithBookingByDateCount(Date date) throws DBException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            return administratorTimetableDao.getTimetableWithBookingByDateCount(connection, date);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean setBookingPaid(int bookingId) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            return administratorTimetableDao.setBookingPaid(connection, bookingId);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean deleteBooking(int bookingId) throws DBException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            if (!administratorTimetableDao.deleteBookingFromTimetable(connection, bookingId)) {
                LOG.error("Cannot delete booking from timetable");
                throw new DBException(Messages.ERR_CANNOT_DELETE_BOOKING);
            }
            if (!administratorTimetableDao.deleteBooking(connection, bookingId)) {
                LOG.error("Cannot delete booking");
                throw new DBException(Messages.ERR_CANNOT_DELETE_BOOKING);
            }
            connection.commit();
        } catch (SQLException | DBException ex) {
            rollback(connection);
            ex.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_DELETE_BOOKING);
        } finally {
            close(connection);
        }
        return true;
    }

    @Override
    public List<Timetable> getMasterFreeTimetableByDate(Date date, int masterId) throws DBException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            return administratorTimetableDao.getMasterFreeTimetableByDate(connection, date, masterId);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean changeClientBookingTime(int bookingId, int timetableId) throws DBException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            if (!administratorTimetableDao.deleteBookingFromTimetable(connection, bookingId)) {
                LOG.error("Cannot delete booking from timetable");
                throw new DBException(Messages.ERR_CANNOT_DELETE_BOOKING);
            }
            if (!timetableDao.addBookingToTimetable(connection, timetableId, bookingId)) {
                LOG.error("Cannot add booking to timetable");
                throw new DBException(Messages.ERR_CANNOT_ADD_BOOKING_TO_TIMETABLE);
            }
        } catch (SQLException | DBException ex) {
            rollback(connection);
            ex.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_DELETE_BOOKING);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close(connection);
        }
        return true;
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
            LOG.error("Cannot rollback");
        }
    }
}
