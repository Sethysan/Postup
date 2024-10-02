package com.techelevator.model;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override

    public void addResourceHandlers(ResourceHandlerRegistry registry){
        // Serve images from the 'images/' directory under the root of the project
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/images/");
    }
}
