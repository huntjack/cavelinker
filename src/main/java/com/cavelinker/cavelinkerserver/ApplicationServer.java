package com.cavelinker.cavelinkerserver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.cavelinker.cavelinkerserver.model.Message;
import com.cavelinker.cavelinkerserver.model.Schedule;
import com.cavelinker.cavelinkerserver.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


public class ApplicationServer {

    @PersistenceContext(unitName = "Cavelinker_Database")
    EntityManager entityManager;

    public static void main (String[] args) {
        ApplicationServer applicationServer = new ApplicationServer();
        applicationServer.tryToConnect();
    }

    @PostConstruct
    public void tryToConnect() {
        User user=new User();
        Message message=new Message();
        Schedule schedule=new Schedule();

        StringBuilder testGamerTag=new StringBuilder("Eric");
        StringBuilder testActivityMessage=new StringBuilder("This is an activity message.");
        String testContactType = new String("Discord");
        StringBuilder testContactInfo= new StringBuilder("newContactID");
        String day= new String("MONDAY");
        String testActivity= new String("FFXIV:Savage");
        StringBuilder testEmail = new StringBuilder(new String("hunt.jack01@gmail.com"));
        StringBuilder testPassword= new StringBuilder(new String("password"));

        user.setEmail(testEmail);
        user.setPassword(testPassword);
        user.setContactType(testContactType);
        user.setContactInfo(testContactInfo);
        message.setGamerTag(testGamerTag);
        message.setActivityMessage(testActivityMessage);
        schedule.setDay(day);
        schedule.setActivity(testActivity);

        System.out.println(user.getUser_ID());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        System.out.println(user.getContactType());
        System.out.println(user.getContactInfo());
        System.out.println(message.getMessage_ID());
        System.out.println(message.getGamerTag());
        System.out.println(message.getActivityMessage());
        System.out.println(schedule.getSchedule_ID());
        System.out.println(schedule.getDay());
        System.out.println(schedule.getActivity());


        for(int retries=20; retries>0; retries--) {
            try {
                System.out.println("Connection attempt "+retries);
                this.entityManager.persist(user);
                this.entityManager.persist(message);
                this.entityManager.persist(schedule);

                TypedQuery<User> userTypedQuery=this.entityManager.createQuery("SELECT user FROM User user", User.class);
                List<User> users=userTypedQuery.getResultList();
                TypedQuery<Schedule> scheduleTypedQuery=this.entityManager.createQuery("SELECT schedule FROM Schedule schedule", Schedule.class);
                List<Schedule> schedules=scheduleTypedQuery.getResultList();
                TypedQuery<Message> messageTypedQuery= this.entityManager.createQuery("SELECT message FROM Message message", Message.class);
                List<Message> messages=messageTypedQuery.getResultList();

                System.out.println("Printing Object Info:");
                for (User userElement : users) {
                    System.out.println(userElement.getUser_ID());
                    System.out.println(userElement.getEmail());
                    System.out.println(userElement.getPassword());
                    System.out.println(userElement.getContactType());
                    System.out.println(userElement.getContactInfo());
                }

                for (Schedule scheduleElement : schedules) {
                    System.out.println(scheduleElement.getSchedule_ID());
                    System.out.println(scheduleElement.getDay());
                    System.out.println(scheduleElement.getActivity());
                }

                for (Message messageElement : messages) {
                    System.out.println(messageElement.getMessage_ID());
                    System.out.println(messageElement.getGamerTag());
                    System.out.println(messageElement.getActivityMessage());
                }
                System.out.println("Object Info Printing Complete.");
                break;
            } catch (Exception exception) {
                exception.printStackTrace();
                System.err.println("Unable to connect: "+retries+" retries left: ");
                this.waitFiveSeconds();
            }
        }
    }
    public void waitFiveSeconds() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
