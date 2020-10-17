package com.epam.entity;

import java.sql.Date;

public class Timetable extends Entity {
    private static final long serialVersionUID = -6033724926184323065L;

    private Date date;
    private Timeslot timeslot;
    private User master;
    private Booking booking;

    public Timetable() {
    }

    public Timetable(Date date, Timeslot timeslot, User master) {
        this.date = date;
        this.timeslot = timeslot;
        this.master = master;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return "Timetable[date=" + date + ", timeslot=" + timeslot + ", master=" + master +
                ", booking=" + booking + ']';
    }
}
