/**
 * 对话生成类型枚举
 */
export enum cosTypeEnum {
  HARRY_POTTER=( "harry_potter"),
  NE_ZHA=( "ne_zha"),
  AO_BING=( "ao_bing"),
  SOCRATES=( "socrates"),
}

/**
 * 对话生成类型配置
 */
export const COS_TYPE_CONFIG = {
  [cosTypeEnum.HARRY_POTTER]: {
    label: '哈利波特模式',
    value: cosTypeEnum.HARRY_POTTER,
  },
  [cosTypeEnum.NE_ZHA]: {
    label: '哪吒模式',
    value: cosTypeEnum.NE_ZHA,
  },
  [cosTypeEnum.AO_BING]: {
    label: '敖丙模式',
    value: cosTypeEnum.AO_BING,
  },
  [cosTypeEnum.SOCRATES]: {
    label: '苏格拉底模式',
    value: cosTypeEnum.SOCRATES,
  },

} as const

/**
 * 对话生成类型选项（用于下拉选择）
 */
export const CODE_GEN_TYPE_OPTIONS = Object.values(COS_TYPE_CONFIG).map((config) => ({
  label: config.label,
  value: config.value,
}))

/**
 * 格式化对话生成类型
 * @param type 对话生成类型
 * @returns 格式化后的类型描述
 */
export const formatcosType = (type: string | undefined): string => {
  if (!type) return '未知类型'

  const config = COS_TYPE_CONFIG[type as cosTypeEnum]
  return config ? config.label : type
}

/**
 * 获取所有对话生成类型
 */
export const getAllcosTypes = () => {
  return Object.values(cosTypeEnum)
}

/**
 * 检查是否为有效的对话生成类型
 * @param type 待检查的类型
 */
export const isValidcosType = (type: string): type is cosTypeEnum => {
  return Object.values(cosTypeEnum).includes(type as cosTypeEnum)
}
