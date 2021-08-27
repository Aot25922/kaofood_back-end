package kao.backend.spring.contoller;

import kao.backend.spring.model.CategoryEntity;
import kao.backend.spring.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryContoller {
    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping("")
    private List<CategoryEntity> showAllType() { return categoryRepository.findAll(); }
}
