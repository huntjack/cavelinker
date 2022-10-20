package com.cavelinker.cavelinkerserver;

import jakarta.persistence.*;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

import java.time.ZonedDateTime;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(updatable = false, nullable = false)
    private Long schedule_ID;
    private String day;

    private String activity;
    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    private ZonedDateTime startTime;
    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    private ZonedDateTime endTime;

    @ManyToOne
    @JoinColumn(name="user_ID")
    private User user;
    @ManyToOne
    @JoinColumn(name="message_ID")
    private Message message;

    public Long getSchedule_ID() {return schedule_ID;}
    public void setSchedule_ID(Long schedule_ID) {this.schedule_ID = schedule_ID;}

    public String getDay() {return day;}
    public void setDay(String day) {this.day = day;}

    public String getActivity() {return activity;}
    public void setActivity(String activity) {this.activity = activity;}

    public ZonedDateTime getStartTime() {return startTime;}
    public void setStartTime(ZonedDateTime startTime) {this.startTime = startTime;}

    public ZonedDateTime getEndTime() {return endTime;}
    public void setEndTime(ZonedDateTime endTime) {this.endTime = endTime;}

}
