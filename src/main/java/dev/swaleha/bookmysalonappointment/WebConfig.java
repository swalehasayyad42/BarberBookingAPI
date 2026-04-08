package dev.swaleha.bookmysalonappointment;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS requests from http://localhost:3000 (your React frontend)
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")  // Replace with your frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allowed HTTP methods
                .allowedHeaders("*")  // Allow any headers
                .allowCredentials(true);  // Allow cookies (if needed)
    }
}

