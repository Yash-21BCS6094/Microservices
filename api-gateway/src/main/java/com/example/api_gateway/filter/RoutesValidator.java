package com.example.api_gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RoutesValidator {

    private static final Logger logger = LoggerFactory.getLogger(RoutesValidator.class);

    public static final List<String> openApiEndpoints = List.of(
            "api/v1/auth/register",
            "api/v1/auth/login",
            "api/v1/auth/refresh",
            "api/v1/auth/logout"
    );

    public Predicate<ServerHttpRequest> isSecured = request -> {
        String path = request.getURI().getPath();
        boolean isSecured = openApiEndpoints.stream().noneMatch(uri -> path.startsWith("/" + uri));

        logger.info("Route check for path: {} | Secured: {}", path, isSecured);
        return isSecured;
    };
}
