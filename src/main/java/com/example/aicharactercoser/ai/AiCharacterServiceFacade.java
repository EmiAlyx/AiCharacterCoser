package com.example.aicharactercoser.ai;

import com.example.aicharactercoser.exception.BusinessException;
import com.example.aicharactercoser.exception.ErrorCode;
import com.example.aicharactercoser.exception.ThrowUtils;
import com.example.aicharactercoser.model.enums.AiCosTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;



@Service
@Slf4j
@RequiredArgsConstructor
public class AiCharacterServiceFacade {
    private final AiCharacterServiceFactory aiCharacterServiceFactory;


    /**
     * 统一入口：根据类型调用不同角色prompt
     *
     * @param userMessage     用户信息
     * @param aiCosTypeEnum 生成类型
     * @return 保存的目录
     */
    public Flux<String> generateChatStream(String userMessage, AiCosTypeEnum aiCosTypeEnum, Long appId) {
        ThrowUtils.throwIf(aiCosTypeEnum == null, ErrorCode.SYSTEM_ERROR, "生成类型为空");
        AiCharacterService aiCharacterService
                =aiCharacterServiceFactory.getAiCharacterService(appId);

        return switch (aiCosTypeEnum) {
            case HARRY_POTTER -> {
                Flux<String> result = aiCharacterService.cosHarryPotter(userMessage);
                yield prcessChatStream(result);
            }
            case SOCRATES -> {

                Flux<String> result = aiCharacterService.cosSocrates(userMessage);
                yield prcessChatStream(result);
            }
            case AO_BING -> {
                Flux<String> result = aiCharacterService.cosAoBing(userMessage);
                yield prcessChatStream(result);
            }
            case NE_ZHA -> {
                Flux<String> result = aiCharacterService.cosNeZha(userMessage);
                yield prcessChatStream(result);
            }
            default -> {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR,
                        "不支持的类型" + aiCosTypeEnum.getValue());
            }
        };
    }
    private Flux<String> prcessChatStream(Flux<String> result) {
        StringBuilder builder = new StringBuilder();
        return result.doOnNext(builder::append).doOnComplete(() -> {
                String outer= builder.toString();
        });
    }


}