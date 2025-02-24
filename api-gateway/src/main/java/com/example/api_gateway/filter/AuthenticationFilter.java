package com.example.api_gateway.filter;

import com.example.api_gateway.exception.InvalidAccessException;
import com.example.api_gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    @Autowired
    private RoutesValidator validator;

    @Autowired
    private JwtUtil jwtUtil;
    public AuthenticationFilter() {
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            logger.info("Incoming request to: {}", path);

            if (validator.isSecured.test(exchange.getRequest())) {
                logger.info("Secured route detected: {}", path);

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    logger.warn("Missing authorization header for request: {}", path);
                    throw new InvalidAccessException("Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                logger.info("Authorization header found");

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                } else {
                    logger.warn("Invalid token format in request: {}", path);
                    throw new InvalidAccessException("Invalid authorization token");
                }

                try {
                    jwtUtil.validateToken(authHeader);
                    logger.info("JWT validation successful for request: {}", path);
                } catch (Exception e) {
                    logger.error("Unauthorized access detected: {}", e.getMessage());
                    throw new InvalidAccessException("Unauthorized access to application");
                }
            } else {
                logger.info("Public route accessed: {}", path);
            }

            return chain.filter(exchange);
        });
    }

    private String extractRoleFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET) // Use the same secret key as in token generation
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class); // Extracts the "role" claim
    }

    public static class Config {
    }
}
