package com.cavelinker.cavelinkerserver.testenvironmentsetup;

import com.cavelinker.cavelinkerserver.entities.Activity;
import com.cavelinker.cavelinkerserver.entities.Schedule;
import com.cavelinker.cavelinkerserver.enums.ActivityType;
import com.cavelinker.cavelinkerserver.enums.ContactType;
import com.cavelinker.cavelinkerserver.entities.User;
import com.cavelinker.cavelinkerserver.enums.ServerName;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.logging.LoggingFeature;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseSetup {

    private static Client client;
    private static Logger logger;

    private static User user1;
    private static User user2;
    private static User user3;
    private static User user4;
    private static Activity activity1;
    private static User user5;
    private static Activity activity2;
    private static User user6;
    private static Activity activity3;
    private static User user7;
    private static Activity activity4;
    private static Schedule schedule1;
    private static User user8;
    private static Activity activity5;
    private static Schedule schedule2;

    static LoggingFeature logging() {
        logger = Logger.getLogger(DatabaseSetup.class.getName());
        return new LoggingFeature(logger, Level.INFO, LoggingFeature.Verbosity.PAYLOAD_TEXT, null);
    }
    public static void populateData(String baseUri) {
        client = ClientBuilder.newClient().register(logging());
        WebTarget baseTarget = client.target(baseUri);
        WebTarget userTarget = baseTarget.path("/users");
        WebTarget activityPostTarget = baseTarget.path("/secured/users/{userId}/activities");
        WebTarget schedulePostTarget = activityPostTarget.path("{activityId}/schedules");
        //User 1: Test User Update
        Response userResponse1 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(user1 = new User("userupdate@gmail.com", ContactType.INSTAGRAM, "userupdate")));
        //User 2: Test User Delete
        Response userResponse2 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(user2 = new User("userdelete@gmail.com", ContactType.TWITTER, "userdelete")));
        //User 3: Test Activity Post
        Response userResponse3 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(user3 = new User("activitypost@gmail.com", ContactType.DISCORD, "activitypost")));
        //User 4: Test Activity Update
        Response userResponse4 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(user4 = new User("activityupdate@gmail.com", ContactType.INSTAGRAM, "activityupdate")));
        //User 4->Activity 1: Test Activity Update
        Response activityResponse1 = activityPostTarget
                .resolveTemplate("userId", "4")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(activity1 = new Activity(UUID.randomUUID().toString(),"activityupdate", ActivityType.UWU,
                        ServerName.GAIA, "activityupdate")));
        //User 5: Test Activity Delete
        Response userResponse5 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(user5 = new User("testactivitydelete@gmail.com", ContactType.FACEBOOK, "testactivitydelete")));
        //User 5->Activity 2: Test Activity Delete
        Response activityResponse2 = activityPostTarget
                .resolveTemplate("userId", "5")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(activity2 = new Activity(UUID.randomUUID().toString(),
                        "DeleteGamerTag",
                        ActivityType.DSU,
                        ServerName.DYNAMIS,
                        "DeleteActivityMessage")));
        //User 6: Test Schedule Post
        Response userResponse6 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(user6 = new User("schedulepost@gmail.com", ContactType.TWITTER, "schedulepost")));
        //User 6->Activity 3: Test Schedule Post
        Response activityResponse3 = activityPostTarget
                .resolveTemplate("userId", "6")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(activity3 = new Activity(UUID.randomUUID().toString(),
                        "schedulepost",
                        ActivityType.SAVAGE_OLD,
                        ServerName.LIGHT,
                        "Schedule Post Message")));
        //User 7: Test Schedule Update
        Response userResponse7 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(user7 = new User("scheduleput@gmail.com", ContactType.INSTAGRAM, "schedulePut")));
        //User 7->Activity 4: Test Schedule Update
        Response activityResponse4 = activityPostTarget
                .resolveTemplate("userId", "7")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(activity4 = new Activity(UUID.randomUUID().toString(),
                        "scheduleput",
                        ActivityType.EXTREME_CURRENT,
                        ServerName.CRYSTAL,
                        "Schedule Put Message")));
        //User 7->Activity 4->Schedule 1: Test Schedule Update
        Response scheduleResponse1 = schedulePostTarget
                .resolveTemplate("userId", "7")
                .resolveTemplate("activityId", "4")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(schedule1 = new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 19, 16, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 19, 21, 0),
                        ZoneId.of("America/Los_Angeles"))));
        //User 8: Test Schedule Delete
        Response userResponse8 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(user8 = new User("scheduledelete@gmail.com", ContactType.DISCORD, "scheduleDelete")));
        //User 8->Activity 5: Test Schedule Delete
        Response activityResponse5 = activityPostTarget
                .resolveTemplate("userId", "8")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(activity5 = new Activity(UUID.randomUUID().toString(),
                        "scheduledelete",
                        ActivityType.TEA,
                        ServerName.AETHER,
                        "Schedule Delete Message")));
        //User 8->Activity 5->Schedule 2: Test Schedule Delete
        Response scheduleResponse2 = schedulePostTarget
                .resolveTemplate("userId", "8")
                .resolveTemplate("activityId", "5")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(schedule2 = new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 19, 20, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 20, 01, 0),
                        ZoneId.of("Asia/Manila"))));
    }


    public static User getUser1() {return user1;}
    public static User getUser2() {return user2;}
    public static User getUser3() {return user3;}
    public static User getUser4() {return user4;}
    public static User getUser5() {return user5;}
    public static User getUser6() {return user6;}
    public static User getUser7() {return user7;}
    public static User getUser8() {return user8;}
    public static Activity getActivity1() {return activity1;}
    public static Activity getActivity2() {return activity2;}
    public static Activity getActivity3() {return activity3;}
    public static Activity getActivity4() {return activity4;}
    public static Activity getActivity5() {return activity5;}
    public static Schedule getSchedule1() {return schedule1;}
    public static Schedule getSchedule2() {return schedule2;}
}
