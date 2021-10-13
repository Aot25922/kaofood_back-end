package kao.backend.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class KaofoodBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(KaofoodBackEndApplication.class, args);
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/menu").allowedOrigins("http://localhost").allowedMethods("*").allowedHeaders("*");
//                registry.addMapping("/user/login").allowedOrigins("http://localhost").allowedMethods("*").allowedHeaders("*").allowCredentials(true).exposedHeaders("JWT");
//                registry.addMapping("/user/logout").allowedOrigins("http://localhost").allowedMethods("*").allowedHeaders("*").allowCredentials(true);
//            }
//        };
//
//    }
}
