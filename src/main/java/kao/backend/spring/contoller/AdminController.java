package kao.backend.spring.contoller;

import kao.backend.spring.model.OrderEntity;
import kao.backend.spring.model.StatusEntity;
import kao.backend.spring.model.UserEntity;
import kao.backend.spring.repository.OrderDetailRepository;
import kao.backend.spring.repository.OrderRepository;
import kao.backend.spring.repository.StatusRepository;
import kao.backend.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    StatusRepository statusRepository;

    @GetMapping("/allAccount")
    public ResponseEntity<List<UserEntity>> getAll(HttpSession session) {
        if (checkForAdmin(session)) {
            return ResponseEntity.ok(userRepository.findAll());
        } else {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id, HttpSession session) {
        if (!(checkForAdmin(session))) {
            return ResponseEntity.badRequest().body("You not admin");
        }
        UserEntity user = userRepository.findById(id);
        userRepository.delete(user);
        return ResponseEntity.ok("Delete Account");
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<String> editRole(@PathVariable int id, @RequestParam String role, HttpSession session) {
        if (!(checkForAdmin(session))) {
            return ResponseEntity.badRequest().body("You not admin");
        }
        UserEntity user = userRepository.findById(id);
        user.setRole(role);
        userRepository.save(user);
        return ResponseEntity.ok("role change");
    }

    @GetMapping("/allOrder")
    private ResponseEntity<List<OrderEntity>> showAll(HttpSession session) {
        if (!(checkForAdmin(session))) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @PutMapping("/edit/order")
    public ResponseEntity<String> editStatus(@RequestParam int orderId, @RequestParam int statusId,HttpSession session) {
        if (!(checkForAdmin(session))) {
            return ResponseEntity.badRequest().body("You not admin");
        }
        try {
            OrderEntity order = orderRepository.findById(orderId);
            StatusEntity status = statusRepository.findById(statusId);
            order.setStatus(status);
            System.out.println(order.getStatus().getName());
            orderRepository.save(order);
            return ResponseEntity.ok("Success");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.badRequest().body("Not Found");
    }

    private Boolean checkForAdmin(HttpSession session) {
        List<String> loginAccount = (List<String>) session.getAttribute("Account");
        if (loginAccount == null || loginAccount.isEmpty()) {
            return false;
        }
        try {
            UserEntity account = userRepository.findByEmailAndPassword(loginAccount.get(0), loginAccount.get(1));
            System.out.println(account.getRole());
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
