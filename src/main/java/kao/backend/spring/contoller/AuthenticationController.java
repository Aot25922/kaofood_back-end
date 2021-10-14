package kao.backend.spring.contoller;

import kao.backend.spring.model.AuthenRequest;
import kao.backend.spring.model.AuthenResponse;
import kao.backend.spring.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController

public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @RequestMapping(value = "/authen",method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenToken(@RequestParam String email,@RequestParam String password) throws  Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }catch (BadCredentialsException e){
            throw  new Exception("Error",e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String jwt = jwtUtil.generateToken(userDetails);

        return  ResponseEntity.ok(new AuthenResponse(jwt));

    }
}
