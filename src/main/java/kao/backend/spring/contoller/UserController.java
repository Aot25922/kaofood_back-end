package kao.backend.spring.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kao.backend.spring.model.UserEntity;
import kao.backend.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public UserEntity login(@RequestParam String email, @RequestParam String password) {
        return userRepository.findByEmailAndPassword(email, password);
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
