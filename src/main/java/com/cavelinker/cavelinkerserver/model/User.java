package com.cavelinker.cavelinkerserver.model;

import com.cavelinker.cavelinkerserver.enums.ContactType;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long user_ID;
    @Column(unique=true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private ContactType contactType;
    private String contactUserName;
    @OneToMany(mappedBy = "user")
    private List<Activity> activities;

    public User(){}
    public User(String email, String password, ContactType contactType, String contactUserName) {
        this.email=email;
        this.password=password;
        this.contactType=contactType;
        this.contactUserName=contactUserName;
    }

    public Long getUser_ID() {return user_ID;}
    public void setUser_ID(Long user_ID) {this.user_ID = user_ID;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public ContactType getContactType() {return contactType;}
    public void setContactType(ContactType contactType) {this.contactType = contactType;}
    public String getContactUserName() {return contactUserName;}
    public void setContactUserName(String contactUserName) {this.contactUserName = contactUserName;}

}
