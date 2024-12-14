package beauty_salon.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.access.AccessDeniedHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoggingAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       org.springframework.security.access.AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        String username = (request.getUserPrincipal() != null) ? request.getUserPrincipal().getName() : "Anonymous";
        LOGGER.warn("Access denied for user: {} on URL: {}", username, request.getRequestURI());

        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }
}
