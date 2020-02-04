package kr.lineus.unistars.config.security;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import kr.lineus.unistars.entity.UserEntity;



@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger log = getLogger(CustomAuthenticationEntryPoint.class);

    private static boolean reusedTicketCounter = false;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
 
    @Autowired
    private CustomAuthenticationProvider authenticationManager;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
    	
    	
        String returnUrl = "";
        if (request.getServerName().toLowerCase().contains("localhost")) {
            returnUrl = (request.isSecure() ? "https" : "http") + "://" + request.getServerName();
        } else {
            returnUrl = (request.isSecure() ? "https" : "http") + "://" + request.getServerName();
        }
        if (request.getServerPort() > 0) {
            if ((request.isSecure() && request.getServerPort() != 443) || (!request.isSecure() && request.getServerPort() != 80)) {
                returnUrl += ":" + request.getServerPort();
            }
        }
        returnUrl += request.getRequestURI();
        
        log.info("Resolved returnURL to:" + returnUrl);
        //TODO: implement later

        /*
        String username = "someone";
        String pwd = "somepwd";
        UserEntity u = null; //query from db
        User user=  new UserDetailsImpl(u);
        CustomAuthentication authentication = new CustomAuthentication(username, user, new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(authentication));
        */
        
        
     }

}
