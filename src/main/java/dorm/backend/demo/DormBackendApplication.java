package dorm.backend.demo;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@RestController
@MapperScan("dorm.backend.demo.mapper") // 替换为你自己的 mapper 包路径
public class DormBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DormBackendApplication.class, args);
	}
}
