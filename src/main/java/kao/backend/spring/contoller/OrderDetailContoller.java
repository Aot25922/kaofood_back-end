package kao.backend.spring.contoller;

import kao.backend.spring.model.OrderDetailEntity;
import kao.backend.spring.repository.MenuRepository;
import kao.backend.spring.repository.OrderDetailRepository;
import kao.backend.spring.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/orderdetail")
public class OrderDetailContoller {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MenuRepository menuRepository;
    @GetMapping("")
    private ResponseEntity<List<OrderDetailEntity>> showAll() {return ResponseEntity.ok(orderDetailRepository.findAll());}

//    @PostMapping("/new")
//    private ResponseEntity<String> newDetail(@RequestParam int orderId,@RequestParam int menuId,@RequestParam int count){
//        try {
//            OrderDetailEntity newOrderDetail = new OrderDetailEntity(orderRepository.getById(orderId), menuRepository.getById(menuId), count);
//            orderDetailRepository.save(newOrderDetail);
//        }catch (NullPointerException e){
//            System.out.println(e.getStackTrace());
//        }
//        return  ResponseEntity.ok("Order Success");
//    }
}
