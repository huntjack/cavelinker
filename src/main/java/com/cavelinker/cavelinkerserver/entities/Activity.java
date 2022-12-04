package com.cavelinker.cavelinkerserver.entities;

import com.cavelinker.cavelinkerserver.enums.ActivityType;
import com.cavelinker.cavelinkerserver.enums.ServerName;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long activity_Id;
    private String gamerTag;
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;
    @Enumerated(EnumType.STRING)
    private ServerName serverName;
    private String activityMessage;
    @OneToMany(mappedBy = "activity")
    private List<Schedule> schedules;
    @ManyToOne
    @JoinColumn(name="user_Id")
    private User user;

    public Long getActivity_Id() {return activity_Id;}
    public void setActivity_Id(Long activity_Id) {this.activity_Id = activity_Id;}

    public String getGamerTag() {return gamerTag;}
    public void setGamerTag(String gamerTag) {this.gamerTag = gamerTag;}

    public ActivityType getActivityType() {return activityType;}
    public void setActivityType(ActivityType activityType) {this.activityType = activityType;}

    public ServerName getServerName() {return serverName;}
    public void setServerName(ServerName serverName) {this.serverName = serverName;}

    public String getActivityMessage() {return activityMessage;}
    public void setActivityMessage(String activityMessage) {this.activityMessage = activityMessage;}

    public void setUser(User user) {this.user = user;}
}
