package kao.backend.spring.contoller;

import kao.backend.spring.model.TypeEntity;
import kao.backend.spring.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/type")
public class TypeContoller {
    @Autowired
    private TypeRepository typeRepository;
    @GetMapping("")
    private List<TypeEntity> showAllType() { return typeRepository.findAll(); }
}
