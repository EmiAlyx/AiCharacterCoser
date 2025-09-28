package com.example.aicharactercoser.ai;



import dev.langchain4j.service.SystemMessage;
import reactor.core.publisher.Flux;

public interface AiCharacterService {
    /**
     * 哈利波特
     * @param  userMessage 用户提示词
     * @return ai的输出结果
     */
    @SystemMessage( fromResource = "prompt/prompt-Harry-Potter.txt")
    Flux<String> cosHarryPotter(String userMessage);
    /**
     * 苏格拉底
     * @param userMessage 用户提示词
     * @return ai的输出结果
     */
    // value ="提示词"   fromResource ="提示词路径"
    @SystemMessage( fromResource = "prompt/prompt-Socrates.txt")
    Flux<String> cosSocrates(String userMessage);
    /**
     * 哪吒
     * @param userMessage 用户提示词
     * @return ai的输出结果
     */
    // value ="提示词"   fromResource ="提示词路径"
    @SystemMessage( fromResource = "prompt/prompt-Ne-Zha.txt")
    Flux<String> cosNeZha(String userMessage);
    /**
     * 敖丙
     * @param userMessage 用户提示词
     * @return ai的输出结果
     */
    // value ="提示词"   fromResource ="提示词路径"
    @SystemMessage( fromResource = "prompt/prompt-Ao-Bing.txt")
    Flux<String> cosAoBing(String userMessage);


}
