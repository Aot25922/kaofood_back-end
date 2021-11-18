package kao.backend.spring.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kao.backend.spring.model.*;
import kao.backend.spring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private MenuRepository menuRepository;

    //Get all order
    @GetMapping("")
    private ResponseEntity<List<OrderEntity>> showAll() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    //Create new order
    @PostMapping("/new")
    private ResponseEntity<String> newOrder(@RequestParam int userId,@RequestBody List<menuRequest> menuList) throws JsonProcessingException {
        UserEntity user = userRepository.findById(userId);
        int totalPrice = 0;
        try {
            OrderEntity newOrder = new OrderEntity(totalPrice, user, statusRepository.getById(1));
            newOrder.setId(orderRepository.findAll().get(orderRepository.findAll().size()-1).getId()+1);
            orderRepository.save(newOrder);
            for(menuRequest i : menuList){
                MenuEntity userMenu = menuRepository.findById(i.getMenuId());
                OrderDetailEntity newOrderDetail = new OrderDetailEntity(newOrder,userMenu,i.getCount() );
                newOrderDetail.setId(orderDetailRepository.findAll().get(orderDetailRepository.findAll().size()-1).getId()+1);
                totalPrice += userMenu.getPrice()*newOrderDetail.getCount();
                orderDetailRepository.save(newOrderDetail);
            }
            newOrder.setTotalPrice(totalPrice);
            orderRepository.save(newOrder);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
            return ResponseEntity.ok().body("Success");

    }

    @GetMapping("/user/{userId}")
    private ResponseEntity<List<OrderEntity>> userOrder(@PathVariable("userId") int userId, HttpSession session){
        List<String> loginAccount = (List<String>) session.getAttribute("Account");
        UserEntity user = userRepository.findByEmailAndPassword(loginAccount.get(0), loginAccount.get(1));
        if (user.getId() != userId) {
            return ResponseEntity.badRequest().body(null);
        }
        List<OrderEntity> orderUser = orderRepository.findAllByUser_Id(userId);
        return ResponseEntity.ok(orderUser);
    }

}
