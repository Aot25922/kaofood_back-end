package kao.backend.spring.contoller;

import kao.backend.spring.model.OrderDetailEntity;
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
    @Autowired
    @GetMapping("")
    private ResponseEntity<List<OrderDetailEntity>> showAll() {return ResponseEntity.ok(orderDetailRepository.findAll());}
}
