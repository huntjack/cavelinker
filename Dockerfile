FROM tomee:9.0.0-M8-jre11-ubuntu-plume

COPY ./target/cavelinkerserver-1.0-SNAPSHOT.war /usr/local/tomee/webapps/
COPY ./tomcat-users.xml /usr/local/tomee/conf/
COPY ./context.xml /usr/local/tomee/webapps/host-manager/META-INF/

CMD ["catalina.sh", "run"]