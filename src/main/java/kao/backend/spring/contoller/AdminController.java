package kao.backend.spring.contoller;

import kao.backend.spring.model.UserEntity;
import kao.backend.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserRepository userRepository;
    private List<String> loginAccount;

    @GetMapping("/allAccount")
    public ResponseEntity<List<UserEntity>> getAll(HttpSession session) {
        if (checkForAdmin(session)) {
            return ResponseEntity.ok(userRepository.findAll());
        }else {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editUser(@PathVariable int id, @RequestParam(value = "delete", required = false) Boolean delete, @RequestParam(value = "role", required = false) String role, HttpSession session) {
        if (delete == null && role == null) {
            return ResponseEntity.badRequest().body("No Edit Option");
        }
        if(!(checkForAdmin(session))){
            return ResponseEntity.badRequest().body("You not admin");
        }
        UserEntity user = userRepository.findById(id);
        if(delete!=null&&delete == true){
            userRepository.delete(user);
            return ResponseEntity.ok("Delete Account");
        }
        if(role != null){
            user.setRole(role);
            userRepository.save(user);
            return ResponseEntity.ok("Change Role");
        }
        return ResponseEntity.badRequest().body("No Edit Option");
    }

    private Boolean checkForAdmin(HttpSession session) {
        loginAccount = (List<String>) session.getAttribute("Account");
        if (loginAccount == null || loginAccount.isEmpty()) {
            return false;
        }
        try {
            UserEntity account = userRepository.findByEmailAndPassword(loginAccount.get(0), loginAccount.get(1));
            if (!(account.getRole().equals("Admin"))) {
                return false;
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


}
