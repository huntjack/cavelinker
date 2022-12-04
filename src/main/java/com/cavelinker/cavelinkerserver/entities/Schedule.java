package com.cavelinker.cavelinkerserver.entities;

import com.cavelinker.cavelinkerserver.converters.ZoneIdConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long schedule_Id;
    private LocalDateTime startTime_Utc;
    private LocalDateTime endTime_Utc;
    @Convert(converter = ZoneIdConverter.class)
    private ZoneId userTimeZone;
    @ManyToOne
    @JoinColumn(name="activity_Id")
    private Activity activity;

    public Long getSchedule_Id() {return schedule_Id;}
    public void setSchedule_Id(Long schedule_Id) {this.schedule_Id = schedule_Id;}
    public LocalDateTime getStartTimeUtc() {return startTime_Utc;}
    public void setStartTimeUtc(LocalDateTime startTime_Utc) {this.startTime_Utc = startTime_Utc;}

    public LocalDateTime getEndTimeUtc() {return endTime_Utc;}
    public void setEndTimeUtc(LocalDateTime endTime_Utc) {this.endTime_Utc = endTime_Utc;}

    public ZoneId getUserTimeZone() {return userTimeZone;}
    public void setUserTimeZone(ZoneId userTimeZone) {this.userTimeZone = userTimeZone;}

}
