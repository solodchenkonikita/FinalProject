package com.epam.service.impl;

import com.epam.dao.MasterTimetableDao;
import com.epam.dao.impl.MasterTimetableDaoImpl;
import com.epam.entity.Timetable;
import com.epam.exception.DBException;
import com.epam.service.MasterTimetableManager;
import com.epam.util.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class MasterTimetableManagerImpl implements MasterTimetableManager {

    MasterTimetableDao masterTimetableDao = new MasterTimetableDaoImpl();

    @Override
    public List<Timetable> getMasterTimetableByDate(Date date, int masterId) throws DBException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            return masterTimetableDao.getMasterTimetableByDate(connection, date, masterId);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean setBookingDone(int bookingId) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            return masterTimetableDao.setBookingDone(connection, bookingId);
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
}
