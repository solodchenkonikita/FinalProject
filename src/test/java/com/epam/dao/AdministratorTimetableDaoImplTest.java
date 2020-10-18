package com.epam.dao;

import com.epam.dao.impl.AdministratorTimetableDaoImpl;
import com.epam.entity.Timetable;
import com.epam.exception.DBException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdministratorTimetableDaoImplTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet rs;

    private AdministratorTimetableDaoImpl dao;

    @Before
    public void SetUp() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        dao = new AdministratorTimetableDaoImpl();
    }

    @Test
    public void getTimetableWithBookingByDateTest() throws SQLException, DBException {

        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);
        when(rs.getDate(anyInt())).thenReturn(Date.valueOf("2020-10-10"));
        when(rs.getString(anyInt())).thenReturn("11:00:00");

        List<Timetable> list = dao.getTimetableWithBookingByDate(connection, Date.valueOf("2020-10-10"), 1, 1);
        assertEquals(list.size(), 1);
    }

    @Test(expected = DBException.class)
    public void getTimetableWithBookingByDateTest_ThrowDBException() throws SQLException, DBException {
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getTimetableWithBookingByDate(connection, Date.valueOf("2020-10-10"), 1, 1);

    }

    @Test
    public void getTimetableWithBookingByDateCountTest() throws SQLException, DBException {
        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);

        int actual = dao.getTimetableWithBookingByDateCount(connection, Date.valueOf("2020-10-10"));
        int expected = 1;
        assertEquals(actual, expected);

    }

    @Test(expected = DBException.class)
    public void getUserBookingCountTest_ThrowDBException() throws SQLException, DBException {
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getTimetableWithBookingByDateCount(connection, Date.valueOf("2020-10-10"));
    }

    @Test
    public void setBookingPaidTest() throws SQLException {
        when(ps.executeUpdate()).thenReturn(1);
        boolean actual = dao.setBookingPaid(connection, 1);
        assertTrue(actual);

        when(ps.executeUpdate()).thenReturn(0);
        actual = dao.setBookingPaid(connection, 1);
        assertFalse(actual);

        when(ps.executeUpdate()).thenThrow(new SQLException());
        actual = dao.setBookingPaid(connection, 1);
        assertFalse(actual);

    }

    @Test
    public void deleteBookingFromTimetable() throws SQLException {
        when(ps.executeUpdate()).thenReturn(1);
        boolean actual = dao.deleteBookingFromTimetable(connection, 1);
        assertTrue(actual);

        when(ps.executeUpdate()).thenReturn(0);
        actual = dao.deleteBookingFromTimetable(connection, 1);
        assertFalse(actual);

        when(ps.executeUpdate()).thenThrow(new SQLException());
        actual = dao.deleteBookingFromTimetable(connection, 1);
        assertFalse(actual);

    }

    @Test
    public void deleteBookingTest() throws SQLException {
        when(ps.executeUpdate()).thenReturn(1);
        boolean actual = dao.deleteBooking(connection, 1);
        assertTrue(actual);

        when(ps.executeUpdate()).thenReturn(0);
        actual = dao.deleteBooking(connection, 1);
        assertFalse(actual);

        when(ps.executeUpdate()).thenThrow(new SQLException());
        actual = dao.deleteBooking(connection, 1);
        assertFalse(actual);

    }

    @Test
    public void getMasterFreeTimetableByDateTest() throws SQLException, DBException {

        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);
        when(rs.getString(anyInt())).thenReturn("11:00:00");

        List<Timetable> list = dao.getMasterFreeTimetableByDate(connection, Date.valueOf("2020-10-10"), 1);
        assertEquals(list.size(), 1);
    }

    @Test(expected = DBException.class)
    public void getMasterFreeTimetableByDateTest_ThrowDBException() throws SQLException, DBException {
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getMasterFreeTimetableByDate(connection, Date.valueOf("2020-10-10"), 1);
    }

}
