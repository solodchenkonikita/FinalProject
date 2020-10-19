package com.epam.dao.impl;

import com.epam.dao.PriceListDao;
import com.epam.entity.Service;
import com.epam.exception.DBException;
import com.epam.exception.Messages;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of PriceListDao interface.
 *
 * @author Solodchenko Nikita
 *
 */
public class PriceListDaoImpl implements PriceListDao {

    private final static Logger LOG = Logger.getLogger(PriceListDaoImpl.class);

    private static final String SQL_GET_ALL_SERVICES = "SELECT * FROM service ";
    private static final String SQL_GET_CHOSEN_NUMBER_OF_SERVICES = "SELECT * FROM service ORDER BY id LIMIT ?,? ";
    private static final String SQL_GET_COUNT_OF_SERVICE = "SELECT count(*) FROM service ";
    private static final String SQL_GET_MASTER_SERVICES =
            "SELECT service.id, service.service_name FROM beauty_salon.master_has_service " +
                    "LEFT JOIN beauty_salon.service ON master_has_service.service_id=service.id " +
                    "WHERE user_id=? ";

    @Override
    public List<Service> getAllServices(Connection connection) throws DBException {
        List<Service> services = new ArrayList<>();
        Statement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.createStatement();
            ps.executeQuery(SQL_GET_ALL_SERVICES);
            rs = ps.getResultSet();
            while (rs.next()) {
                Service service = new Service(rs.getString(2), rs.getDouble(3));
                service.setId(rs.getInt(1));
                services.add(service);
            }
        } catch (SQLException ex) {
            LOG.error("Cannot obtain chosen number of services");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_SERVICES);
        } finally {
            close(rs);
            close(ps);
        }
        return services;
    }

    @Override
    public List<Service> getChosenNumberOfServices(Connection connection, int start, int limit) throws DBException {
        List<Service> services = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_CHOSEN_NUMBER_OF_SERVICES);
            ps.setInt(1, start);
            ps.setInt(2, limit);
            ps.executeQuery();
            rs = ps.getResultSet();
            while (rs.next()) {
                Service service = new Service(rs.getString(2), rs.getDouble(3));
                service.setId(rs.getInt(1));
                services.add(service);
            }
        } catch (SQLException ex) {
            LOG.error("Cannot obtain chosen number of services");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_SERVICES);
        } finally {
            close(rs);
            close(ps);
        }
        return services;
    }

    @Override
    public int getCountOfService(Connection connection) throws DBException {
        int count = 0;
        Statement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.createStatement();
            ps.executeQuery(SQL_GET_COUNT_OF_SERVICE);
            rs = ps.getResultSet();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            LOG.error("Cannot obtain count of services");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_COUNT_OF_SERVICES);
        } finally {
            close(rs);
            close(ps);
        }
        return count;
    }

    @Override
    public List<Service> getServicesByMaster(Connection connection, int masterId) throws DBException {
        List<Service> services = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(SQL_GET_MASTER_SERVICES);
            ps.setInt(1, masterId);
            ps.executeQuery();
            rs = ps.getResultSet();
            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt(1));
                service.setServiceName(rs.getString(2));
                services.add(service);
            }
        } catch (SQLException ex) {
            LOG.error("Cannot obtain master services");
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_MASTER_SERVICES);
        } finally {
            close(rs);
            close(ps);
        }
        return services;
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
