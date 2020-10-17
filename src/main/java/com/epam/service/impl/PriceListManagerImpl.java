package com.epam.service.impl;

import com.epam.dao.PriceListDao;
import com.epam.dao.impl.PriceListDaoImpl;
import com.epam.entity.Service;
import com.epam.exception.DBException;
import com.epam.service.PriceListManager;

import java.util.List;

public class PriceListManagerImpl implements PriceListManager {

    PriceListDao priceListDao = new PriceListDaoImpl();

    @Override
    public List<Service> getAllServices() throws DBException {
        return priceListDao.getAllServices();
    }

    @Override
    public List<Service> getChosenNumberOfServices(int start, int limit) throws DBException {
        return priceListDao.getChosenNumberOfServices(start, limit);
    }

    @Override
    public int getCountOfService() throws DBException {
        return priceListDao.getCountOfService();
    }

    @Override
    public List<Service> getServicesByMaster(int masterId) throws DBException {
        return priceListDao.getServicesByMaster(masterId);
    }
}
