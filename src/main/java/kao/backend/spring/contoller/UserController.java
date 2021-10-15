package kao.backend.spring.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kao.backend.spring.model.AuthenResponse;
import kao.backend.spring.model.UserEntity;
import kao.backend.spring.repository.UserRepository;
import kao.backend.spring.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/login")
    public ResponseEntity<UserEntity> login(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "password", required = false) String password, HttpSession session) throws Exception {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (email == null && password == null) {
            if (session == null) {
                return null;
            }
            List<String> loginAccount = (List<String>) session.getAttribute("Account");
            System.out.println(session.getId());
            if (loginAccount == null || loginAccount.isEmpty()) {
                return null;
            }
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginAccount.get(0));
            final String jwt = jwtUtil.generateToken(userDetails);
            responseHeaders.set("JWT", new AuthenResponse(jwt).getJwt());
            return ResponseEntity.ok().headers(responseHeaders).body(userRepository.findByEmailAndPassword(loginAccount.get(0), loginAccount.get(1)));
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            throw new Exception("Error", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String jwt = jwtUtil.generateToken(userDetails);
        ArrayList<String> account = new ArrayList<>();
        account.add(email);
        account.add(password);
        session.setAttribute("Account", account);
        responseHeaders.set("JWT", new AuthenResponse(jwt).getJwt());
        System.out.println(session.getId());
        return ResponseEntity.ok().headers(responseHeaders).body(userRepository.findByEmailAndPassword(email, password));

    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout Successful!");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestParam String account,HttpSession session) {
        HttpHeaders responseHeaders = new HttpHeaders();
        UserEntity newAccount = null;
        try {
            newAccount = objectMapper.readValue(account, UserEntity.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getStackTrace());
        }
        System.out.println(newAccount.getEmail());
        if (userRepository.findByEmail(newAccount.getEmail()) != null) {
            return ResponseEntity.badRequest().body("accountEmailExist");
        }
        if (userRepository.findByPhone(newAccount.getPhone()) != null) {
            return ResponseEntity.badRequest().body("accountPhoneExist");
        }
        userRepository.save(newAccount);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(newAccount.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        responseHeaders.set("JWT", new AuthenResponse(jwt).getJwt());
        ArrayList<String> sessionAccount = new ArrayList<>();
        sessionAccount.add(newAccount.getEmail());
        sessionAccount.add(newAccount.getPassword());
        session.setAttribute("Account", sessionAccount);
        return ResponseEntity.ok().headers(responseHeaders).body("success");
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editUser(@RequestParam String account){
        UserEntity editAccount = null;
        try {
            editAccount = objectMapper.readValue(account, UserEntity.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getStackTrace());
            return ResponseEntity.internalServerError().body("Json Problem");
        }
        try{
            UserEntity oldAccount = userRepository.findByEmailAndPassword(editAccount.getEmail(), editAccount.getPassword());
            oldAccount.setEmail(editAccount.getEmail());
            oldAccount.setPassword(editAccount.getPassword());
            oldAccount.setAddress(editAccount.getAddress());
            oldAccount.setPhone(editAccount.getPhone());
            oldAccount.setFname(editAccount.getFname());
            oldAccount.setLname(editAccount.getLname());
            oldAccount.setOrderList(editAccount.getOrderList());
            userRepository.save(oldAccount);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Cannot find menu to edit");
        }
        return ResponseEntity.ok("success");
    }
}
