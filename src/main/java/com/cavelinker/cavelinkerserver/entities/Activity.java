package com.cavelinker.cavelinkerserver.entities;

import com.cavelinker.cavelinkerserver.enums.ActivityType;
import com.cavelinker.cavelinkerserver.enums.ServerName;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "activityId",
        scope = Activity.class)
@NamedQuery(name="getActivityWithSchedules",
        query="SELECT activity FROM Activity activity " +
                "JOIN FETCH activity.schedules " +
                "WHERE activity.activityId = :activityId")
@NamedQuery(name = "findMatchingActivities",
        query = "SELECT activity FROM Activity activity " +
        "JOIN activity.schedules schedule " +
        "WHERE activity.activityType = :activityType AND " +
        "activity.serverName = :serverName AND " +
        "schedule.startTimeUtc <= :oneHourLessThanEndTime AND " +
        "schedule.endTimeUtc >= :oneHourMoreThanStartTime")
public class Activity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique = true, updatable = false, nullable = false)
    private Long activityId;
    @Column(unique=true, updatable = false, nullable = false)
    private String activityBusinessKey;
    private String gamerTag;
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;
    @Enumerated(EnumType.STRING)
    private ServerName serverName;
    private String activityMessage;
    @OneToMany(mappedBy = "activity", cascade = {CascadeType.DETACH, CascadeType.MERGE},  orphanRemoval = true)
    private List<Schedule> schedules;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    public void addSchedule(Schedule schedule) {
        schedule.setActivity(this);
        schedules.add(schedule);
    }
    public void removeSchedule(Schedule schedule) {
        schedules.remove(schedule);
        schedule.setActivity(null);
    }

    @Override
    public boolean equals(Object object) {
        if(this==object){
            return true;
        }
        if(object==null) {
            return false;
        }
        if(getClass() != object.getClass()) {
            return false;
        }
        Activity inputActivity = (Activity)object;
        return Objects.equals(activityBusinessKey, inputActivity.getActivityBusinessKey());
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(activityBusinessKey);
    }

    public Activity(String activityBusinessKey, String gamerTag, ActivityType activityType, ServerName serverName, String activityMessage) {
        this.activityBusinessKey=activityBusinessKey;
        this.gamerTag=gamerTag;
        this.activityType=activityType;
        this.serverName=serverName;
        this.activityMessage=activityMessage;
    }
    public Activity() {}

    public Long getActivityId() {return activityId;}
    public void setActivityId(Long activityId) {this.activityId = activityId;}

    public String getActivityBusinessKey() {return activityBusinessKey;}

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

    public List<Schedule> getSchedules() {return schedules;}
    public void setSchedules(List<Schedule> schedules) {this.schedules = schedules;}
}
