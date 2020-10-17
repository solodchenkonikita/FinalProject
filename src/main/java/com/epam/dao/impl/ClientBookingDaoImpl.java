package com.epam.dao.impl;

import com.epam.dao.ClientBookingDao;
import com.epam.entity.*;
import com.epam.exception.DBException;
import com.epam.exception.Messages;
import com.epam.util.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientBookingDaoImpl implements ClientBookingDao {

    private final static Logger LOG = Logger.getLogger(ClientBookingDao.class);

    private static final String SQL_GET_TIMETABLE_WITH_USER_BOOKING =
            "SELECT date, timetable.id, timeslot_id, timeslot.start_time, timeslot.end_time, booking_id, booking.mark, " +
                    "booking.progress_status, booking.payment_status, booking.comment, " +
                    "service.service_name, service.price, user.id, user.first_name, user.last_name " +
                    "FROM beauty_salon.timetable " +
                    "LEFT JOIN beauty_salon.calendar ON timetable.calendar_id=calendar.id " +
                    "LEFT JOIN beauty_salon.timeslot ON timetable.timeslot_id=timeslot.id " +
                    "LEFT JOIN beauty_salon.user ON timetable.master_id=user.id " +
                    "LEFT JOIN beauty_salon.booking ON timetable.booking_id=booking.id " +
                    "LEFT JOIN beauty_salon.service ON booking.service_id=service.id " +
                    "WHERE booking_id IS NOT NULL AND booking.client_id=? " +
                    "ORDER BY date DESC, start_time DESC LIMIT ?, ? ";

    private static final String SQL_MASTER_MARKS =
            "SELECT mark FROM beauty_salon.booking " +
                    "LEFT JOIN beauty_salon.timetable ON booking_id = booking.id " +
                    "WHERE master_id = ? AND mark IS NOT NULL";
    private static final String SQL_GET_USER_BOOKING_COUNT = "SELECT count(*) FROM beauty_salon.booking WHERE client_id = ? ";
    private static final String SQL_SET_MARK_IN_BOOKING = "UPDATE beauty_salon.booking SET mark = ? WHERE id = ? ";
    private static final String SQL_SET_MARK_TO_MASTER = "UPDATE beauty_salon.master_mark SET mark = ? WHERE master_id = ? ";
    private static final String SQL_SET_COMMENT = "UPDATE beauty_salon.booking SET comment = ? WHERE id = ? ";

    private Connection connection;


    @Override
    public List<Timetable> getTimetableWithUserBooking(int userId, int start, int limit) throws DBException {
        List<Timetable> timetables = new ArrayList<>();
        connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_TIMETABLE_WITH_USER_BOOKING);
            ps.setInt(1, userId);
            ps.setInt(2, start);
            ps.setInt(3, limit);
            ps.executeQuery();

            rs = ps.getResultSet();
            while (rs.next()) {
                Timetable timetable = new Timetable();
                timetable.setDate(rs.getDate(1));
                timetable.setId(rs.getInt(2));

                Timeslot timeslot = new Timeslot();
                timeslot.setId(rs.getInt(3));
                timeslot.setStartTime(Time.valueOf(rs.getString(4)));
                timeslot.setEndTime(Time.valueOf(rs.getString(5)));
                timetable.setTimeslot(timeslot);

                Booking booking = new Booking();
                booking.setId(rs.getInt(6));
                booking.setMark(rs.getInt(7));
                booking.setProgressStatus(rs.getString(8));
                booking.setPaymentStatus(rs.getString(9));
                booking.setComment(rs.getString(10));

                Service service = new Service();
                service.setServiceName(rs.getString(11));
                service.setPrice(rs.getDouble(12));
                booking.setService(service);
                timetable.setBooking(booking);

                User user = new User();
                user.setId(rs.getInt(13));
                user.setFirstName(rs.getString(14));
                user.setLastName(rs.getString(15));
                timetable.setMaster(user);

                timetables.add(timetable);
            }

        } catch (SQLException ex) {
            LOG.error("Cannot obtain timetable with user booking");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BOOKING);
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return timetables;
    }

    @Override
    public int getUserBookingCount(int clientId) throws DBException {
        int count = 0;
        connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_USER_BOOKING_COUNT);
            ps.setInt(1, clientId);
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            LOG.error("Cannot obtain booking count");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BOOKING_COUNT);
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return count;
    }

    @Override
    public boolean setMarkInBooking(Connection connection, int bookingId, int mark) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SET_MARK_IN_BOOKING);
            ps.setInt(1, mark);
            ps.setInt(2, bookingId);

            if (ps.executeUpdate() != 1) {
                return false;
            }
        } catch (SQLException ex) {
            LOG.error("Cannot set mark in booking");
            return false;
        } finally {
            close(ps);
        }
        return true;
    }

    @Override
    public List<Integer> masterMarks(Connection connection, int masterId) throws DBException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Integer> marks = new ArrayList<>();
        try {
            ps = connection.prepareStatement(SQL_MASTER_MARKS);
            ps.setInt(1, masterId);
            ps.execute();

            rs = ps.getResultSet();
            if (rs.next()) {
                marks.add(rs.getInt(1));
            }

        } catch (SQLException ex) {
            LOG.error("Can not find master marks");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_MASTER_MARKS);
        } finally {
            close(rs);
            close(ps);
        }
        return marks;
    }

    @Override
    public boolean setMarkToMaster(Connection connection, int masterId, double mark) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SET_MARK_TO_MASTER);
            ps.setDouble(1, mark);
            ps.setInt(2, masterId);

            if (ps.executeUpdate() != 1) {
                return false;
            }

        } catch (SQLException ex) {
            LOG.error("Cannot set mark to master");
            return false;
        } finally {
            close(ps);
        }
        return true;
    }

    @Override
    public boolean setComment(int bookingId, String comment) {
        connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SET_COMMENT);
            ps.setString(1, comment);
            ps.setInt(2, bookingId);

            if (ps.executeUpdate() != 1) {
                return false;
            }

        } catch (SQLException ex) {
            LOG.error("Cannot set comment");
            return false;
        } finally {
            close(ps);
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

    private void close(Statement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
