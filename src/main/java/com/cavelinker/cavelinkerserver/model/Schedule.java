package com.cavelinker.cavelinkerserver.model;

import jakarta.persistence.*;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long schedule_ID;
    private String day;
    @ManyToOne
    @JoinColumn(name="activity_ID")
    private Activity activity;

    public Long getSchedule_ID() {return schedule_ID;}
    public void setSchedule_ID(Long schedule_ID) {this.schedule_ID = schedule_ID;}

    public String getDay() {return day;}
    public void setDay(String day) {this.day = day;}

}
