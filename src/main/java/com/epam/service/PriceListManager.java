package com.epam.service;

import com.epam.entity.Service;
import com.epam.exception.DBException;

import java.util.List;

/**
 * Price list service interface.
 *
 * @author Solodchenko Nikita
 *
 */
public interface PriceListManager {

    /**
     * Get all services.
     *
     * @return list of services.
     */
    List<Service> getAllServices() throws DBException;

    /**
     * Get part of the services.
     *
     * @param start start position of services.
     * @param limit limit for getting number of services.
     * @return list of services.
     */
    List<Service> getChosenNumberOfServices(int start, int limit) throws DBException;

    /**
     * Get services count.
     *
     * @return count of services.
     */
    int getCountOfService() throws DBException;

    /**
     * Get master services.
     *
     * @param masterId specified master id.
     * @return list of services.
     */
    List<Service> getServicesByMaster(int masterId) throws DBException;
}
