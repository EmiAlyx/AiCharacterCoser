package com.example.aicharactercoser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.example.aicharactercoser.mapper")
@SpringBootApplication
public class AiCharacterCoserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiCharacterCoserApplication.class, args);
	}

}
