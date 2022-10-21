FROM tomee:9.0.0-M8-jre11-ubuntu-plume

COPY ./target/cavelinkerserver-1.0-SNAPSHOT.war /usr/local/tomee/webapps/cavelinkerserver-1.0-SNAPSHOT.war

CMD ["catalina.sh", "run"]