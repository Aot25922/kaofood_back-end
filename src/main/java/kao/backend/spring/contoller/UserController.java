package kao.backend.spring.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kao.backend.spring.model.UserEntity;
import kao.backend.spring.repository.RoleRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //User Login
    @GetMapping("/login")
    public ResponseEntity<UserEntity> login(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "password", required = false) String password, HttpServletRequest request,HttpSession session) throws Exception {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (email == null && password == null) {
            if (session == null) {
                return null;
            }
            List<String> loginAccount = (List<String>) session.getAttribute("Account");
            if (loginAccount == null || loginAccount.isEmpty()) {
                return null;
            }
            UserEntity getUser = userRepository.findByEmailAndPassword(loginAccount.get(0),loginAccount.get(1));
            if(getUser!=null) {
                return ResponseEntity.ok().body(getUser);
            }
            return null;
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String jwt = jwtUtil.generateToken(userDetails);
        ArrayList<String> account = new ArrayList<>();
        account.add(email);
        responseHeaders.set("JWT", jwt);
        UserEntity getUser = userRepository.findByEmail(email);
        if(passwordEncoder.matches(password,getUser.getPassword())) {
            account.add(getUser.getPassword());
            request.getSession().setAttribute("Account", account);
            return ResponseEntity.ok().headers(responseHeaders).body(getUser);
        }
        return ResponseEntity.internalServerError().body(null);
    }

    //User Logout
    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout Successful!");
    }

    //User Signup
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestParam String account,HttpSession session) {
        HttpHeaders responseHeaders = new HttpHeaders();
        UserEntity newAccount = null;
        try {
            newAccount = objectMapper.readValue(account, UserEntity.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Cannot Convert To Entity");
        }
        System.out.println(newAccount.getEmail());
        if (userRepository.findByEmail(newAccount.getEmail()) != null) {
            return ResponseEntity.badRequest().body("accountEmailExist");
        }
        if (userRepository.findByPhone(newAccount.getPhone()) != null) {
            return ResponseEntity.badRequest().body("accountPhoneExist");
        }
        newAccount.setPassword(passwordEncoder.encode(newAccount.getPassword()));
        newAccount.setRole(roleRepository.findById(3));
        userRepository.save(newAccount);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(newAccount.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        responseHeaders.set("JWT", jwt);
        ArrayList<String> sessionAccount = new ArrayList<>();
        sessionAccount.add(newAccount.getEmail());
        sessionAccount.add(newAccount.getPassword());
        session.setAttribute("Account", sessionAccount);
        return ResponseEntity.ok().headers(responseHeaders).body("success");
    }

    //Edit user detail
    @PutMapping("/edit/profile")
    public ResponseEntity<String> editUserProfile(@RequestParam String account,HttpSession session){
        UserEntity editAccount = null;
        try {
            editAccount = objectMapper.readValue(account, UserEntity.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Json Problem");
        }
        try{
            List<String> loginAccount = (List<String>) session.getAttribute("Account");
            if (loginAccount == null || loginAccount.isEmpty()) {
                return null;
            }
            UserEntity oldAccount = userRepository.findByEmailAndPassword(loginAccount.get(0), loginAccount.get(1));
            oldAccount.setEmail(editAccount.getEmail());
            oldAccount.setAddress(editAccount.getAddress());
            oldAccount.setPhone(editAccount.getPhone());
            oldAccount.setFname(editAccount.getFname());
            oldAccount.setLname(editAccount.getLname());
            if(!(editAccount.getPassword().equals(""))){
                oldAccount.setPassword(passwordEncoder.encode(editAccount.getPassword()));
                ArrayList<String> sessionAccount = new ArrayList<>();
                sessionAccount.add(oldAccount.getEmail());
                sessionAccount.add(oldAccount.getPassword());
                session.setAttribute("Account", sessionAccount);
            }
            userRepository.save(oldAccount);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Cannot find menu to edit");
        }
        return ResponseEntity.ok("success");
    }

    //Edit user password
    @PutMapping("/edit/password")
    public ResponseEntity<String> editUserPassword(String password,HttpSession session){
        try{
            List<String> loginAccount = (List<String>) session.getAttribute("Account");
            if (loginAccount == null || loginAccount.isEmpty()) {
                return null;
            }
            UserEntity account = userRepository.findByEmailAndPassword(loginAccount.get(0), loginAccount.get(1));
            account.setPassword(passwordEncoder.encode(password));
            userRepository.save(account);
            loginAccount.set(1,passwordEncoder.encode(password) );
            session.setAttribute("Account", loginAccount);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Cannot find menu to edit");
        }
        return ResponseEntity.ok("success");
    }
}
