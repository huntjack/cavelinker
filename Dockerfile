FROM tomee:9.0.0-M8-jre11-ubuntu-plume

COPY ./target/cavelinkerserver-1.0-SNAPSHOT.war /opt/tomcat/webapps/

CMD ["java", "com.cavelinker.cavelinkerserver.ApplicationServer"]