package com.cavelinker.cavelinkerserver;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class userCRUDTest {

    @Test
    void testDatabaseInput() {

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

        System.out.println(user.getUser_ID());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        System.out.println(user.getContactType());
        System.out.println(user.getContactInfo());
        System.out.println(message.getMessage_ID());
        System.out.println(message.getGamerTag());
        System.out.println(message.getMessage());
        System.out.println(schedule.getSchedule_ID());
        System.out.println(schedule.getDay());
        System.out.println(schedule.getActivity());
        System.out.println(schedule.getStartTime());
        System.out.println(schedule.getEndTime());

        List<User> users = session.createQuery("from User", User.class).getResultList();
        List<Schedule> schedules = session.createQuery("from Schedule ", Schedule.class).getResultList();
        List<Message> messages = session.createQuery("from Message", Message.class).getResultList();

        assertNotNull(users);
        assertNotNull(schedules);
        assertNotNull(messages);

        //terminate session factory, otherwise program won't end
        sessionFactory.close();

    }


}
