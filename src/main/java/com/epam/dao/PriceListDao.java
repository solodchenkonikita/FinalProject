package com.epam.dao;

import com.epam.entity.Service;
import com.epam.exception.DBException;

import java.sql.Connection;
import java.util.List;

/**
 * Price list dao interface.
 *
 * @author Nikita Solodchenko
 *
 */
public interface PriceListDao {

    /**
     * Get all services.
     *
     * @param connection connection to database.
     * @return list of services.
     */
    List<Service> getAllServices(Connection connection) throws DBException;

    /**
     * Get part of the services.
     *
     * @param connection connection to database.
     * @param start start position of services.
     * @param limit limit for getting number of services.
     * @return list of services.
     */
    List<Service> getChosenNumberOfServices(Connection connection, int start, int limit) throws DBException;

    /**
     * Get services count.
     *
     * @param connection connection to database.
     * @return count of services.
     */
    int getCountOfService(Connection connection) throws DBException;

    /**
     * Get master services.
     *
     * @param connection connection to database.
     * @param masterId specified master id.
     * @return list of services.
     */
    List<Service> getServicesByMaster(Connection connection, int masterId) throws DBException;
}
