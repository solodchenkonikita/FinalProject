package com.epam.dao.impl;

import com.epam.dao.AdministratorTimetableDao;
import com.epam.entity.*;
import com.epam.exception.DBException;
import com.epam.exception.Messages;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministratorTimetableDaoImpl implements AdministratorTimetableDao {

    private final static Logger LOG = Logger.getLogger(AdministratorTimetableDaoImpl.class);
    private static final String SQL_GET_TIMETABLE_WITH_BOOKING =
            "SELECT date, timetable.id, timeslot_id, timeslot.start_time, timeslot.end_time, master_id, " +
                    "(SELECT user.first_name FROM beauty_salon.user WHERE id = master_id), " +
                    "(SELECT user.last_name FROM beauty_salon.user WHERE id = master_id), " +
                    "booking_id, user.id, user.first_name, user.last_name,  " +
                    "booking.progress_status, booking.payment_status, service.service_name  " +
                    "FROM beauty_salon.timetable " +
                    "LEFT JOIN beauty_salon.calendar ON timetable.calendar_id=calendar.id " +
                    "LEFT JOIN beauty_salon.timeslot ON timetable.timeslot_id=timeslot.id " +
                    "LEFT JOIN beauty_salon.booking ON timetable.booking_id=booking.id " +
                    "LEFT JOIN beauty_salon.user ON booking.client_id=user.id " +
                    "LEFT JOIN beauty_salon.service ON booking.service_id=service.id " +
                    "WHERE date = ? AND booking_id IS NOT NULL " +
                    "ORDER BY start_time LIMIT ?, ? ";

    private static final String SQL_GET_TIMETABLE_WITH_BOOKING_COUNT =
            "SELECT COUNT(*) FROM beauty_salon.timetable " +
                    "LEFT JOIN beauty_salon.calendar ON timetable.calendar_id=calendar.id " +
                    "WHERE booking_id IS NOT NULL AND calendar.date=? ";

    private static final String SQL_GET_MASTER_FREE_TIMETABLE_BY_DATE =
            "SELECT timetable.id, timeslot.id, timeslot.start_time, timeslot.end_time " +
                    "FROM beauty_salon.timetable " +
                    "LEFT JOIN beauty_salon.calendar ON timetable.calendar_id=calendar.id " +
                    "LEFT JOIN beauty_salon.timeslot ON timetable.timeslot_id=timeslot.id " +
                    "WHERE booking_id IS NULL AND date=? AND master_id=? " +
                    "ORDER BY start_time ";
    private static final String SQL_SET_BOOKING_PAID = "UPDATE beauty_salon.booking SET payment_status = 'confirmed' WHERE id = ? ";
    private static final String SQL_DELETE_BOOKING_FROM_TIMETABLE = "UPDATE beauty_salon.timetable SET booking_id = NULL WHERE booking_id=? ";
    private static final String SQL_DELETE_BOOKING = "DELETE FROM beauty_salon.booking WHERE id=? ";

    @Override
    public List<Timetable> getTimetableWithBookingByDate(Connection connection, Date date, int start, int limit) throws DBException {
        List<Timetable> timetables = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_TIMETABLE_WITH_BOOKING);
            ps.setDate(1, date);
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

                User master = new User();
                master.setId(rs.getInt(6));
                master.setFirstName(rs.getString(7));
                master.setLastName(rs.getString(8));
                timetable.setMaster(master);

                Booking booking = new Booking();
                booking.setId(rs.getInt(9));

                User client = new User();
                client.setId(rs.getInt(10));
                client.setFirstName(rs.getString(11));
                client.setLastName(rs.getString(12));
                booking.setClient(client);

                booking.setProgressStatus(rs.getString(13));
                booking.setPaymentStatus(rs.getString(14));

                Service service = new Service();
                service.setServiceName(rs.getString(15));
                booking.setService(service);
                timetable.setBooking(booking);

                timetables.add(timetable);
            }

        } catch (SQLException ex) {
            LOG.error("Cannot find timetable with booking");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_TIMETABLE_WITH_BOOKING);
        } finally {
            close(rs);
            close(ps);
        }
        return timetables;
    }

    @Override
    public int getTimetableWithBookingByDateCount(Connection connection, Date date) throws DBException {
        int count = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_TIMETABLE_WITH_BOOKING_COUNT);
            ps.setDate(1, date);
            ps.executeQuery();
            rs = ps.getResultSet();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            LOG.error("Cannot obtain timetable with booking count");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_TIMETABLE_WITH_BOOKING_COUNT);
        } finally {
            close(rs);
            close(ps);
        }
        return count;
    }

    @Override
    public boolean setBookingPaid(Connection connection, int bookingId) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_SET_BOOKING_PAID);
            ps.setInt(1, bookingId);

            if (ps.executeUpdate() != 1) {
                return false;
            }

        } catch (SQLException ex) {
            LOG.error("Cannot change payment status", ex);
            return false;
        } finally {
            close(ps);
        }
        return true;
    }

    @Override
    public boolean deleteBookingFromTimetable(Connection connection, int bookingId) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_DELETE_BOOKING_FROM_TIMETABLE);
            ps.setInt(1, bookingId);

            if (ps.executeUpdate() != 1) {
                return false;
            }

        } catch (SQLException ex) {
            LOG.error("Cannot delete booking from timetable");
            return false;
        } finally {
            close(ps);
        }
        return true;
    }

    @Override
    public boolean deleteBooking(Connection connection, int bookingId) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_DELETE_BOOKING);
            ps.setInt(1, bookingId);

            if (ps.executeUpdate() != 1) {
                return false;
            }

        } catch (SQLException ex) {
            LOG.error("Cannot delete booking");
            return false;
        } finally {
            close(ps);
        }
        return true;
    }

    @Override
    public List<Timetable> getMasterFreeTimetableByDate(Connection connection, Date date, int masterId) throws DBException {
        List<Timetable> timetables = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_MASTER_FREE_TIMETABLE_BY_DATE);
            ps.setDate(1, date);
            ps.setInt(2, masterId);
            ps.executeQuery();

            rs = ps.getResultSet();
            while (rs.next()) {
                Timetable timetable = new Timetable();
                timetable.setId(rs.getInt(1));

                Timeslot timeslot = new Timeslot();
                timeslot.setId(rs.getInt(2));
                timeslot.setStartTime(Time.valueOf(rs.getString(3)));
                timeslot.setEndTime(Time.valueOf(rs.getString(4)));
                timetable.setTimeslot(timeslot);

                timetables.add(timetable);
            }

        } catch (SQLException ex) {
            LOG.error("Cannot find master free timetable");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_MASTER_FREE_TIMETABLE);
        } finally {
            close(rs);
            close(ps);
        }
        return timetables;
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
