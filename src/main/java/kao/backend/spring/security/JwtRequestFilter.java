package kao.backend.spring.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    //Filter request for JWT token
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
         final String authorizationHeader = request.getHeader("Authorization");
         String email = null;
         String jwt = null;

         if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")){
             jwt = authorizationHeader.substring(7);
             email = jwtUtil.getUsernameFromToken(jwt);
         }

         if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
             UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(email);
             List<String> loginAccount = (List<String>) request.getSession().getAttribute("Account");
             UserDetails validateUser = this.myUserDetailsService.loadUserByUsername(loginAccount.get(0));
             try {
                 if (jwtUtil.validateToken(jwt, userDetails) && userDetails.equals(validateUser)) {
                     UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                             userDetails, null, userDetails.getAuthorities());
                     usernamePasswordAuthenticationToken.setDetails(
                             new WebAuthenticationDetailsSource().buildDetails(request)
                     );
                     SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                 }
             }catch (ExpiredJwtException e){
                 System.out.println("erf");
             }
         }
         chain.doFilter(request, response);
        Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
        boolean firstHeader = true;
        for (String header : headers) { // there can be multiple Set-Cookie attributes
            if (firstHeader) {
                response.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header, "SameSite=None")); // set
                firstHeader = false;
                continue;
            }
            response.addHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header, "SameSite=None")); // add
        }
    }
}
