package com.epam.dao;

import com.epam.dao.impl.PriceListDaoImpl;
import com.epam.entity.Service;
import com.epam.exception.DBException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PriceListDaoImplTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement ps;
    @Mock
    private Statement stm;
    @Mock
    private ResultSet rs;

    private PriceListDaoImpl dao;

    @Before
    public void SetUp() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(connection.createStatement()).thenReturn(stm);
        dao = new PriceListDaoImpl();
    }

    @Test
    public void getAllServicesTest() throws SQLException, DBException {

        when(stm.executeQuery(anyString())).thenReturn(rs);
        when(stm.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);
        when(rs.getDate(anyInt())).thenReturn(Date.valueOf("2020-10-10"));
        when(rs.getString(anyInt())).thenReturn("11:00:00");

        List<Service> list = dao.getAllServices(connection);
        assertEquals(list.size(), 1);
    }

    @Test(expected = DBException.class)
    public void getAllServicesTest_ThrowDBException() throws SQLException, DBException {
        when(stm.executeQuery(anyString())).thenThrow(new SQLException());
        dao.getAllServices(connection);

    }

    @Test
    public void getChosenNumberOfServicesTest() throws SQLException, DBException {

        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);
        when(rs.getString(anyInt())).thenReturn("11:00:00");

        List<Service> list = dao.getChosenNumberOfServices(connection, 1, 1);
        assertEquals(list.size(), 1);
    }

    @Test(expected = DBException.class)
    public void getChosenNumberOfServicesTest_ThrowDBException() throws SQLException, DBException {
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getChosenNumberOfServices(connection, 1, 1);

    }

    @Test
    public void getCountOfServiceTest() throws SQLException, DBException {
        when(stm.executeQuery(anyString())).thenReturn(rs);
        when(stm.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);

        int actual = dao.getCountOfService(connection);
        int expected = 1;
        assertEquals(actual, expected);

    }

    @Test(expected = DBException.class)
    public void getCountOfServiceTest_ThrowDBException() throws SQLException, DBException {
        when(stm.executeQuery(anyString())).thenThrow(new SQLException());
        dao.getCountOfService(connection);
    }

    @Test
    public void getServicesByMasterTest() throws SQLException, DBException {

        when(ps.executeQuery()).thenReturn(rs);
        when(ps.getResultSet()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);

        when(rs.getInt(anyInt())).thenReturn(1);
        when(rs.getString(anyInt())).thenReturn("11:00:00");

        List<Service> list = dao.getServicesByMaster(connection, 1);
        assertEquals(list.size(), 1);
    }

    @Test(expected = DBException.class)
    public void getServicesByMasterTest_ThrowDBException() throws SQLException, DBException {
        when(ps.executeQuery()).thenThrow(new SQLException());
        dao.getServicesByMaster(connection, 1);

    }

}
