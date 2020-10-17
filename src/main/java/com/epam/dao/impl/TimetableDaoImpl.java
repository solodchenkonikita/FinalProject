package com.epam.dao.impl;

import com.epam.dao.TimetableDao;
import com.epam.entity.*;
import com.epam.exception.DBException;
import com.epam.exception.Messages;
import com.epam.util.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimetableDaoImpl implements TimetableDao {

    private final static Logger LOG = Logger.getLogger(TimetableDaoImpl.class);

    private static final String SQL_GET_TIMETABLE_BY_DATE_AND_SORT_BY_MASTER_NAME =
            "SELECT timetable.id, timeslot_id, timeslot.start_time, timeslot.end_time, user.id, user.first_name, user.last_name, booking_id " +
                    "FROM beauty_salon.timetable " +
                    "LEFT JOIN beauty_salon.calendar ON timetable.calendar_id=calendar.id " +
                    "LEFT JOIN beauty_salon.timeslot ON timetable.timeslot_id=timeslot.id " +
                    "LEFT JOIN beauty_salon.user ON timetable.master_id=user.id " +
                    "WHERE booking_id IS NULL AND calendar.date=? AND TIME(timeslot.start_time)>TIME(?) " +
                    "ORDER BY user.first_name, user.last_name LIMIT ?,? ";

    private static final String SQL_GET_TIMETABLE_BY_DATE_AND_SORT_BY_MASTER_NAME_COUNT =
            "SELECT COUNT(*) " +
                    "FROM beauty_salon.timetable " +
                    "LEFT JOIN beauty_salon.calendar ON timetable.calendar_id=calendar.id " +
                    "LEFT JOIN beauty_salon.timeslot ON timetable.timeslot_id=timeslot.id " +
                    "LEFT JOIN beauty_salon.user ON timetable.master_id=user.id " +
                    "WHERE booking_id IS NULL AND calendar.date=? AND TIME(timeslot.start_time)>TIME(?) " +
                    "ORDER BY user.first_name, user.last_name ";

    private static final String SQL_GET_TIMETABLE_BY_DATE_AND_SORT_BY_MASTER_MARK =
            "SELECT timetable.id, timeslot_id, timeslot.start_time, timeslot.end_time, user.id, user.first_name, user.last_name, booking_id " +
                    "FROM beauty_salon.timetable " +
                    "LEFT JOIN beauty_salon.calendar ON timetable.calendar_id=calendar.id " +
                    "LEFT JOIN beauty_salon.timeslot ON timetable.timeslot_id=timeslot.id " +
                    "LEFT JOIN beauty_salon.user ON timetable.master_id=user.id " +
                    "LEFT JOIN beauty_salon.master_mark ON timetable.master_id=master_mark.master_id " +
                    "WHERE booking_id IS NULL AND calendar.date=? AND TIME(timeslot.start_time)>TIME(?) " +
                    "ORDER BY master_mark.mark DESC, user.first_name, user.last_name LIMIT ?,? ";

    private static final String SQL_GET_TIMETABLE_BY_DATE_AND_SERVICE_ID =
            "SELECT timetable.id, timeslot_id, timeslot.start_time, timeslot.end_time, user.id, user.first_name, user.last_name, booking_id " +
                    "FROM beauty_salon.timetable " +
                    "LEFT JOIN beauty_salon.calendar ON timetable.calendar_id=calendar.id " +
                    "LEFT JOIN beauty_salon.timeslot ON timetable.timeslot_id=timeslot.id " +
                    "LEFT JOIN beauty_salon.user ON timetable.master_id=user.id " +
                    "LEFT JOIN beauty_salon.master_has_service ON timetable.master_id=master_has_service.user_id " +
                    "WHERE booking_id IS NULL AND calendar.date=? AND TIME(timeslot.start_time)>TIME(?) " +
                    "AND service_id=? " +
                    "ORDER BY user.first_name, user.last_name LIMIT ?,? ";

    private static final String SQL_GET_TIMETABLE_BY_DATE_AND_MASTER_ID_COUNT =
            "SELECT COUNT(*) " +
                    "FROM beauty_salon.timetable " +
                    "LEFT JOIN beauty_salon.calendar ON timetable.calendar_id=calendar.id " +
                    "LEFT JOIN beauty_salon.timeslot ON timetable.timeslot_id=timeslot.id " +
                    "LEFT JOIN beauty_salon.user ON timetable.master_id=user.id " +
                    "WHERE booking_id IS NULL AND calendar.date=? AND timetable.master_id=? " +
                    "AND TIME(timeslot.start_time)>TIME(?) ";

    private static final String SQL_GET_TIMETABLE_BY_DATE_AND_SERVICE_ID_COUNT =
            "SELECT COUNT(*) " +
                    "FROM beauty_salon.timetable " +
                    "LEFT JOIN beauty_salon.calendar ON timetable.calendar_id=calendar.id " +
                    "LEFT JOIN beauty_salon.timeslot ON timetable.timeslot_id=timeslot.id " +
                    "LEFT JOIN beauty_salon.user ON timetable.master_id=user.id " +
                    "LEFT JOIN beauty_salon.master_has_service ON timetable.master_id=master_has_service.user_id " +
                    "WHERE booking_id IS NULL AND calendar.date=? AND TIME(timeslot.start_time)>TIME(?) " +
                    "AND service_id=? " +
                    "ORDER BY user.first_name, user.last_name ";

    private Connection connection;

    private final String SQL_GET_TIMETABLE_BY_DATE =
            "SELECT timetable.id, timeslot_id, timeslot.start_time, timeslot.end_time, user.id, user.first_name, user.last_name, booking_id " +
                    "FROM beauty_salon.timetable " +
                    "LEFT JOIN beauty_salon.calendar ON timetable.calendar_id=calendar.id " +
                    "LEFT JOIN beauty_salon.timeslot ON timetable.timeslot_id=timeslot.id " +
                    "LEFT JOIN beauty_salon.user ON timetable.master_id=user.id " +
                    "WHERE booking_id IS NULL AND calendar.date=? AND TIME(timeslot.start_time)>TIME(?) LIMIT ?,? ";

    private final String SQL_GET_TIMETABLE_BY_DATE_AND_MASTER_ID =
            "SELECT timetable.id, timeslot_id, timeslot.start_time, timeslot.end_time, user.id, user.first_name, user.last_name, booking_id " +
                    "FROM beauty_salon.timetable " +
                    "LEFT JOIN beauty_salon.calendar ON timetable.calendar_id=calendar.id " +
                    "LEFT JOIN beauty_salon.timeslot ON timetable.timeslot_id=timeslot.id " +
                    "LEFT JOIN beauty_salon.user ON timetable.master_id=user.id " +
                    "WHERE booking_id IS NULL AND calendar.date=? AND timetable.master_id=? " +
                    "AND TIME(timeslot.start_time)>TIME(?) LIMIT ?,? ";

    private static final String SQL_GET_DATES = "SELECT date FROM calendar WHERE date>=? ";
    private static final String SQL_CREATE_BOOKING = "INSERT INTO beauty_salon.booking (service_id, client_id) VALUES (?, ?) ";
    private static final String SQL_ADD_BOOKING_TO_TIMETABLE = "UPDATE timetable SET booking_id = ? WHERE (id = ?)  AND booking_id IS NULL ";

    @Override
    public List<Timetable> getTimetable(Date date, Time time, int start, int limit) throws DBException {
        System.out.println("Time inside = " + time);
        List<Timetable> timetables = new ArrayList<>();
        connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_TIMETABLE_BY_DATE);
            ps.setDate(1, date);
            ps.setString(2, time.toString());
            ps.setInt(3, start);
            ps.setInt(4, limit);
            ps.executeQuery();

            rs = ps.getResultSet();
            while (rs.next()) {
                Timetable timetable = new Timetable();
                timetable.setDate(date);
                timetable.setId(rs.getInt(1));

                Timeslot timeslot = new Timeslot();
                timeslot.setId(rs.getInt(2));
                timeslot.setStartTime(Time.valueOf(rs.getString(3)));
                timeslot.setEndTime(Time.valueOf(rs.getString(4)));
                timetable.setTimeslot(timeslot);

                User user = new User();
                user.setId(rs.getInt(5));
                user.setFirstName(rs.getString(6));
                user.setLastName(rs.getString(7));
                user.setRole(Role.MASTER.getName());
                timetable.setMaster(user);

                timetables.add(timetable);
            }

        } catch (SQLException ex) {
            LOG.error("Cannot obtain timetable");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_TIMETABLE);
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return timetables;
    }

    @Override
    public List<Date> getDates(Date date) throws DBException {
        List<Date> dates = new ArrayList<>();
        connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_DATES);
            ps.setDate(1, date);
            ps.executeQuery();

            rs = ps.getResultSet();
            while (rs.next()) {
                dates.add(rs.getDate(1));
            }

        } catch (SQLException ex) {
            LOG.error("Cannot obtain dates");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATES);
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return dates;
    }

    //in progress
    @Override
    public List<Timetable> getTimetableByDateAndMasterId(Date date, int masterId, Time time, int start, int limit) throws DBException {
        List<Timetable> timetables = new ArrayList<>();
        connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_TIMETABLE_BY_DATE_AND_MASTER_ID);
            ps.setDate(1, date);
            ps.setInt(2, masterId);
            ps.setString(3, time.toString());
            ps.setInt(4, start);
            ps.setInt(5, limit);
            ps.executeQuery();

            rs = ps.getResultSet();
            while (rs.next()) {
                Timetable timetable = new Timetable();
                timetable.setDate(date);
                timetable.setId(rs.getInt(1));

                Timeslot timeslot = new Timeslot();
                timeslot.setId(rs.getInt(2));
                timeslot.setStartTime(Time.valueOf(rs.getString(3)));
                timeslot.setEndTime(Time.valueOf(rs.getString(4)));
                timetable.setTimeslot(timeslot);

                User user = new User();
                user.setId(rs.getInt(5));
                user.setFirstName(rs.getString(6));
                user.setLastName(rs.getString(7));
                user.setRole(Role.MASTER.getName());
                timetable.setMaster(user);

                timetables.add(timetable);
            }

        } catch (SQLException ex) {
            LOG.error("Cannot obtain timetable");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_TIMETABLE);
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return timetables;
    }

    @Override
    public int getTimetableByDateAndMasterIdCount(Date date, Time time, int masterId) throws DBException {
        int count = 0;
        connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_TIMETABLE_BY_DATE_AND_MASTER_ID_COUNT);
            ps.setDate(1, date);
            ps.setInt(2, masterId);
            ps.setString(3, time.toString());
            ps.executeQuery();

            rs = ps.getResultSet();
            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException ex) {
            LOG.error("Cannot obtain timetable cont");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_TIMETABLE_COUNT);
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return count;
    }

    @Override
    public List<Timetable> getTimetableByDateAndSortByMasterName(Date date, Time time, int start, int limit) throws DBException {
        List<Timetable> timetables = new ArrayList<>();
        connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_TIMETABLE_BY_DATE_AND_SORT_BY_MASTER_NAME);
            ps.setDate(1, date);
            ps.setString(2, time.toString());
            ps.setInt(3, start);
            ps.setInt(4, limit);
            ps.executeQuery();

            rs = ps.getResultSet();
            while (rs.next()) {
                Timetable timetable = new Timetable();
                timetable.setDate(date);
                timetable.setId(rs.getInt(1));

                Timeslot timeslot = new Timeslot();
                timeslot.setId(rs.getInt(2));
                timeslot.setStartTime(Time.valueOf(rs.getString(3)));
                timeslot.setEndTime(Time.valueOf(rs.getString(4)));
                timetable.setTimeslot(timeslot);

                User user = new User();
                user.setId(rs.getInt(5));
                user.setFirstName(rs.getString(6));
                user.setLastName(rs.getString(7));
                user.setRole(Role.MASTER.getName());
                timetable.setMaster(user);

                timetables.add(timetable);
            }

        } catch (SQLException ex) {
            LOG.error("Cannot obtain timetable");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_TIMETABLE);
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return timetables;
    }

    @Override
    public int getTimetableByDateCount(Date date, Time time) throws DBException {
        int count = 0;
        connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_TIMETABLE_BY_DATE_AND_SORT_BY_MASTER_NAME_COUNT);
            ps.setDate(1, date);
            ps.setString(2, time.toString());
            ps.executeQuery();

            rs = ps.getResultSet();
            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException ex) {
            LOG.error("Cannot obtain timetable cont");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_TIMETABLE_COUNT);
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return count;
    }

    @Override
    public List<Timetable> getTimetableByDateAndSortByMasterMark(Date date, Time time, int start, int limit) throws DBException {
        List<Timetable> timetables = new ArrayList<>();
        connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_TIMETABLE_BY_DATE_AND_SORT_BY_MASTER_MARK);
            ps.setDate(1, date);
            ps.setString(2, time.toString());
            ps.setInt(3, start);
            ps.setInt(4, limit);
            ps.executeQuery();

            rs = ps.getResultSet();
            while (rs.next()) {
                Timetable timetable = new Timetable();
                timetable.setDate(date);
                timetable.setId(rs.getInt(1));

                Timeslot timeslot = new Timeslot();
                timeslot.setId(rs.getInt(2));
                timeslot.setStartTime(Time.valueOf(rs.getString(3)));
                timeslot.setEndTime(Time.valueOf(rs.getString(4)));
                timetable.setTimeslot(timeslot);

                User user = new User();
                user.setId(rs.getInt(5));
                user.setFirstName(rs.getString(6));
                user.setLastName(rs.getString(7));
                user.setRole(Role.MASTER.getName());
                timetable.setMaster(user);

                timetables.add(timetable);
            }

        } catch (SQLException ex) {
            LOG.error("Cannot obtain timetable");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_TIMETABLE);
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return timetables;
    }

    @Override
    public List<Timetable> getTimetableByDateAndService(Date date, int serviceId, Time time, int start, int limit) throws DBException {
        List<Timetable> timetables = new ArrayList<>();
        connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_TIMETABLE_BY_DATE_AND_SERVICE_ID);
            ps.setDate(1, date);
            ps.setString(2, time.toString());
            ps.setInt(3, serviceId);
            ps.setInt(4, start);
            ps.setInt(5, limit);

            ps.executeQuery();

            rs = ps.getResultSet();
            while (rs.next()) {
                Timetable timetable = new Timetable();
                timetable.setDate(date);
                timetable.setId(rs.getInt(1));

                Timeslot timeslot = new Timeslot();
                timeslot.setId(rs.getInt(2));
                timeslot.setStartTime(Time.valueOf(rs.getString(3)));
                timeslot.setEndTime(Time.valueOf(rs.getString(4)));
                timetable.setTimeslot(timeslot);

                User user = new User();
                user.setId(rs.getInt(5));
                user.setFirstName(rs.getString(6));
                user.setLastName(rs.getString(7));
                user.setRole(Role.MASTER.getName());
                timetable.setMaster(user);

                timetables.add(timetable);
            }

        } catch (SQLException ex) {
            LOG.error("Cannot obtain timetable");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_TIMETABLE);
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return timetables;
    }

    @Override
    public int getTimetableByDateAndServiceCount(Date date, Time time, int serviceId) throws DBException {
        int count = 0;
        connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_TIMETABLE_BY_DATE_AND_SERVICE_ID_COUNT);
            ps.setDate(1, date);
            ps.setString(2, time.toString());
            ps.setInt(3, serviceId);
            ps.executeQuery();

            rs = ps.getResultSet();
            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException ex) {
            LOG.error("Cannot obtain timetable count");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_TIMETABLE_COUNT);
        } finally {
            close(rs);
            close(ps);
            close(connection);
        }
        return count;
    }

    @Override
    public int createBooking(Connection connection, int serviceId, int clientId) throws DBException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int bookingId = -1;
        try {
            ps = connection.prepareStatement(SQL_CREATE_BOOKING, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, serviceId);
            ps.setInt(2, clientId);
            ps.execute();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                bookingId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            LOG.error("Cannot create booking");
            throw new DBException(Messages.ERR_CANNOT_CREATE_BOOKING);
        } finally {
            close(rs);
            close(ps);
        }
        return bookingId;
    }

    @Override
    public boolean addBookingToTimetable(Connection connection, int timetableId, int bookingId) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_ADD_BOOKING_TO_TIMETABLE);
            ps.setInt(1, bookingId);
            ps.setInt(2, timetableId);
            if (ps.executeUpdate() != 1) {
                return false;
            }
        } catch (SQLException ex) {
            LOG.error("Cannot add booking to timetable");
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
