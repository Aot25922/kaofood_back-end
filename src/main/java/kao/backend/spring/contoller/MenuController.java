package kao.backend.spring.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kao.backend.spring.model.MenuEntity;
import kao.backend.spring.model.UserEntity;
import kao.backend.spring.repository.MenuRepository;
import kao.backend.spring.repository.UserRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    UserRepository userRepository;

    //Get all menu
    @GetMapping("")
    private ResponseEntity<List<MenuEntity>> getAll() {
        return ResponseEntity.ok().body(menuRepository.findAll());
    }

    //Get menu image
    @GetMapping("/image/{image}")
    public ResponseEntity<String> getImageAsByteArray(HttpServletResponse response, @PathVariable String image) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        try (FileInputStream in = new FileInputStream("./storage/menu/image/" + image);
             OutputStream out = response.getOutputStream()) {
            IOUtils.copy(in, out);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            return ResponseEntity.internalServerError().body("Cannot found image name : "+image);
        }
        return ResponseEntity.ok("success");
    }

    //Add new Menu and picture
    @PostMapping("/add")
    public ResponseEntity<String> addMenu(@RequestParam String menu, @RequestParam MultipartFile multipartFile, HttpSession session) throws IOException {
        MenuEntity newMenu = null;
        List<String> loginAccount = (List<String>) session.getAttribute("Account");
        UserEntity user = userRepository.findByEmailAndPassword(loginAccount.get(0), loginAccount.get(1));
        if (user.getRole().getName().equals("Member")) {
            return ResponseEntity.badRequest().body("You role is \"Member\"");
        }
        try {
            newMenu = objectMapper.readValue(menu, MenuEntity.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Cannot mapped JSON");
        }
        if (menuRepository.existsByNameIsLikeIgnoreCase(newMenu.getName())) {
            return ResponseEntity.badRequest().body("You menu is \"Already Exist! \"");
        }
        String type = ".png";
        try {
            if(multipartFile == null){
                return ResponseEntity.badRequest().body("No image");
            }else {
                String oldname = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                type = oldname.substring(oldname.lastIndexOf("."));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Cannot get type from image");
        }
        String fileName = newMenu.getName() + type;
        String uploadDir = "./storage/menu/image/";
        newMenu.setImage("/menu/image/" + fileName);
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (FileSystemException e) {
                System.out.println(e.getMessage());
                return  ResponseEntity.internalServerError().body("Cannot create directory");
            }
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            return ResponseEntity.internalServerError().body("IO Problem");
        }
        menuRepository.save(newMenu);
        return ResponseEntity.ok("success");
    }

    //Edit Menu and Picture
    @PutMapping("/edit")
    public ResponseEntity<String> editMenu(@RequestParam String menu, @RequestParam(required = false) MultipartFile multipartFile, HttpSession session) {
        MenuEntity editMenu = null;
        List<String> loginAccount = (List<String>) session.getAttribute("Account");
        UserEntity user = userRepository.findByEmailAndPassword(loginAccount.get(0), loginAccount.get(1));
        if (user.getRole().getName().equals("Member")) {
            return ResponseEntity.badRequest().body("You role is \"Member\"");
        }
        try {
            editMenu = objectMapper.readValue(menu, MenuEntity.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("This menu is \"Can't Convert to Entity\"");
        }
        try {
            MenuEntity Menu = menuRepository.getById(editMenu.getId());
            if(!(Menu.getName().equals(editMenu.getName()))){
                if(menuRepository.existsByNameIsLikeIgnoreCase(editMenu.getName())){
                    return ResponseEntity.badRequest().body("You menu is \"Already Exist! \"");
                }
                Menu.setName(editMenu.getName());
            }
            Menu.setPrice(editMenu.getPrice());
            Menu.setDescription(editMenu.getDescription());
            Menu.setCategory(editMenu.getCategory());
            if (multipartFile != null) {
                String type = ".png";
                try {
                    String oldname = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                    type = oldname.substring(oldname.lastIndexOf("."));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return ResponseEntity.internalServerError().body("Multipart file error");
                }
                String fileName = Menu.getName() + type;
                String uploadDir = "./storage/menu/image/";
                Menu.setImage("/menu/image/" + fileName);
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    try {
                        Files.createDirectories(uploadPath);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        return ResponseEntity.internalServerError().body("IO Problem");
                    }
                }
                try (InputStream inputStream = multipartFile.getInputStream()) {
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                    return ResponseEntity.internalServerError().body("IO Problem");
                }
            }
            menuRepository.save(Menu);
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("Not found this menu");
        }
        return ResponseEntity.ok("success");

    }

    //Delete Menu
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable int id, HttpSession session) {
        List<String> loginAccount = (List<String>) session.getAttribute("Account");
        UserEntity user = userRepository.findByEmailAndPassword(loginAccount.get(0), loginAccount.get(1));
        if (user.getRole().getName().equals("Member")) {
            return ResponseEntity.badRequest().body("You role is \"Member\"");
        }
        try {
            MenuEntity menu = menuRepository.findById(id);
            File checkFile = new File("./storage" + menu.getImage());
            if (checkFile.exists()) {
                try {
                    Path oldImgPath = Paths.get("./storage" + menu.getImage());
                    Files.delete(oldImgPath);
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                    return ResponseEntity.internalServerError().body("IO Problem");
                }
            }
            menuRepository.deleteById(id);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("You menu is \"Not Found\"");
        }
        return ResponseEntity.ok("Success");
    }
}
