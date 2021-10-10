package kao.backend.spring.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kao.backend.spring.model.UserEntity;
import kao.backend.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("")
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/login")
    public UserEntity login(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "password" ,required = false) String password,HttpServletRequest request,HttpSession session) {
        if(email==null && password==null){
            if(session == null){
                return null;
            }
            List<String> loginAccount = (List<String>) session.getAttribute("Account");
            System.out.println(session.getId());
            if(loginAccount==null||loginAccount.isEmpty()){
                return null;
            }
            return  userRepository.findByEmailAndPassword(loginAccount.get(0), loginAccount.get(1));
        }
            ArrayList<String> account = new ArrayList<>();
            account.add(email);
            account.add(password);
            request.getSession().setAttribute("Account", account);
            System.out.println(session.getId());
            return userRepository.findByEmailAndPassword(email, password);

    }

    @DeleteMapping("/logout")
    public void logout(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "password" ,required = false) String password,HttpSession session ){
        System.out.println(session.getId());
        session.invalidate();
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String account) {
        UserEntity newAccount = null;
        try {
            newAccount = objectMapper.readValue(account, UserEntity.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getStackTrace());
        }
        if (userRepository.findByEmail(newAccount.getEmail())!=null) {
            return "accountEmailExist";
        }
        if (userRepository.findByPhone(newAccount.getPhone())!=null) {
            return "accountPhoneExist";
        }
        userRepository.save(newAccount);
        return "success";
    }
}
