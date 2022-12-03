package com.cavelinker.cavelinkerserver.filters;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

@Provider
public class AuthorizationFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER_KEY="Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX="Basic ";
    private static final String SECURED_URL_PREFIX = "secured";

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        /*
        Basic Auth Implementation:

        if(containerRequestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
        List<String> authorizationHeader = containerRequestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
            if(authorizationHeader.size() > 0) {
                String authenticationToken = authorizationHeader.get(0);
                authenticationToken = authenticationToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
                byte[] decodedByteArray = Base64.getDecoder().decode(authenticationToken);
                String decodedString = new String(decodedByteArray);
                StringTokenizer stringTokenizer = new StringTokenizer(decodedString, ":");
                String userName = stringTokenizer.nextToken();
                String userPassword = stringTokenizer.nextToken();

                if(call to UserService Authentication Method is successful ) {
                    return;
                }
                Response unauthorizedStatus = Response
                                                .status(Response.Status.UNAUTHORIZED)
                                                .entity("User is not authorized to access this resource.")
                                                .build();
            }
        }

         */
    }
}
