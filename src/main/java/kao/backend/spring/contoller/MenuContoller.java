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
