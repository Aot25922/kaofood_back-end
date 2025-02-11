package kao.backend.spring.contoller;

import kao.backend.spring.model.CategoryEntity;
import kao.backend.spring.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    //Get all category
    @GetMapping("")
    private ResponseEntity<List<CategoryEntity>> getAll() { return ResponseEntity.ok(categoryRepository.findAll()); }
}
