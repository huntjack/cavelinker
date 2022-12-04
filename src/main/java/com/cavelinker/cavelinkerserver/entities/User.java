package com.cavelinker.cavelinkerserver.entities;

import com.cavelinker.cavelinkerserver.enums.ContactType;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long user_Id;
    @Column(unique=true)
    private String email;
    @Enumerated(EnumType.STRING)
    private ContactType contactType;
    private String contactUserName;
    @OneToMany(mappedBy = "user")
    private List<Activity> activities;

    public void addActivity(Activity activity) {
        this.activities.add(activity);
        activity.setUser(this);
    }
    public User(){}
    public User(String email, ContactType contactType, String contactUserName) {
        this.email=email;
        this.contactType=contactType;
        this.contactUserName=contactUserName;
    }

    public Long getUser_Id() {return user_Id;}
    public void setUser_Id(Long user_Id) {this.user_Id = user_Id;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public ContactType getContactType() {return contactType;}
    public void setContactType(ContactType contactType) {this.contactType = contactType;}
    public String getContactUserName() {return contactUserName;}
    public void setContactUserName(String contactUserName) {this.contactUserName = contactUserName;}

}
