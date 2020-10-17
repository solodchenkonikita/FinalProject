package com.epam.service.impl;

import com.epam.dao.ClientBookingDao;
import com.epam.dao.impl.ClientBookingDaoImpl;
import com.epam.entity.Timetable;
import com.epam.exception.DBException;
import com.epam.exception.Messages;
import com.epam.service.ClientTimetableManager;
import com.epam.util.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClientTimetableManagerImpl implements ClientTimetableManager {

    private final static Logger LOG = Logger.getLogger(ClientTimetableManagerImpl.class);

    ClientBookingDao clientBookingDao = new ClientBookingDaoImpl();


    @Override
    public List<Timetable> getTimetableWithUserBooking(int userId, int start, int limit) throws DBException {
        return clientBookingDao.getTimetableWithUserBooking(userId, start, limit);
    }

    @Override
    public int getUserBookingCount(int clientId) throws DBException {
        return clientBookingDao.getUserBookingCount(clientId);
    }

    @Override
    public boolean gradeMaster(int bookingId, int mark, int masterId) throws DBException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            if (!clientBookingDao.setMarkInBooking(connection, bookingId, mark)) {
                LOG.error("Cannot set mark in booking");
                throw new DBException(Messages.ERR_CANNOT_GRADE_MASTER);
            }
            List<Integer> marks = clientBookingDao.masterMarks(connection, masterId);
            double averageMark = mark;
            LOG.trace("Mark = " + averageMark);
            int count = marks.size();
            if (count != 0) {
                int markSum = 0;
                for (int i : marks) {
                    markSum += i;
                }
                averageMark = (markSum + (double) mark) / (count + 1);
            }
            LOG.trace("New mark = " + averageMark);
            if (!clientBookingDao.setMarkToMaster(connection, masterId, averageMark)) {
                LOG.error("Cannot set mark to master");
                throw new DBException(Messages.ERR_CANNOT_GRADE_MASTER);
            }
            connection.commit();
        } catch (SQLException | DBException ex) {
            rollback(connection);
            ex.printStackTrace();
            throw new DBException(Messages.ERR_CANNOT_GRADE_MASTER);
        } finally {
            close(connection);
        }
        return true;
    }

    @Override
    public boolean setComment(int bookingId, String comment) {
        return clientBookingDao.setComment(bookingId, comment);
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
            LOG.error("Cannot rollback master grade");
        }
    }

}
