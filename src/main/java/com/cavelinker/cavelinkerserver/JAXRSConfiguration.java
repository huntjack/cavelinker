package com.cavelinker.cavelinkerserver;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;
import com.cavelinker.cavelinkerserver.resources.*;

@ApplicationPath("/api")
public class JAXRSConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(UserResource.class);
        return classes;
    }
}
