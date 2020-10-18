package com.epam.service.impl;

import com.epam.dao.PriceListDao;
import com.epam.dao.impl.PriceListDaoImpl;
import com.epam.entity.Service;
import com.epam.exception.DBException;
import com.epam.service.PriceListManager;
import com.epam.util.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PriceListManagerImpl implements PriceListManager {

    PriceListDao priceListDao = new PriceListDaoImpl();

    @Override
    public List<Service> getAllServices() throws DBException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            return priceListDao.getAllServices(connection);
        } finally {
            close(connection);
        }
    }

    @Override
    public List<Service> getChosenNumberOfServices(int start, int limit) throws DBException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            return priceListDao.getChosenNumberOfServices(connection, start, limit);
        } finally {
            close(connection);
        }
    }

    @Override
    public int getCountOfService() throws DBException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            return priceListDao.getCountOfService(connection);
        } finally {
            close(connection);
        }
    }

    @Override
    public List<Service> getServicesByMaster(int masterId) throws DBException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            return priceListDao.getServicesByMaster(connection, masterId);
        } finally {
            close(connection);
        }
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
}
