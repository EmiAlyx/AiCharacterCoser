package com.example.aicharactercoser.ai;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;


@SpringBootTest
public class textPrompt{
    @Autowired
    private  AiCharacterService aiCharacterService;
    @Test
    void textPromptHarryPotter() {
        Flux<String> stringFlux = aiCharacterService.cosHarryPotter("你好，可以介绍一下你自己吗");
        StringBuilder builder = new StringBuilder();

        // 订阅流并处理结果
        stringFlux
                .doOnNext(builder::append)
                .doOnComplete(() -> {
                    System.out.println("哈利·波特的回应: " + builder.toString());
                })
                .doOnError(error -> {
                    System.err.println("处理过程中发生错误: " + error.getMessage());
                })
                .blockLast(); // 阻塞等待流完成，适用于测试场景

        Assertions.assertNotNull(builder);
        Assertions.assertFalse(builder.isEmpty(), "回应内容不应为空");
    }
    @Test
    void textPromptSocrates(){
        Flux<String> stringFlux = aiCharacterService.cosSocrates("你好，可以介绍一下你自己吗");
        StringBuilder builder = new StringBuilder();

        // 订阅流并处理结果
        stringFlux
                .doOnNext(builder::append)
                .doOnComplete(() -> {
                    System.out.println("苏格拉底的回应: " + builder.toString());
                })
                .doOnError(error -> {
                    System.err.println("处理过程中发生错误: " + error.getMessage());
                })
                .blockLast(); // 阻塞等待流完成，适用于测试场景

        Assertions.assertNotNull(builder);
        Assertions.assertFalse(builder.isEmpty(), "回应内容不应为空");
    }
}
