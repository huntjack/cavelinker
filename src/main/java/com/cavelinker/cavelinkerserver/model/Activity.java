package com.cavelinker.cavelinkerserver.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long activity_ID;
    private String activity;
    private String gamerTag;
    private String activityMessage;
    @OneToMany(mappedBy = "activity")
    private List<Schedule> schedules;
    @ManyToOne
    @JoinColumn(name="user_ID")
    private User user;

    public Long getActivity_ID() {return activity_ID;}
    public void setActivity_ID(Long activity_ID) {this.activity_ID = activity_ID;}

    public String getGamerTag() {return gamerTag;}
    public void setGamerTag(String gamerTag) {this.gamerTag = gamerTag;}

    public String getActivityMessage() {return activityMessage;}
    public void setActivityMessage(String activityMessage) {this.activityMessage = activityMessage;}
}
