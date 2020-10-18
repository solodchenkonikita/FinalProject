package com.epam.dao;

import com.epam.dao.impl.UserDaoImpl;
import com.epam.entity.User;
import com.epam.exception.DBException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement ps;
    @Mock
    private Statement stm;
    @Mock
    private ResultSet rs;

    private UserDaoImpl dao;

    @Before
    public void SetUp() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        dao = new UserDaoImpl();
    }

    @Test
    public void addUserTest() throws DBException, SQLException {
        User user = new User("test@gmail.com", "test", "test", "test", "test");
        dao.addUser(connection, user);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(ps, times(5)).setString(anyInt(), anyString());
        verify(ps, times(1)).execute();
    }

    @Test(expected = DBException.class)
    public void addUserTest_ThrowDBException() throws SQLException, DBException {
        User user = new User("test@gmail.com", "test", "test", "test", "test");
        when(ps.execute()).thenThrow(new SQLException());
        dao.addUser(connection, user);

    }

    @Test
    public void findUserByEmailTest() throws SQLException, DBException {

        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);
        when(rs.getString(anyInt())).thenReturn("test");

        User user = dao.findUserByEmail(connection, "test");
        assertNotNull(user);
    }

    @Test(expected = DBException.class)
    public void findUserByEmailTest_ThrowDBException() throws SQLException, DBException {
        when(ps.execute()).thenThrow(new SQLException());
        dao.findUserByEmail(connection, "test");

    }

    @Test
    public void findUserEmailByEmailTest() throws SQLException, DBException {

        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getString(anyInt())).thenReturn("test");

        String result = dao.findUserEmailByEmail(connection, "test");
        assertNotNull(result);
    }

    @Test(expected = DBException.class)
    public void findUserEmailByEmailTest_ThrowDBException() throws SQLException, DBException {
        when(ps.execute()).thenThrow(new SQLException());
        dao.findUserEmailByEmail(connection, "test");

    }

    @Test
    public void findAllMastersTest() throws SQLException, DBException {

        when(connection.createStatement()).thenReturn(stm);
        when(stm.executeQuery(anyString())).thenReturn(rs);
        when(stm.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);
        when(rs.getString(anyInt())).thenReturn("test");

        List<User> list = dao.findAllMasters(connection);
        assertEquals(list.size(), 1);
    }

    @Test(expected = DBException.class)
    public void findAllMastersTest_ThrowDBException() throws SQLException, DBException {
        when(connection.createStatement()).thenReturn(stm);
        when(stm.executeQuery(anyString())).thenThrow(new SQLException());
        dao.findAllMasters(connection);

    }

    @Test
    public void getUsersWithBookingByDateTest() throws SQLException {

        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);
        when(rs.getString(anyInt())).thenReturn("11:00:00");

        List<User> list = dao.getUsersWithBookingByDate(connection, Date.valueOf("2020-10-10"));
        assertEquals(list.size(), 1);
    }

}
