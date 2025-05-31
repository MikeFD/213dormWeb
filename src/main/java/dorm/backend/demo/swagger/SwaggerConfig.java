package dorm.backend.demo.swagger;

import io.swagger.v3.oas.models.OpenAPI;
//import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

//@SpringBootConfiguration
@Configuration
//@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("213宿舍接口文档")
                        .version("1.0.0")
                        .description("API 接口详细描述")
                        .contact(new Contact()
                                .name("213")
                                .email("team@example.com")
                                .url("https://example.com")));
    }
}
