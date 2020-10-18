package com.epam.dao;

import com.epam.entity.Service;
import com.epam.exception.DBException;

import java.sql.Connection;
import java.util.List;

/**
 * Work with price list in database
 *
 * @author Nikita Solodchenko
 *
 */
public interface PriceListDao {

    /**
     * Get services from database.
     *
     * @return all services.
     */
    List<Service> getAllServices(Connection connection) throws DBException;

    /**
     * Get services from database.
     *
     * @param start start
     * @param limit limit
     * @return all services.
     */
    List<Service> getChosenNumberOfServices(Connection connection, int start, int limit) throws DBException;

    int getCountOfService(Connection connection) throws DBException;

    List<Service> getServicesByMaster(Connection connection, int masterId) throws DBException;
}
