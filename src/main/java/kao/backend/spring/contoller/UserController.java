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
    public  List<UserEntity> getAll(HttpSession session){
        List<UserEntity> test = (List<UserEntity>) session.getAttribute("account");
        return test;
    }
    @GetMapping("/{email}/{password}")
    public List<UserEntity> login(@PathVariable String email,@PathVariable String password,HttpServletRequest request){
        if(userRepository.findAllByEmailAndPassword(email, password).isEmpty()==false){
           List<UserEntity> test=userRepository.findAllByEmailAndPassword(email, password);
           request.getSession().setAttribute("account",test);
           return userRepository.findAllByEmailAndPassword(email, password);
       }
       else{
           return null;
       }
    }

}
