package com.epam.dao.impl;

import com.epam.dao.MasterTimetableDao;
import com.epam.entity.*;
import com.epam.exception.DBException;
import com.epam.exception.Messages;
import com.epam.util.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MasterTimetableDaoImpl implements MasterTimetableDao {

    private final static Logger LOG = Logger.getLogger(MasterTimetableDaoImpl.class);
    private static final String SQL_GET_MASTER_TIMETABLE_BY_DATE =
            "SELECT date, timetable.id, timeslot_id, timeslot.start_time, timeslot.end_time, booking_id, " +
                    "user.id, user.first_name, user.last_name, " +
                    "booking.progress_status, booking.payment_status, service.service_name " +
                    "FROM beauty_salon.timetable " +
                    "LEFT JOIN beauty_salon.calendar ON timetable.calendar_id=calendar.id " +
                    "LEFT JOIN beauty_salon.timeslot ON timetable.timeslot_id=timeslot.id " +
                    "LEFT JOIN beauty_salon.booking ON timetable.booking_id=booking.id " +
                    "LEFT JOIN beauty_salon.user ON booking.client_id=user.id " +
                    "LEFT JOIN beauty_salon.service ON booking.service_id=service.id " +
                    "WHERE date = ? AND timetable.master_id = ? " +
                    "ORDER BY start_time ";
    private static final String SQL_SET_BOOKING_DONE = "UPDATE beauty_salon.booking SET progress_status = 'done' WHERE id = ? ";

    private Connection connection;


    @Override
    public List<Timetable> getMasterTimetableByDate(Date date, int masterId) throws DBException {
        List<Timetable> timetables = new ArrayList<>();
        connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_MASTER_TIMETABLE_BY_DATE);
            ps.setDate(1, date);
            ps.setInt(2, masterId);
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
                User user = new User();
                user.setId(rs.getInt(7));
                user.setFirstName(rs.getString(8));
                user.setLastName(rs.getString(9));
                booking.setClient(user);
                booking.setProgressStatus(rs.getString(10));
                booking.setPaymentStatus(rs.getString(11));

                Service service = new Service();
                service.setServiceName(rs.getString(12));
                booking.setService(service);
                timetable.setBooking(booking);

                timetables.add(timetable);
            }

        } catch (SQLException ex) {
            LOG.error("Cannot obtain master timetable");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_MASTER_TIMETABLE);
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return timetables;
    }

    @Override
    public boolean setBookingDone(int bookingId) {
        connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SET_BOOKING_DONE);
            ps.setInt(1, bookingId);

            if (ps.executeUpdate() != 1) {
                return false;
            }

        } catch (SQLException ex) {
            LOG.error("Cannot change progress status");
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
