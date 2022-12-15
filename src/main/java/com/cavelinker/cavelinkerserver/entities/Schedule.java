package com.cavelinker.cavelinkerserver.entities;

//import com.cavelinker.cavelinkerserver.converters.ZoneIdConverter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "scheduleId")
public class Schedule implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique = true, updatable = false, nullable = false)
    private Long scheduleId;
    @Column(unique=true, updatable = false, nullable = false)
    private String scheduleBusinessKey;
    private LocalDateTime startTimeUtc;
    private LocalDateTime endTimeUtc;
    private ZoneId userTimeZone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="activityId")
    private Activity activity;

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
        Schedule inputSchedule = (Schedule) object;
        return Objects.equals(scheduleBusinessKey, inputSchedule.getScheduleBusinessKey());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(scheduleBusinessKey);
    }

    public Schedule(String scheduleBusinessKey, LocalDateTime startTimeUtc, LocalDateTime endTimeUtc, ZoneId userTimeZone) {
        this.scheduleBusinessKey = scheduleBusinessKey;
        this.startTimeUtc = startTimeUtc;
        this.endTimeUtc = endTimeUtc;
        this.userTimeZone = userTimeZone;
    }
    public Schedule() {}

    public Long getScheduleId() {return scheduleId;}
    public void setScheduleId(Long scheduleId) {this.scheduleId = scheduleId;}

    public String getScheduleBusinessKey() {return scheduleBusinessKey;}

    public LocalDateTime getStartTimeUtc() {return startTimeUtc;}
    public void setStartTimeUtc(LocalDateTime startTimeUtc) {this.startTimeUtc = startTimeUtc;}

    public LocalDateTime getEndTimeUtc() {return endTimeUtc;}
    public void setEndTimeUtc(LocalDateTime endTimeUtc) {this.endTimeUtc = endTimeUtc;}

    public ZoneId getUserTimeZone() {return userTimeZone;}
    public void setUserTimeZone(ZoneId userTimeZone) {this.userTimeZone = userTimeZone;}

    public Activity getActivity() {return activity;}
    public void setActivity(Activity activity) {this.activity = activity;}

}
