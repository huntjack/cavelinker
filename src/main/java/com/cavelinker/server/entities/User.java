package com.cavelinker.server.entities;

import com.cavelinker.server.enums.ContactType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId",
        scope = User.class)
@NamedQuery(name="getUserWithActivities",
        query="SELECT user FROM User user INNER JOIN FETCH user.activities WHERE user.userId = :userId")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique = true, updatable = false, nullable = false)
    private Long userId;
    @Column(unique=true, updatable = false, nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    private ContactType contactType;
    private String contactUserName;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE},  orphanRemoval = true)
    private List<Activity> activities;

    public void addActivity(Activity activity) {
        /*activity.setUser(this);*/
        activities.add(activity);
    }
    public void removeActivity(Activity activity) {
        activities.remove(activity);
        /*activity.setUser(null); */
    }
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
        User inputUser=(User)object;
        return Objects.equals(email, inputUser.getEmail());
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
    public User(String email, ContactType contactType, String contactUserName) {
        this.email=email;
        this.contactType=contactType;
        this.contactUserName=contactUserName;
    }
    public User(){}

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
