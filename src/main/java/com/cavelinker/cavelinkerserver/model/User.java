package com.cavelinker.cavelinkerserver.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long user_ID;
    private StringBuilder email;
    private StringBuilder password;
    private String contactType;
    private StringBuilder contactInfo;
    @OneToMany(mappedBy = "user")
    private List<Message> messages;

    User(){}
    User(StringBuilder email, StringBuilder passowrd, String contactType, StringBuilder contactInfo) {
        this.email=email;
        this.password=password;
        this.contactType=contactType;
        this.contactInfo=contactInfo;
    }

    public Long getUser_ID() {return user_ID;}

    public StringBuilder getEmail() {return email;}
    public void setEmail(StringBuilder email) {this.email = email;}

    public StringBuilder getPassword() {return password;}
    public void setPassword(StringBuilder password) {password = password;}

    public String getContactType() {return contactType;}
    public void setContactType(String contactType) {this.contactType = contactType;}

    public StringBuilder getContactInfo() {return contactInfo;}
    public void setContactInfo(StringBuilder contactInfo) {this.contactInfo = contactInfo;}
}
