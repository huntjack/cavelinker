package com.cavelinker.cavelinkerserver.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long message_ID;
    private String gamerTag;
    private String activityMessage;
    @OneToMany(mappedBy = "message")
    private List<Schedule> schedules;
    @ManyToOne
    @JoinColumn(name="user_ID")
    private User user;

    public Long getMessage_ID() {return message_ID;}
    public void setMessage_ID(Long message_ID) {this.message_ID = message_ID;}

    public String getGamerTag() {return gamerTag;}
    public void setGamerTag(String gamerTag) {this.gamerTag = gamerTag;}

    public String getActivityMessage() {return activityMessage;}
    public void setActivityMessage(String activityMessage) {this.activityMessage = activityMessage;}
}
