package com.cavelinker.cavelinkerserver;

import jakarta.persistence.*;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long schedule_ID;
    private String day;
    private String activity;
    @ManyToOne
    @JoinColumn(name="message_ID")
    private Message message;

    public Long getSchedule_ID() {return schedule_ID;}
    public void setSchedule_ID(Long schedule_ID) {this.schedule_ID = schedule_ID;}

    public String getDay() {return day;}
    public void setDay(String day) {this.day = day;}

    public String getActivity() {return activity;}
    public void setActivity(String activity) {this.activity = activity;}

}
