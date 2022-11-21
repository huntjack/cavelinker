package com.cavelinker.cavelinkerserver;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;
import com.cavelinker.cavelinkerserver.resources.*;

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

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(UserResource.class);
        return classes;
    }
}
