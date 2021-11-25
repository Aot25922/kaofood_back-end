package kao.backend.spring.contoller;

import kao.backend.spring.model.*;
import kao.backend.spring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

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
    @Autowired
    RoleRepository roleRepository;

    //Get all user account.
    @GetMapping("/allAccount")
    public ResponseEntity<List<UserEntity>> getAll(HttpSession session) {
        if (checkForAdmin(session)) {
            return ResponseEntity.ok(userRepository.findAll());
        } else {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    //Delete user account
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id, HttpSession session) {
        UserEntity user;
        if (!(checkForAdmin(session))) {
            return ResponseEntity.badRequest().body("You not admin");
        }
        try {
            orderDetailRepository.deleteAllByUser_Id(id);
            orderRepository.deleteByUser_Id(id);
            userRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Cannot find user");
        }
        return ResponseEntity.ok("Delete Account");
    }

    //Edit role of user account
    @PutMapping("/edit/role/{id}")
    public ResponseEntity<String> editRole(@PathVariable int id, @RequestParam int roleId, HttpSession session) {
        if (!(checkForAdmin(session))) {
            return ResponseEntity.badRequest().body("You not admin");
        }
        try {
            UserEntity user = userRepository.findById(id);
            RoleEntity role = roleRepository.findById(roleId);
            user.setRole(role);
            userRepository.save(user);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Cannot Edit");
        }
        return ResponseEntity.ok("role change");
    }

    //Edit order status
    @PutMapping("/edit/order")
    public ResponseEntity<String> editStatus(@RequestParam int orderId, @RequestParam int statusId, HttpSession session) {
        List<String> loginAccount = (List<String>) session.getAttribute("Account");
        if (loginAccount == null || loginAccount.isEmpty()) {
            return ResponseEntity.badRequest().body("Account null");
        }
        try {
            UserEntity account = userRepository.findByEmailAndPassword(loginAccount.get(0), loginAccount.get(1));
            if (account.getRole().getName().equals("Member")) {
                return ResponseEntity.badRequest().body("Account is Member");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("null");
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
            return ResponseEntity.internalServerError().body("Cannot find order or status");
        }
    }

    //Check for Admin role
    private Boolean checkForAdmin(HttpSession session) {
        List<String> loginAccount = (List<String>) session.getAttribute("Account");
        if (loginAccount == null || loginAccount.isEmpty()) {
            return false;
        }
        try {
            UserEntity account = userRepository.findByEmailAndPassword(loginAccount.get(0), loginAccount.get(1));
            System.out.println(account.getRole());
            if (!(account.getRole().getName().equals("Admin"))) {
                return false;
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


}
