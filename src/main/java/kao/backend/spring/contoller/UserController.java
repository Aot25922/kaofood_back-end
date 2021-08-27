package kao.backend.spring.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kao.backend.spring.model.UserEntity;
import kao.backend.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();
    @GetMapping("/{email}/{password}")
    public List<UserEntity> login(@PathVariable String email,@PathVariable String password){
       if(userRepository.findAllByEmailAndPassword(email, password).isEmpty()==false){
           return userRepository.findAllByEmailAndPassword(email, password);
       }
       else{
           return null;
       }

    }
}
