package com.example.aicharactercoser;

import dev.langchain4j.community.store.embedding.redis.spring.RedisEmbeddingStoreAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.example.aicharactercoser.mapper")
//排除EmbeddingStore的自动装配
@SpringBootApplication(exclude = RedisEmbeddingStoreAutoConfiguration.class)
public class AiCharacterCoserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiCharacterCoserApplication.class, args);
	}

}
