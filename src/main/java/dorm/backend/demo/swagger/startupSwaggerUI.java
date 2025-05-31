package dorm.backend.demo.swagger;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class startupSwaggerUI implements ApplicationListener<ApplicationReadyEvent> {
    private static final String SwaggerURL = "http://localhost:8088/swagger-ui/index.html";

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("--------------------------------------------------");
        System.out.println("接口文档地址: " + SwaggerURL);
        System.out.println("--------------------------------------------------");
    }
}
