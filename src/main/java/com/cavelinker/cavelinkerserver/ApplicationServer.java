package com.cavelinker.cavelinkerserver;

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
                    }

                    for (Message messageElement : messages) {
                        System.out.println(message.getMessage_ID());
                        System.out.println(message.getGamerTag());
                        System.out.println(message.getActivityMessage());
                    }
                    break;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                System.err.println("Unable to connect: "+retries+" retries left: ");
                applicationServer.waitFiveSeconds();
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
