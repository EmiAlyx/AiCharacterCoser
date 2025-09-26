package com.example.aicharactercoser.ai;



import dev.langchain4j.service.SystemMessage;
import reactor.core.publisher.Flux;

public interface AiCharacterService {
    /**
     *
     * @param  userMessage 用户提示词
     * @return ai的输出结果
     */
    @SystemMessage( fromResource = "prompt/prompt-Harry-Potter.txt")
    Flux<String> cosHarryPotter(String userMessage);
    /**
     *
     * @param userMessage 用户提示词
     * @return ai的输出结果
     */
    // value ="提示词"   fromResource ="提示词路径"
    @SystemMessage( fromResource = "prompt/prompt-Socrates.txt")
    Flux<String> cosSocrates(String userMessage);


}
