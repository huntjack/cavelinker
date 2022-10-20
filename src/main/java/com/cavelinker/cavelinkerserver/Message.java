package com.cavelinker.cavelinkerserver;

import jakarta.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name="message_ID", updatable = false, nullable = false)
    private Long message_ID;
    private StringBuilder gamerTag;
    private StringBuilder message;

    public Long getMessage_ID() {return message_ID;}
    public void setMessage_ID(Long message_ID) {this.message_ID = message_ID;}

    public StringBuilder getGamerTag() {return gamerTag;}
    public void setGamerTag(StringBuilder gamerTag) {this.gamerTag = gamerTag;}

    public StringBuilder getMessage() {return message;}
    public void setMessage(StringBuilder message) {this.message = message;}
}
