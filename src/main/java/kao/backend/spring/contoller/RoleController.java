package kao.backend.spring.contoller;

import kao.backend.spring.model.RoleEntity;
import kao.backend.spring.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleRepository roleRepository;

    //Get all role
    @GetMapping("")
    public ResponseEntity<List<RoleEntity>> getAllrole(){
        return ResponseEntity.ok(roleRepository.findAll());
    }
}
