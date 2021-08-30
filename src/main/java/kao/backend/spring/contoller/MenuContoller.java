package kao.backend.spring.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kao.backend.spring.model.MenuEntity;
import kao.backend.spring.repository.MenuRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/menu")
public class MenuContoller {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("")
    private List<MenuEntity> showAll() {
        return menuRepository.findAll();
    }

//    Add new Menu and picture
//    @PostMapping("/add")
//    public void addMenu(@RequestParam String menu, @RequestParam MultipartFile multipartFile) {
//        MenuEntity newMenu = null;
//        try {
//            newMenu = objectMapper.readValue(menu, MenuEntity.class);
//        } catch (JsonProcessingException e) {
//            System.out.println(e.getStackTrace());
//        }
//        if (menuRepository.findMenuEntityByMenuName(newMenu.getName()) != null) {
//            return;
//        }
//        String type = ".png";
//        try {
//            String oldname = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            type = oldname.substring(oldname.lastIndexOf("."));
//        } catch (Exception e) {
//            System.out.println(e.getStackTrace());
//        }
//        String fileName = newMenu.getName() + type;
//        String uploadDir = "./storage/image/";
//        newMenu.setImage(uploadDir + fileName);
//        Path uploadPath = Paths.get(uploadDir);
//        if (!Files.exists(uploadPath)) {
//            try {
//                Files.createDirectories(uploadPath);
//            } catch (Exception e) {
//                System.out.println(e.getStackTrace());
//            }
//        }
//        try (InputStream inputStream = multipartFile.getInputStream()) {
//            Path filePath = uploadPath.resolve(fileName);
//            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException ioe) {
//            System.out.println(ioe.getMessage());
//        }
//        menuRepository.save(newMenu);
//    }
//
//    //add only picture
//    @PutMapping("/add/picture")
//    public void addPicture(@RequestParam("pic") MultipartFile multipartFile) {
//        String type;
//        try {
//            String oldname = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            type = oldname.substring(oldname.lastIndexOf("."));
//        } catch (Exception e) {
//            System.out.println(e.getStackTrace());
//        }
//        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        String uploadDir = "./storage/image/";
//        Path uploadPath = Paths.get(uploadDir);
//        if (!Files.exists(uploadPath)) {
//            try {
//                Files.createDirectories(uploadPath);
//            } catch (Exception e) {
//                System.out.println(e.getStackTrace());
//            }
//        }
//        try (InputStream inputStream = multipartFile.getInputStream()) {
//            Path filePath = uploadPath.resolve(fileName);
//            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException ioe) {
//            System.out.println(ioe.getMessage());
//        }
//    }
//
//    @GetMapping("/picture/{image}")
//    public void getImageAsByteArray(HttpServletResponse response, @PathVariable String image) throws IOException {
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        try(FileInputStream in = new FileInputStream("./storage/image/"+image);
//            OutputStream out = response.getOutputStream()) {
//            IOUtils.copy(in, out);
//        }catch (IOException ioe){
//            System.out.println(ioe.getMessage());
//        }
//
//    }
}
