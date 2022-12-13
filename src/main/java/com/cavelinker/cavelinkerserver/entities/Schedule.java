package com.cavelinker.cavelinkerserver.entities;

import com.cavelinker.cavelinkerserver.converters.ZoneIdConverter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "scheduleId")
public class Schedule implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long scheduleId;
    private LocalDateTime startTimeUtc;
    private LocalDateTime endTimeUtc;
    @Convert(converter = ZoneIdConverter.class)
    private ZoneId userTimeZone;
    @ManyToOne
    @JoinColumn(name="activityId")
    private Activity activity;

    public Long getScheduleId() {return scheduleId;}
    public void setScheduleId(Long scheduleId) {this.scheduleId = scheduleId;}
    public LocalDateTime getStartTimeUtc() {return startTimeUtc;}
    public void setStartTimeUtc(LocalDateTime startTimeUtc) {this.startTimeUtc = startTimeUtc;}

    public LocalDateTime getEndTimeUtc() {return endTimeUtc;}
    public void setEndTimeUtc(LocalDateTime endTimeUtc) {this.endTimeUtc = endTimeUtc;}

    public ZoneId getUserTimeZone() {return userTimeZone;}
    public void setUserTimeZone(ZoneId userTimeZone) {this.userTimeZone = userTimeZone;}

}
