package com.epam.dao;

import com.epam.dao.impl.MasterTimetableDaoImpl;
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
public class MasterTimetableDaoImplTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet rs;

    private MasterTimetableDaoImpl dao;

    @Before
    public void SetUp() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        dao = new MasterTimetableDaoImpl();
    }

    @Test
    public void getMasterTimetableByDateTest() throws SQLException, DBException {

        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);
        when(rs.getDate(anyInt())).thenReturn(Date.valueOf("2020-10-10"));
        when(rs.getString(anyInt())).thenReturn("11:00:00");

        List<Timetable> list = dao.getMasterTimetableByDate(connection, Date.valueOf("2020-10-10"), 1);
        assertEquals(list.size(), 1);
    }

    @Test(expected = DBException.class)
    public void getTimetableWithBookingByDateTest_ThrowDBException() throws SQLException, DBException {
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getMasterTimetableByDate(connection, Date.valueOf("2020-10-10"), 1);

    }

    @Test
    public void setBookingDoneTest() throws SQLException {
        when(ps.executeUpdate()).thenReturn(1);
        boolean actual = dao.setBookingDone(connection, 1);
        assertTrue(actual);

        when(ps.executeUpdate()).thenReturn(0);
        actual = dao.setBookingDone(connection, 1);
        assertFalse(actual);

        when(ps.executeUpdate()).thenThrow(new SQLException());
        actual = dao.setBookingDone(connection, 1);
        assertFalse(actual);

    }

}
