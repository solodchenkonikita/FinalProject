package com.epam.entity;

/**
 * Service entity.
 *
 * @author Solodchenko Nikita
 *
 */
public class Service extends Entity {

    private static final long serialVersionUID = 4698591346848613263L;

    private String serviceName;
    private double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public Service() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Service [serviceName='" + serviceName + ", price=" + price + ']';
    }
}
