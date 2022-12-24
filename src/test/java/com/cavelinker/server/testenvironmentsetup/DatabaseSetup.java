package com.cavelinker.server.testenvironmentsetup;

import com.cavelinker.server.entities.Activity;
import com.cavelinker.server.entities.Schedule;
import com.cavelinker.server.enums.ActivityType;
import com.cavelinker.server.enums.ContactType;
import com.cavelinker.server.entities.User;
import com.cavelinker.server.enums.ServerName;
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

    private static Activity activity1;
    private static Schedule schedule1;

    static LoggingFeature logging() {
        logger = Logger.getLogger(DatabaseSetup.class.getName());
        return new LoggingFeature(logger, Level.INFO, LoggingFeature.Verbosity.PAYLOAD_TEXT, null);
    }
    public static void populateData(String baseUri) {
        client = ClientBuilder.newClient().register(logging());
        WebTarget baseTarget = client.target(baseUri);
        WebTarget userTarget = baseTarget.path("/users");
        WebTarget activityPostTarget = userTarget.path("/{userId}/activities");
        WebTarget schedulePostTarget = activityPostTarget.path("{activityId}/schedules");
        //User 1: Test User Update
        Response userResponse1 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("userupdate@gmail.com", ContactType.INSTAGRAM, "userupdate")));
        //User 2: Test User Delete
        Response userResponse2 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("userdelete@gmail.com", ContactType.TWITTER, "userdelete")));
        //User 3: Test Activity Post
        Response userResponse3 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("activitypost@gmail.com", ContactType.DISCORD, "activitypost")));
        //User 4: Test Activity Update
        Response userResponse4 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("activityupdate@gmail.com", ContactType.INSTAGRAM, "activityupdate")));
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
                .post(Entity.json(new User("testactivitydelete@gmail.com", ContactType.FACEBOOK, "testactivitydelete")));
        //User 5->Activity 2: Test Activity Delete
        Response activityResponse2 = activityPostTarget
                .resolveTemplate("userId", "5")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Activity(UUID.randomUUID().toString(),
                        "DeleteGamerTag",
                        ActivityType.DSU,
                        ServerName.DYNAMIS,
                        "DeleteActivityMessage")));
        //User 6: Test Schedule Post
        Response userResponse6 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("schedulepost@gmail.com", ContactType.TWITTER, "schedulepost")));
        //User 6->Activity 3: Test Schedule Post
        Response activityResponse3 = activityPostTarget
                .resolveTemplate("userId", "6")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Activity(UUID.randomUUID().toString(),
                        "schedulepost",
                        ActivityType.SAVAGE_OLD,
                        ServerName.LIGHT,
                        "Schedule Post Message")));
        //User 7: Test Schedule Update
        Response userResponse7 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("scheduleput@gmail.com", ContactType.INSTAGRAM, "schedulePut")));
        //User 7->Activity 4: Test Schedule Update
        Response activityResponse4 = activityPostTarget
                .resolveTemplate("userId", "7")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Activity(UUID.randomUUID().toString(),
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
                        LocalDateTime.of(2022, Month.DECEMBER, 27, 16, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 27, 21, 0),
                        ZoneId.of("America/Los_Angeles"))));
        //User 8: Test Schedule Delete
        Response userResponse8 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("scheduledelete@gmail.com", ContactType.DISCORD, "scheduleDelete")));
        //User 8->Activity 5: Test Schedule Delete
        Response activityResponse5 = activityPostTarget
                .resolveTemplate("userId", "8")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Activity(UUID.randomUUID().toString(),
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
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 27, 20, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 28, 1, 0),
                        ZoneId.of("Asia/Manila"))));
        //User 9: findMatchingActivities() Test
        Response userResponse9 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("user9@gmail.com", ContactType.TWITTER, "user9")));
        //User 9->Activity 6: findMatchingActivities() Test
        Response activityResponse6 = activityPostTarget
                .resolveTemplate("userId", "9")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Activity(UUID.randomUUID().toString(),
                        "user9",
                        ActivityType.DSU,
                        ServerName.AETHER,
                        "user9 message")));
        //User 9->Activity 6->Schedule 3: findMatchingActivities() Test
        Response scheduleResponse3 = schedulePostTarget
                .resolveTemplate("userId", "9")
                .resolveTemplate("activityId", "6")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 19, 5, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 19, 8, 0),
                        ZoneId.of("America/Los_Angeles"))));
        //User 9->Activity 6->Schedule 4: findMatchingActivities() Test
        Response scheduleResponse4 = schedulePostTarget
                .resolveTemplate("userId", "9")
                .resolveTemplate("activityId", "6")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 21, 5, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 21, 8, 0),
                        ZoneId.of("America/Los_Angeles"))));
        //User 9->Activity 6->Schedule 5: findMatchingActivities() Test
        Response scheduleResponse5= schedulePostTarget
                .resolveTemplate("userId", "9")
                .resolveTemplate("activityId", "6")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 23, 5, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 23, 9, 0),
                        ZoneId.of("America/Los_Angeles"))));
        //User 10: findMatchingActivities() Test
        Response userResponse10 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("user10@gmail.com", ContactType.FACEBOOK, "user10")));
        //User 10->Activity 7: findMatchingActivities() Test
        Response activityResponse7 = activityPostTarget
                .resolveTemplate("userId", "10")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Activity(UUID.randomUUID().toString(),
                        "user10",
                        ActivityType.DSU,
                        ServerName.AETHER,
                        "user10 message")));
        //User 10->Activity 7->Schedule 6: findMatchingActivities() Test
        Response scheduleResponse6 = schedulePostTarget
                .resolveTemplate("userId", "10")
                .resolveTemplate("activityId", "7")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 19, 20, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 20, 1, 0),
                        ZoneId.of("Asia/Manila"))));
        //User 10->Activity 7->Schedule 7: findMatchingActivities() Test
        Response scheduleResponse7 = schedulePostTarget
                .resolveTemplate("userId", "10")
                .resolveTemplate("activityId", "7")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 21, 20, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 22, 1, 0),
                        ZoneId.of("Asia/Manila"))));
        //User 10->Activity 7->Schedule 8: findMatchingActivities() Test
        Response scheduleResponse8 = schedulePostTarget
                .resolveTemplate("userId", "10")
                .resolveTemplate("activityId", "7")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 23, 21, 30),
                        LocalDateTime.of(2022, Month.DECEMBER, 24, 2, 0),
                        ZoneId.of("Asia/Manila"))));
        //User 11: findMatchingActivities() Test
        Response userResponse11 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("user11@gmail.com", ContactType.INSTAGRAM, "user11")));
        //User 11->Activity 8: findMatchingActivities() Test
        Response activityResponse8 = activityPostTarget
                .resolveTemplate("userId", "11")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Activity(UUID.randomUUID().toString(),
                        "user11",
                        ActivityType.SAVAGE_CURRENT,
                        ServerName.PRIMAL,
                        "user11 message")));
        //User 11->Activity 8->Schedule 9: findMatchingActivities() Test
        Response scheduleResponse9 = schedulePostTarget
                .resolveTemplate("userId", "11")
                .resolveTemplate("activityId", "8")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 20, 18, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 20, 23, 0),
                        ZoneId.of("Asia/Tokyo"))));
        //User 11->Activity 8->Schedule 10: findMatchingActivities() Test
        Response scheduleResponse10 = schedulePostTarget
                .resolveTemplate("userId", "11")
                .resolveTemplate("activityId", "8")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 22, 18, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 22, 23, 0),
                        ZoneId.of("Asia/Tokyo"))));
        //User 11->Activity 8->Schedule 11: findMatchingActivities() Test
        Response scheduleResponse11 = schedulePostTarget
                .resolveTemplate("userId", "11")
                .resolveTemplate("activityId", "8")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 24, 19, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 25, 0, 0),
                        ZoneId.of("Asia/Tokyo"))));
        //User 12: findMatchingActivities() Test
        Response userResponse12 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("user12@gmail.com", ContactType.DISCORD, "user12")));
        //User 12->Activity 9: findMatchingActivities() Test
        Response activityResponse9 = activityPostTarget
                .resolveTemplate("userId", "12")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Activity(UUID.randomUUID().toString(),
                        "user12",
                        ActivityType.SAVAGE_CURRENT,
                        ServerName.PRIMAL,
                        "user12 message")));
        //User 12->Activity 9->Schedule 12: findMatchingActivities() Test
        Response scheduleResponse12 = schedulePostTarget
                .resolveTemplate("userId", "12")
                .resolveTemplate("activityId", "9")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 20, 14, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 20, 16, 30),
                        ZoneId.of("Asia/Riyadh"))));
        //User 12->Activity 9->Schedule 13: findMatchingActivities() Test
        Response scheduleResponse13 = schedulePostTarget
                .resolveTemplate("userId", "12")
                .resolveTemplate("activityId", "9")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 22, 14, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 22, 16, 30),
                        ZoneId.of("Asia/Riyadh"))));
        //User 12->Activity 9->Schedule 14: findMatchingActivities() Test
        Response scheduleResponse14 = schedulePostTarget
                .resolveTemplate("userId", "12")
                .resolveTemplate("activityId", "9")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 24, 15, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 24, 17, 0),
                        ZoneId.of("Asia/Riyadh"))));
        //User 13: findMatchingActivities() Test
        Response userResponse13 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("user13@gmail.com", ContactType.FACEBOOK, "user13")));
        //User 13->Activity 10: findMatchingActivities() Test
        Response activityResponse10 = activityPostTarget
                .resolveTemplate("userId", "13")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Activity(UUID.randomUUID().toString(),
                        "user13",
                        ActivityType.DSU,
                        ServerName.AETHER,
                        "user13 message")));
        //User 13->Activity 10->Schedule 15: findMatchingActivities() Test
        Response scheduleResponse15 = schedulePostTarget
                .resolveTemplate("userId", "13")
                .resolveTemplate("activityId", "10")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 19, 5, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 19, 14, 0),
                        ZoneId.of("America/Toronto"))));
        //User 13->Activity 10->Schedule 16: findMatchingActivities() Test
        Response scheduleResponse16 = schedulePostTarget
                .resolveTemplate("userId", "13")
                .resolveTemplate("activityId", "10")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 21, 5, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 21, 14, 0),
                        ZoneId.of("America/Toronto"))));
        //User 13->Activity 10->Schedule 17: findMatchingActivities() Test
        Response scheduleResponse17 = schedulePostTarget
                .resolveTemplate("userId", "13")
                .resolveTemplate("activityId", "10")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 23, 6, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 23, 15, 0),
                        ZoneId.of("America/Toronto"))));
        //User 14: findMatchingActivities() Test
        Response userResponse14 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("user14@gmail.com", ContactType.TWITTER, "user14")));
        //User 14->Activity 11: findMatchingActivities() Test
        Response activityResponse11 = activityPostTarget
                .resolveTemplate("userId", "14")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Activity(UUID.randomUUID().toString(),
                        "user14",
                        ActivityType.SAVAGE_CURRENT,
                        ServerName.PRIMAL,
                        "user14 message")));
        //User 14->Activity 11->Schedule 18: findMatchingActivities() Test
        Response scheduleResponse18 = schedulePostTarget
                .resolveTemplate("userId", "14")
                .resolveTemplate("activityId", "11")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 20, 16, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 21, 3, 0),
                        ZoneId.of("Australia/Sydney"))));
        //User 14->Activity 11->Schedule 19: findMatchingActivities() Test
        Response scheduleResponse19 = schedulePostTarget
                .resolveTemplate("userId", "14")
                .resolveTemplate("activityId", "11")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 22, 16, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 23, 3, 0),
                        ZoneId.of("Australia/Sydney"))));
        //User 14->Activity 11->Schedule 20: findMatchingActivities() Test
        Response scheduleResponse20 = schedulePostTarget
                .resolveTemplate("userId", "14")
                .resolveTemplate("activityId", "11")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Schedule(UUID.randomUUID().toString(),
                        LocalDateTime.of(2022, Month.DECEMBER, 24, 16, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 25, 3, 0),
                        ZoneId.of("Australia/Sydney"))));
    }


    public static Activity getActivity1() {return activity1;}
    public static Schedule getSchedule1() {return schedule1;}
}
