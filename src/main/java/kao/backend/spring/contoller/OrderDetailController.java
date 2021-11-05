package kao.backend.spring.contoller;

import kao.backend.spring.model.OrderDetailEntity;
import kao.backend.spring.model.OrderEntity;
import kao.backend.spring.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/orderdetail")
public class OrderDetailController {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    //Get all orderDetail
    @GetMapping("")
    private ResponseEntity<List<OrderDetailEntity>> showAll() {return ResponseEntity.ok(orderDetailRepository.findAll());}

    //List all orderDetail of user
//    @GetMapping("/user/{userId}")
//    private ResponseEntity<List<OrderDetailEntity>> getUserOrder(@PathVariable(name = "userId") int userId){
//        List<OrderDetailEntity> orderInfo = orderDetailRepository.findAllByOrders_User_Id(userId);
//        return ResponseEntity.ok(orderInfo);
//    }
}
