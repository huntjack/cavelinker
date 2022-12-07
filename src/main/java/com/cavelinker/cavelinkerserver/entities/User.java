package com.cavelinker.cavelinkerserver.entities;

import com.cavelinker.cavelinkerserver.enums.ContactType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId")
@NamedQuery(name="findUserByEmail",
        query="SELECT DISTINCT user FROM User user WHERE user.email= :email")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long userId;
    @Column(unique=true)
    private String email;
    @Enumerated(EnumType.STRING)
    private ContactType contactType;
    private String contactUserName;

    @OneToMany(mappedBy = "user")
    private List<Activity> activities;

    public void addActivityMapping(Activity activity) {
        this.activities.add(activity);
        activity.setUser(this);
    }
    public User(){}
    public User(String email, ContactType contactType, String contactUserName) {
        this.email=email;
        this.contactType=contactType;
        this.contactUserName=contactUserName;
    }

    public Long getUserId() {return userId;}
    public void setUserId(Long userId) {this.userId = userId;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public ContactType getContactType() {return contactType;}
    public void setContactType(ContactType contactType) {this.contactType = contactType;}
    public String getContactUserName() {return contactUserName;}
    public void setContactUserName(String contactUserName) {this.contactUserName = contactUserName;}

    public List<Activity> getActivities() {return activities;}
    public void setActivities(List<Activity> activities) {this.activities = activities;}

}
