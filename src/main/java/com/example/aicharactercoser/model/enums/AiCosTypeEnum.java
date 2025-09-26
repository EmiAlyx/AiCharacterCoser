package com.example.aicharactercoser.model.enums;

import cn.hutool.core.util.ObjUtil;

import lombok.Getter;

@Getter
public enum AiCosTypeEnum {
    HARRY_POTTER("哈利波特模式", "harry_potter"),
    SOCRATES("苏格拉底模式", "socrates");

    private final String text;
    private final String value;

    AiCosTypeEnum (String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 枚举值的value
     * @return 枚举值
     */
    public static AiCosTypeEnum  getEnumByValue(String value) {
        if (ObjUtil.isEmpty(value)) {
            return null;
        }
        for (AiCosTypeEnum  anEnum : AiCosTypeEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }


}
