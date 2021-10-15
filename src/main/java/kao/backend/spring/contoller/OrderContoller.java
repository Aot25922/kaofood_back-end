package kao.backend.spring.contoller;

import kao.backend.spring.model.OrderEntity;
import kao.backend.spring.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderContoller {
    @Autowired
    private OrderRepository orderRepository;
    @GetMapping("")
    private ResponseEntity<List<OrderEntity>> showAll() {return ResponseEntity.ok(orderRepository.findAll());}

}
