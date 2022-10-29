package com.cavelinker.cavelinkerserver;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long message_ID;
    private StringBuilder gamerTag;
    private StringBuilder activityMessage;
    @OneToMany(mappedBy = "message")
    private List<Schedule> schedules;
    @ManyToOne
    @JoinColumn(name="user_ID")
    private User user;

    public Long getMessage_ID() {return message_ID;}
    public void setMessage_ID(Long message_ID) {this.message_ID = message_ID;}

    public StringBuilder getGamerTag() {return gamerTag;}
    public void setGamerTag(StringBuilder gamerTag) {this.gamerTag = gamerTag;}

    public StringBuilder getActivityMessage() {return activityMessage;}
    public void setActivityMessage(StringBuilder activityMessage) {this.activityMessage = activityMessage;}
}
