package com.epam.entity;

/**
 * Booking entity.
 *
 * @author Solodchenko Nikita
 *
 */
public class Booking extends Entity {

    private static final long serialVersionUID = -4045566276948667467L;

    private Service service;
    private User client;
    private int mark;
    private String progressStatus;
    private String paymentStatus;
    private String comment;


    public Booking() {
    }

    public Booking(Service service, User client) {
        this.service = service;
        this.client = client;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Booking[service=" + service + ", client=" + client + ", mark=" + mark +
                ", progressStatus='" + progressStatus + ", paymentStatus='" + paymentStatus +
                ", comment='" + comment + ']';
    }
}
