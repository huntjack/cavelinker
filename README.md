## Table of Contents
1. [General Info](#general-info)
2. [Running](#running)
3. [Testing](#testing)

### General Info
***

People love to play team-based video games with other like-minded players. Random matchmaking is convenient, but oftentimes less enjoyable. Video games would be more fun, if static teams were easier to form. That is the goal of Cavelinker.

This program was written to be the backend for an app that would help MMO players (i.e. Final Fantasy XIV players) find statics more easily.

### Running
***

The application server looks for an activity that matches your activity. You can input data using HTTP after the server has been started. First, you must make a user. Each user can have zero or more activities. Each activity can have zero or more schedules associated with it. 

You will need Docker Desktop (Windows/MacOS) or Docker engine(Linux) as well as Maven installed on your computer to run this appplication server.

##### Steps

1. Prepare "docker-compose.yml". Add passwords required in "docker-compose-template.yml" and change filename from "docker-compose-template.yml" to "docker-compose.yml"

2. In the root directory(/cavelinker/), run:<br> 
mvn clean package<br>
docker build -t cavelinker:1.0 .<br> 
docker compose -f docker-compose.yml up<br>

3. You may now add users, activities and schedules using HTTP methods.

4. After entering the entities in to database, you can search for matching activities by sending a GET request to the GET Activity URI shown below. It will return the matching activities and schedules with the times converted to UTC. This can be converted to the user's local timezone on the client side. 

Note: Each schedule has a userTimezone, but this was meant to avoid misalignment due to daylight savings time. Unfortunately, this feature hasn't been fully implemented as of this time.

Example POST URIs:<br>
User:<br>
http://localhost:8080/api/users<br>
Activity:<br>
http://localhost:8080/api/users/{userId}/activities<br>
Schedule:<br>
http://localhost:8080/api/users/{userId}/activities/{activityId}/schedules<br>

Example PUT/GET/DELETE URIs:<br>
User:<br>
http://localhost:8080/api/users/{userId}<br>
Note: Sending a GET request to the user URI will also return the corresponding activities.<br>

Activity:<br>
http://localhost:8080/api/users/{userId}/activities/{activityId}<br>
Note: Sending a GET request to the activity URI will give you a list of activities that match the activity from the URI.<br>

Schedule:<br>
http://localhost:8080/api/users/{userId}/activities/{activityId}/schedules/{scheduleId}<br>

The header of each request must include:<br>
KEY:Content-Type<br>
VALUE:application/json<br>

Request Body Data Format(Not needed for GET/DELETE):

User:<br>
{<br>
    "email": "example@gmail.com",<br>
    "contactType": "FACEBOOK",<br>
    "contactUserName": "myContact"<br>
}<br>
List of possible contact types: "DISCORD, TWITTER, FACEBOOK, INSTAGRAM"<br>

Activity<br>
{<br>
    "activityBusinessKey": "d2ba586b-a884-4e18-8879-741620e2940f",<br>
    "gamerTag": "gamerTag1",<br>
    "activityType": "SAVAGE_CURRENT",<br>
    "serverName": "AETHER",<br>
    "activityMessage": "This is message 1."<br>
}<br>
The activityBusinessKey is a UUID(v4). You may use an online generator to get one. When updating an activity, the activityBusinessKey must match the original record.<br>
List of possible activity types: "SAVAGE_CURRENT, SAVAGE_OLD, EXTREME_CURRENT, EXTREME_OLD, UWU, UCOB, TEA, DSU"<br>
List of possible server names: "AETHER, CRYSTAL, PRIMAL, DYNAMIS, CHAOS, LIGHT, MATERIA, ELEMENTAL, GAIA, MANA, METEOR"<br>

{<br>
    "scheduleBusinessKey": "45365b22-e721-4339-af6b-a6ca1b76eafc",<br>
    "startTimeUtc": "2022-12-19T05:00:00.000",<br>
    "endTimeUtc": "2022-12-19T08:00:00.000",<br>
    "userTimeZone": "America/Los_Angeles"<br>
}<br>
The scheduleBusinessKey is also UUID(v4). You may use an online generator to get one. When updating a schedule, the scheduleBusinessKey must match the original record.<br>
The userTimeZone must be a valid Java ZoneId.<br>

Note: There is no need to include the primary key. The server uses the primary key from the request URI.<br>

### Testing
***

#### Steps
1. In order for the tests to run, you need to add the correct path to the Dockerfile on your system in CaveLinkerIT.java. The Dockerfile is in the root folder (/cavelinker/). CaveLinkerIT.java can be found at:<br>
 /cavelinker/src/test/java/com/cavelinker/server/testenvironmentsetup/CaveLinkerIT.java<br>
In CaveLinkerIT.java, look for Line 26 and add the path to the Dockerfile on your system:<br>
.withDockerFile(Paths.get("/path/to/Dockerfile")))<br>

2. You can run all of the tests by running the following command from the root folder(/cavelinker/):<br> 
mvn clean verify<br>
