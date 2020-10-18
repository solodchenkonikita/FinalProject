package com.epam.dao;

import com.epam.dao.impl.TimetableDaoImpl;
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
public class TimetableDaoImplTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet rs;

    private TimetableDaoImpl dao;

    @Before
    public void SetUp() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        dao = new TimetableDaoImpl();
    }

    @Test
    public void getTimetableTest() throws SQLException, DBException {

        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);
        when(rs.getDate(anyInt())).thenReturn(Date.valueOf("2020-10-10"));
        when(rs.getString(anyInt())).thenReturn("11:00:00");

        List<Timetable> list = dao.getTimetable(connection, Date.valueOf("2020-10-10"), Time.valueOf("00:00:00"), 1, 1);
        assertEquals(list.size(), 1);
    }

    @Test(expected = DBException.class)
    public void getTimetableTest_ThrowDBException() throws SQLException, DBException {
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getTimetable(connection, Date.valueOf("2020-10-10"), Time.valueOf("00:00:00"), 1, 1);

    }

    @Test
    public void getDatesTest() throws SQLException, DBException {

        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        List<Date> list = dao.getDates(connection, Date.valueOf("2020-10-10"));
        assertEquals(list.size(), 1);
    }

    @Test(expected = DBException.class)
    public void getDatesTest_ThrowDBException() throws SQLException, DBException {
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getDates(connection, Date.valueOf("2020-10-10"));
    }

    @Test
    public void getTimetableByDateAndMasterIdTest() throws SQLException, DBException {

        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);
        when(rs.getDate(anyInt())).thenReturn(Date.valueOf("2020-10-10"));
        when(rs.getString(anyInt())).thenReturn("11:00:00");

        List<Timetable> list = dao.getTimetableByDateAndMasterId(connection, Date.valueOf("2020-10-10"), 1, Time.valueOf("00:00:00"), 1, 1);
        assertEquals(list.size(), 1);
    }

    @Test(expected = DBException.class)
    public void getTimetableByDateAndMasterIdTest_ThrowDBException() throws SQLException, DBException {
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getTimetableByDateAndMasterId(connection, Date.valueOf("2020-10-10"), 1, Time.valueOf("00:00:00"), 1, 1);
    }

    @Test
    public void getTimetableByDateAndMasterIdCountTest() throws SQLException, DBException {
        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);

        int actual = dao.getTimetableByDateAndMasterIdCount(connection, Date.valueOf("2020-10-10"), Time.valueOf("00:00:00"), 1);
        int expected = 1;
        assertEquals(actual, expected);

    }

    @Test(expected = DBException.class)
    public void getTimetableByDateAndMasterIdCountTest_ThrowDBException() throws SQLException, DBException {
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getTimetableByDateAndMasterIdCount(connection, Date.valueOf("2020-10-10"), Time.valueOf("00:00:00"), 1);
    }

    @Test
    public void getTimetableByDateAndSortByMasterNameTest() throws SQLException, DBException {

        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);
        when(rs.getDate(anyInt())).thenReturn(Date.valueOf("2020-10-10"));
        when(rs.getString(anyInt())).thenReturn("11:00:00");

        List<Timetable> list = dao.getTimetableByDateAndSortByMasterName(connection, Date.valueOf("2020-10-10"), Time.valueOf("00:00:00"), 1, 1);
        assertEquals(list.size(), 1);
    }

    @Test(expected = DBException.class)
    public void getTimetableByDateAndSortByMasterNameTest_ThrowDBException() throws SQLException, DBException {
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getTimetableByDateAndSortByMasterName(connection, Date.valueOf("2020-10-10"), Time.valueOf("00:00:00"), 1, 1);
    }

    @Test
    public void getTimetableByDateCountTest() throws SQLException, DBException {
        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);

        int actual = dao.getTimetableByDateCount(connection, Date.valueOf("2020-10-10"), Time.valueOf("00:00:00"));
        int expected = 1;
        assertEquals(actual, expected);

    }

    @Test(expected = DBException.class)
    public void getTimetableByDateCountTest_ThrowDBException() throws SQLException, DBException {
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getTimetableByDateCount(connection, Date.valueOf("2020-10-10"), Time.valueOf("00:00:00"));
    }

    @Test
    public void getTimetableByDateAndSortByMasterMarkTest() throws SQLException, DBException {

        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);
        when(rs.getDate(anyInt())).thenReturn(Date.valueOf("2020-10-10"));
        when(rs.getString(anyInt())).thenReturn("11:00:00");

        List<Timetable> list = dao.getTimetableByDateAndSortByMasterMark(connection, Date.valueOf("2020-10-10"), Time.valueOf("00:00:00"), 1, 1);
        assertEquals(list.size(), 1);
    }

    @Test(expected = DBException.class)
    public void getTimetableByDateAndSortByMasterMarkTest_ThrowDBException() throws SQLException, DBException {
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getTimetableByDateAndSortByMasterMark(connection, Date.valueOf("2020-10-10"), Time.valueOf("00:00:00"), 1, 1);
    }

    @Test
    public void getTimetableByDateAndServiceTest() throws SQLException, DBException {

        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);
        when(rs.getDate(anyInt())).thenReturn(Date.valueOf("2020-10-10"));
        when(rs.getString(anyInt())).thenReturn("11:00:00");

        List<Timetable> list = dao.getTimetableByDateAndService(connection, Date.valueOf("2020-10-10"), 1, Time.valueOf("00:00:00"), 1, 1);
        assertEquals(list.size(), 1);
    }

    @Test(expected = DBException.class)
    public void getTimetableByDateAndServiceTest_ThrowDBException() throws SQLException, DBException {
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getTimetableByDateAndService(connection, Date.valueOf("2020-10-10"), 1, Time.valueOf("00:00:00"), 1, 1);
    }

    @Test
    public void getTimetableByDateAndServiceCountTest() throws SQLException, DBException {
        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);

        int actual = dao.getTimetableByDateAndServiceCount(connection, Date.valueOf("2020-10-10"), Time.valueOf("00:00:00"), 1);
        int expected = 1;
        assertEquals(actual, expected);

    }

    @Test(expected = DBException.class)
    public void getTimetableByDateAndServiceCountTest_ThrowDBException() throws SQLException, DBException {
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getTimetableByDateAndServiceCount(connection, Date.valueOf("2020-10-10"), Time.valueOf("00:00:00"), 1);
    }

    @Test
    public void createBookingTest() throws SQLException, DBException {
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(ps);
        when(ps.getGeneratedKeys()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);

        int actual = dao.createBooking(connection, 1, 1);
        int expected = 1;
        assertEquals(actual, expected);

    }

    @Test(expected = DBException.class)
    public void createBookingTest_ThrowDBException() throws SQLException, DBException {
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(ps);
        when(ps.execute()).thenThrow(new SQLException());
        dao.createBooking(connection, 1, 1);
    }

    @Test
    public void addBookingToTimetableTest() throws SQLException {
        when(ps.executeUpdate()).thenReturn(1);
        boolean actual = dao.addBookingToTimetable(connection, 1, 1);
        assertTrue(actual);

        when(ps.executeUpdate()).thenReturn(0);
        actual = dao.addBookingToTimetable(connection, 1, 1);
        assertFalse(actual);

        when(ps.executeUpdate()).thenThrow(new SQLException());
        actual = dao.addBookingToTimetable(connection, 1, 1);
        assertFalse(actual);

    }

}
