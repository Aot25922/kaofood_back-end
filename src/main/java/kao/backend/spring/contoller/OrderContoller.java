package kao.backend.spring.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kao.backend.spring.model.*;
import kao.backend.spring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderContoller {
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

    @PostMapping(path="/new")
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
            System.out.println(e.getStackTrace());
        }
            return ResponseEntity.ok().body("Success");

    }

//    @GetMapping("/test")
//    public void test(@RequestBody menuRequest[] menuList){
//        for(menuRequest i : menuList){
//            System.out.println(i.getMenuId());
//        }
//    }


}
