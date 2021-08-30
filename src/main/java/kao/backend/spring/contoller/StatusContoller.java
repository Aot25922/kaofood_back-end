package kao.backend.spring.contoller;

import kao.backend.spring.model.StatusEntity;
import kao.backend.spring.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/status")
public class StatusContoller {
    @Autowired
    private StatusRepository statusRepository;
    @GetMapping("")
    private List<StatusEntity> showAll() {return statusRepository.findAll();}
}
