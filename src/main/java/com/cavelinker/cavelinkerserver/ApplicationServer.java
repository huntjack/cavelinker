package com.cavelinker.cavelinkerserver;

import java.time.ZonedDateTime;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ApplicationServer {

    public static void main (String[] args) {
        ApplicationServer applicationServer = new ApplicationServer();
        User user=new User();
        Message message=new Message();
        Schedule schedule=new Schedule();

        StringBuilder testGamerTag=new StringBuilder("Eric");
        StringBuilder testMessage=new StringBuilder("This is a message.");
        String testContactType = new String("Discord");
        StringBuilder testContactInfo= new StringBuilder("newContactID");
        String day= new String("MONDAY");
        ZonedDateTime zone = ZonedDateTime.parse("2016-10-05T08:20:10+05:30[Asia/Kolkata]");
        String testActivity= new String("FFXIV:Savage");
        String testEmail = new String("hunt.jack01@gmail.com");
        String testPassword= new String("password");

        user.setEmail(testEmail);
        user.setPassword(testPassword);
        user.setContactType(testContactType);
        user.setContactInfo(testContactInfo);
        message.setGamerTag(testGamerTag);
        message.setMessage(testMessage);
        schedule.setDay(day);
        schedule.setStartTime(zone);
        schedule.setEndTime(zone);
        schedule.setActivity(testActivity);

        SessionFactory sessionFactory = HibernateUtility.getSessionFactory();
        Session session= sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(user);
        session.persist(message);
        session.persist(schedule);
        session.getTransaction().commit();

        //terminate session factory, otherwise program won't end
        sessionFactory.close();
    }

}
