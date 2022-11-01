FROM tomee:9.0.0-M8-jre11-ubuntu-plume

COPY ./target/cavelinkerserver-1.0-SNAPSHOT.war /usr/local/tomee/webapps/
COPY ./tomcat-users.xml /usr/local/tomee/conf/
COPY ./context.xml /usr/local/tomee/webapps/host-manager/META-INF/
COPY ./target/cavelinkerserver-1.0-SNAPSHOT/WEB-INF/lib/antlr-2.7.7.jar /usr/local/tomee/lib/
COPY ./target/cavelinkerserver-1.0-SNAPSHOT/WEB-INF/lib/dom4j-1.6.1.jar /usr/local/tomee/lib/
COPY ./target/cavelinkerserver-1.0-SNAPSHOT/WEB-INF/lib/hibernate-commons-annotations-5.1.0.Final.jar /usr/local/tomee/lib/
COPY ./target/cavelinkerserver-1.0-SNAPSHOT/WEB-INF/lib/hibernate-core-5.4.10.Final.jar /usr/local/tomee/lib/
COPY ./target/cavelinkerserver-1.0-SNAPSHOT/WEB-INF/lib/hibernate-entitymanager-5.4.10.Final.jar /usr/local/tomee/lib/
COPY ./target/cavelinkerserver-1.0-SNAPSHOT/WEB-INF/lib/hibernate-validator-5.1.3.Final.jar /usr/local/tomee/lib/
COPY ./target/cavelinkerserver-1.0-SNAPSHOT/WEB-INF/lib/jboss-logging-3.3.2.Final.jar /usr/local/tomee/lib/
COPY ./target/cavelinkerserver-1.0-SNAPSHOT/WEB-INF/lib/jandex-1.1.0.Final.jar /usr/local/tomee/lib/
COPY ./target/cavelinkerserver-1.0-SNAPSHOT/WEB-INF/lib/javassist-3.18.1-GA.jar /usr/local/tomee/lib/
COPY ./target/cavelinkerserver-1.0-SNAPSHOT/WEB-INF/lib/byte-buddy-1.10.2.jar /usr/local/tomee/lib/
COPY ./target/cavelinkerserver-1.0-SNAPSHOT/WEB-INF/lib/classmate-1.0.0.jar /usr/local/tomee/lib/

CMD ["catalina.sh", "run"]