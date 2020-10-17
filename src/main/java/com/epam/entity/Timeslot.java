package com.epam.entity;

import java.sql.Time;

public class Timeslot extends Entity {

    private static final long serialVersionUID = 2121867589480993070L;

    private Time startTime;
    private Time endTime;

    public Timeslot(Time startTime, Time endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Timeslot() {
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Timeslot [startTime=" + startTime + ", endTime=" + endTime + ']';
    }
}
