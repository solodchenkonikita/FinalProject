package com.epam.dao;

import com.epam.dao.impl.ClientBookingDaoImpl;
import com.epam.entity.*;
import com.epam.exception.DBException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientBookingDaoImplTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet rs;

    @Before
    public void SetUp() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);

    }

    @Test
    public void getTimetableWithUserBookingTest() throws SQLException, DBException {

        ClientBookingDaoImpl dao = new ClientBookingDaoImpl();
        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);
        when(rs.getDate(anyInt())).thenReturn(Date.valueOf("2020-10-10"));
        when(rs.getString(anyInt())).thenReturn("11:00:00");
        when(rs.getDouble(anyInt())).thenReturn(1.1);

        List<Timetable> list = dao.getTimetableWithUserBooking(connection, 1, 1, 1);
        assertEquals(list.size(), 1);
    }

    @Test(expected = DBException.class)
    public void getTimetableWithUserBookingTest_ThrowDBException() throws SQLException, DBException {
        ClientBookingDaoImpl dao = new ClientBookingDaoImpl();
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getTimetableWithUserBooking(connection, 1, 1, 1);

    }

    @Test
    public void getUserBookingCountTest() throws SQLException, DBException {
        ClientBookingDaoImpl dao = new ClientBookingDaoImpl();
        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);

        int actual = dao.getUserBookingCount(connection, 1);
        int expected = 1;
        assertEquals(actual, expected);

    }

    @Test(expected = DBException.class)
    public void getUserBookingCountTest_ThrowDBException() throws SQLException, DBException {
        ClientBookingDaoImpl dao = new ClientBookingDaoImpl();
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getUserBookingCount(connection, 1);

    }

    @Test
    public void setMarkInBookingTest() throws SQLException {
        ClientBookingDaoImpl dao = new ClientBookingDaoImpl();
        when(ps.executeUpdate()).thenReturn(1);
        boolean actual = dao.setMarkInBooking(connection, 1, 1);
        assertTrue(actual);

        when(ps.executeUpdate()).thenReturn(0);
        actual = dao.setMarkInBooking(connection, 1, 1);
        assertFalse(actual);

        when(ps.executeUpdate()).thenThrow(new SQLException());
        actual = dao.setMarkInBooking(connection, 1, 1);
        assertFalse(actual);

    }

    @Test
    public void masterMarksTest() throws SQLException, DBException {
        ClientBookingDaoImpl dao = new ClientBookingDaoImpl();
        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);

        List<Integer> list = dao.masterMarks(connection, 1);
        int expected = 1;
        assertEquals(list.size(), expected);

    }

    @Test(expected = DBException.class)
    public void masterMarksTest_ThrowDBException() throws SQLException, DBException {
        ClientBookingDaoImpl dao = new ClientBookingDaoImpl();
        when(ps.execute()).thenThrow(new SQLException());
        dao.masterMarks(connection, 1);
    }

    @Test
    public void setMarkToMasterTest() throws SQLException {
        ClientBookingDaoImpl dao = new ClientBookingDaoImpl();
        when(ps.executeUpdate()).thenReturn(1);
        boolean actual = dao.setMarkToMaster(connection, 1, 1);
        assertTrue(actual);

        when(ps.executeUpdate()).thenReturn(0);
        actual = dao.setMarkToMaster(connection, 1, 1);
        assertFalse(actual);

        when(ps.executeUpdate()).thenThrow(new SQLException());
        actual = dao.setMarkToMaster(connection, 1, 1);
        assertFalse(actual);

    }

    @Test
    public void setCommentTest() throws SQLException {
        ClientBookingDaoImpl dao = new ClientBookingDaoImpl();
        when(ps.executeUpdate()).thenReturn(1);
        boolean actual = dao.setComment(connection, 1, "1");
        assertTrue(actual);

        when(ps.executeUpdate()).thenReturn(0);
        actual = dao.setComment(connection, 1, "1");
        assertFalse(actual);

        when(ps.executeUpdate()).thenThrow(new SQLException());
        actual = dao.setComment(connection, 1, "1");
        assertFalse(actual);

    }

}
