package com.cavelinker.cavelinkerserver;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        for(int retries=20; retries>0; retries--) {
            try (SessionFactory sessionFactory=HibernateUtility.getSessionFactory();
                 Session session = sessionFactory.getCurrentSession();) {
                if (session != null) {
                    session.beginTransaction();
                    session.persist(user);
                    session.persist(message);
                    session.persist(schedule);
                    session.getTransaction().commit();

                    List<User> users = session.createQuery("from User", User.class).getResultList();
                    List<Schedule> schedules = session.createQuery("from Schedule ", Schedule.class).getResultList();
                    List<Message> messages = session.createQuery("from Message", Message.class).getResultList();

                    for (User userElement : users) {
                        System.out.println(user.getUser_ID());
                        System.out.println(user.getEmail());
                        System.out.println(user.getPassword());
                        System.out.println(user.getContactType());
                        System.out.println(user.getContactInfo());
                    }

                    for (Schedule scheduleElement : schedules) {
                        System.out.println(schedule.getSchedule_ID());
                        System.out.println(schedule.getDay());
                        System.out.println(schedule.getActivity());
                        System.out.println(schedule.getStartTime());
                        System.out.println(schedule.getEndTime());
                    }

                    for (Message messageElement : messages) {
                        System.out.println(message.getMessage_ID());
                        System.out.println(message.getGamerTag());
                        System.out.println(message.getMessage());
                    }
                    break;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                System.err.println("Unable to connect: "+retries+" retries left: ");
                applicationServer.waitFiveSeconds();
            }
            retries--;
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
