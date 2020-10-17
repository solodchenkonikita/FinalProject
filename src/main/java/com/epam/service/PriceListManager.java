package com.epam.service;

import com.epam.entity.Service;
import com.epam.exception.DBException;

import java.util.List;

public interface PriceListManager {

    /**
     * Get services from database.
     *
     * @return all services.
     */
    List<Service> getAllServices() throws DBException;

    List<Service> getChosenNumberOfServices(int start, int limit) throws DBException;

    int getCountOfService() throws DBException;

    List<Service> getServicesByMaster(int masterId) throws DBException;
}
