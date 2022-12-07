package com.cavelinker.cavelinkerserver.entities;

import com.cavelinker.cavelinkerserver.enums.ActivityType;
import com.cavelinker.cavelinkerserver.enums.ServerName;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "activityId")
public class Activity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long activityId;
    private String gamerTag;
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;
    @Enumerated(EnumType.STRING)
    private ServerName serverName;
    private String activityMessage;
    @OneToMany(mappedBy = "activity")
    private List<Schedule> schedules;
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    public Activity() {}
    public Activity(String gamerTag, ActivityType activityType, ServerName serverName, String activityMessage) {
        this.gamerTag=gamerTag;
        this.activityType=activityType;
        this.serverName=serverName;
        this.activityMessage=activityMessage;
    }

    public Long getActivityId() {return activityId;}
    public void setActivityId(Long activityId) {this.activityId = activityId;}

    public String getGamerTag() {return gamerTag;}
    public void setGamerTag(String gamerTag) {this.gamerTag = gamerTag;}

    public ActivityType getActivityType() {return activityType;}
    public void setActivityType(ActivityType activityType) {this.activityType = activityType;}

    public ServerName getServerName() {return serverName;}
    public void setServerName(ServerName serverName) {this.serverName = serverName;}

    public String getActivityMessage() {return activityMessage;}
    public void setActivityMessage(String activityMessage) {this.activityMessage = activityMessage;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
}
