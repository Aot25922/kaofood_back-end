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
import java.io.*;
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
    private List<MenuEntity> getAll() {
        return menuRepository.findAll();
    }

    @GetMapping("/image/{image}")
    public void getImageAsByteArray(HttpServletResponse response, @PathVariable String image) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        try(FileInputStream in = new FileInputStream("./storage/menu/image/"+image);
            OutputStream out = response.getOutputStream()) {
            IOUtils.copy(in, out);
        }catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }

    //Add new Menu and picture
    @PostMapping("/add")
    public void addMenu(@RequestParam String menu, @RequestParam MultipartFile multipartFile) throws IOException {
        MenuEntity newMenu = null;
        try {
            newMenu = objectMapper.readValue(menu, MenuEntity.class);
        }catch (JsonProcessingException e){
            System.out.println(e.getStackTrace());
        }
        if (menuRepository.existsByNameIsLikeIgnoreCase(newMenu.getName())) {
            return;
        }
        String type = ".png";
        try {
            String oldname = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            type = oldname.substring(oldname.lastIndexOf("."));
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        String fileName = newMenu.getName() + type;
        String uploadDir = "./storage/menu/image/";
        newMenu.setImage("/menu/image/" + fileName);
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        menuRepository.save(newMenu);
    }

    //Edit Menu and Picture
    @PutMapping("/edit")
    public void editMenu(@RequestParam String menu, @RequestParam (required = false) MultipartFile multipartFile){
        MenuEntity editMenu = null;
        try {
            editMenu = objectMapper.readValue(menu, MenuEntity.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getStackTrace());
        }
        MenuEntity Menu = menuRepository.getById(editMenu.getId());
        Menu.setName(editMenu.getName());
        Menu.setPrice(editMenu.getPrice());
        Menu.setDescription(editMenu.getDescription());
        Menu.setCategory(editMenu.getCategory());
        if(multipartFile!=null) {
            String type = ".png";
            try {
                String oldname = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                type = oldname.substring(oldname.lastIndexOf("."));
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
            String fileName = Menu.getName() + type;
            String uploadDir = "./storage/menu/image/";
            Menu.setImage("/menu/image/" + fileName);
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                try {
                    Files.createDirectories(uploadPath);
                } catch (Exception e) {
                    System.out.println(e.getStackTrace());
                }
            }
            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
        menuRepository.save(Menu);

    }

    //Delete Menu
    @DeleteMapping("delete/{id}")
    public void deleteMenu(@PathVariable int id){
      MenuEntity menu = menuRepository.findById(id);
        File checkFile=new File("./storage"+menu.getImage());
        if(checkFile.exists()) {
            try {
                Path oldImgPath = Paths.get("./storage"+menu.getImage());
                Files.delete(oldImgPath);
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
        menuRepository.deleteById(id);
    }
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
}
