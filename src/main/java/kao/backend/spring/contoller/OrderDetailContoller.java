package kao.backend.spring.contoller;

import kao.backend.spring.model.OrderDetailEntity;
import kao.backend.spring.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/orderdetail")
public class OrderDetailContoller {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @GetMapping("")
    private List<OrderDetailEntity> showAll() {return orderDetailRepository.findAll();}
}