package com.cavelinker.cavelinkerserver;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@ApplicationScoped
@DataSourceDefinition(
        name = "java:global/jdbc/local-mysql",
        className = "com.mysql.cj.jdbc.MysqlDataSource",
        user = "${ENV=DB_USER}",
        password = "${ENV=DB_PASSWORD}",
        url = "${ENV=DB_JDBC_URL}",
        properties = {
                "allowPublicKeyRetrieval=true",
                "useSSL=false",
                "requireSSL=false"
        }
)
public class JAXRSConfiguration extends Application {

}
