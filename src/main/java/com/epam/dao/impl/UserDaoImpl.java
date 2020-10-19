package com.epam.dao.impl;

import com.epam.dao.UserDao;
import com.epam.entity.User;
import com.epam.exception.DBException;
import com.epam.exception.Messages;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of UserDao interface.
 *
 * @author Solodchenko Nikita
 *
 */
public class UserDaoImpl implements UserDao {

    private final static Logger LOG = Logger.getLogger(UserDaoImpl.class);

    private static final String SQL_ADD_USER =
            "INSERT INTO user (first_name, last_name, email, password, role_id) VALUES (?, ?, ?, ?, " +
                    "(SELECT id FROM role WHERE rolename = ?)) ";
    private static final String SQL_FIND_ALL_MASTERS =
            "SELECT id, first_name, last_name FROM beauty_salon.user " +
                    "WHERE role_id=(SELECT id FROM beauty_salon.role WHERE rolename='master') ";
    private static final String SQL_FIND_USER_BY_EMAIL =
            "SELECT user.id, first_name, last_name, email, password, role.rolename FROM beauty_salon.user " +
                    "LEFT JOIN beauty_salon.role ON user.role_id=role.id WHERE email = ? ";
    private static final String SQL_FIND_USER_EMAIL_BY_EMAIL = "SELECT email FROM user WHERE email = ?";
    private static final String SQL_FIND_USERS_WITH_BOOKING_BY_DATE =
            "SELECT DISTINCT user.id, user.first_name, user.last_name, user.email " +
                    "FROM beauty_salon.timetable " +
                    "LEFT JOIN beauty_salon.calendar ON timetable.calendar_id=calendar.id " +
                    "LEFT JOIN beauty_salon.booking ON timetable.booking_id=booking.id " +
                    "LEFT JOIN beauty_salon.user ON booking.client_id=user.id " +
                    "WHERE date = ? AND booking_id IS NOT NULL ";

    @Override
    public void addUser(Connection connection, User user) throws DBException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_ADD_USER);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole());
            ps.execute();

        } catch (SQLException ex) {
            LOG.error("Cannot add user");
            throw new DBException(Messages.ERR_CANNOT_ADD_USER);
        } finally {
            close(ps);
        }
    }

    @Override
    public User findUserByEmail(Connection connection, String email) throws DBException {
        User user = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL);
            ps.setString(1, email);
            ps.execute();

            rs = ps.getResultSet();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setRole(rs.getString("rolename"));
            }

        } catch (SQLException ex) {
            LOG.error("Cannot obtain user by email");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_EMAIL);
        } finally {
            close(rs);
            close(ps);
        }
        return user;
    }

    @Override
    public String findUserEmailByEmail(Connection connection, String email) throws DBException {
        String dbEmail = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_FIND_USER_EMAIL_BY_EMAIL);
            ps.setString(1, email);
            ps.execute();

            rs = ps.getResultSet();
            if (rs.next()) {
                dbEmail = rs.getString(1);
            }

        } catch (SQLException ex) {
            LOG.error("Cannot obtain user email by email");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_EMAIL_BY_EMAIL);
        } finally {
            close(rs);
            close(ps);
        }
        return dbEmail;
    }

    @Override
    public List<User> findAllMasters(Connection connection) throws DBException {
        List<User> masters = new ArrayList<>();
        Statement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.createStatement();
            ps.executeQuery(SQL_FIND_ALL_MASTERS);

            rs = ps.getResultSet();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                masters.add(user);
            }

        } catch (SQLException ex) {
            LOG.error("Cannot obtain masters");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_MASTERS);
        } finally {
            close(rs);
            close(ps);
        }
        return masters;
    }

    @Override
    public List<User> getUsersWithBookingByDate(Connection connection, Date date) {
        List<User> users = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_FIND_USERS_WITH_BOOKING_BY_DATE);
            ps.setDate(1, date);
            ps.execute();

            rs = ps.getResultSet();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setEmail(rs.getString(4));
                users.add(user);
            }

        } catch (SQLException ex) {
            LOG.error("Cannot obtain users with booking");
            ex.printStackTrace();
        } finally {
            close(rs);
            close(ps);
        }
        return users;
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
