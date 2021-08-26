package kao.backend.spring.contoller;

import kao.backend.spring.model.UserDetailEntity;
import kao.backend.spring.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/userdetail")
public class UserDetailContoller {
    @Autowired
    private UserDetailRepository  userDetailRepository;
    @GetMapping("")
    public List<UserDetailEntity> showAllUserDetail() { return userDetailRepository.findAll(); }
}
