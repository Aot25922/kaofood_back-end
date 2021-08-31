package kao.backend.spring.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kao.backend.spring.model.UserEntity;
import kao.backend.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public  List<UserEntity> getAll(){
        return userRepository.findAll();
    }
    @GetMapping("/{email}/{password}")
    public UserEntity login(@PathVariable String email,@PathVariable String password){
           return userRepository.findByEmailAndPassword(email, password);
    }

}
