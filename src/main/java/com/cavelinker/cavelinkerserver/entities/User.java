package com.cavelinker.cavelinkerserver.entities;

import com.cavelinker.cavelinkerserver.enums.ContactType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
    @Column(unique=true, updatable = false, nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    private ContactType contactType;
    private String contactUserName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Activity> activities;

    public void addActivity(Activity activity) {
        this.activities.add(activity);
        activity.setUser(this);
    }
    public void removeActivity(Activity activity) {
        this.activities.remove(activity);
    }
    @Override
    public boolean equals(Object object) {
        if(this==object){
            return true;
        }
        if(this==null) {
            return false;
        }
        User inputUser=(User)object;
        return Objects.equals(email, inputUser.getEmail());
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(email);
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
