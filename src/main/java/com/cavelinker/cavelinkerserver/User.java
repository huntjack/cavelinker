package com.cavelinker.cavelinkerserver;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name="user_ID", updatable = false, nullable = false)
    private Long user_ID;
    private String email;
    private String password;
    private String contactType;
    private StringBuilder contactInfo;

    public Long getUser_ID() {return user_ID;}
    public void setUser_ID(Long user_ID) {this.user_ID = user_ID;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {password = password;}

    public String getContactType() {return contactType;}
    public void setContactType(String contactType) {this.contactType = contactType;}

    public StringBuilder getContactInfo() {return contactInfo;}
    public void setContactInfo(StringBuilder contactInfo) {this.contactInfo = contactInfo;}
}
