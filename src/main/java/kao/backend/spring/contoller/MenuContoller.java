package kao.backend.spring.contoller;

import kao.backend.spring.model.MenuEntity;
import kao.backend.spring.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/menu")
public class MenuContoller {
    @Autowired
    private MenuRepository menuRepository;
    @GetMapping("")
    private List<MenuEntity> showAll() {return menuRepository.findAll();}
}
