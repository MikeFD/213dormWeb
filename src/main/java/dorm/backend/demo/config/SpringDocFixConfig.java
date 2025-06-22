package dorm.backend.demo.config;

import org.springdoc.core.service.OpenAPIService;
import org.springdoc.core.service.SecurityService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collections;

@Configuration
public class SpringDocFixConfig {

    @Bean
    public static BeanPostProcessor springDocBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof OpenAPIService) {
                    try {
                        Field field = OpenAPIService.class.getDeclaredField("controllerAdviceBeans");
                        ReflectionUtils.makeAccessible(field);
                        field.set(bean, Collections.emptyList());
                    } catch (Exception e) {
                        // 静默处理错误
                    }
                }
                return bean;
            }
        };
    }
}