FROM payara/server-full:5.2022.4-jdk11

COPY ./target/cavelinkerserver/WEB-INF/lib/mysql-connector-j-8.0.31.jar /opt/payara/
COPY ./commands.asadmin $POSTBOOT_COMMANDS
COPY ./target/cavelinkerserver.war /opt/payara/

