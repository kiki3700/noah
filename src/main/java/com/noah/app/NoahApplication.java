package com.noah.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.noah.app")
@ComponentScan("com.noah.app")
@EnableAutoConfiguration
@EnableScheduling
@MapperScan("com.noah.app")
@EntityScan(basePackages="com.noah.app")
public class NoahApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoahApplication.class, args);
	}

}
