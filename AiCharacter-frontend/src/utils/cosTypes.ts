/**
 * 代码生成类型枚举
 */
export enum cosTypeEnum {
  HTML = 'html',
  MULTI_FILE = 'multi_file',
}

/**
 * 代码生成类型配置
 */
export const CODE_GEN_TYPE_CONFIG = {
  [cosTypeEnum.HTML]: {
    label: '原生 HTML 模式',
    value: cosTypeEnum.HTML,
  },
  [cosTypeEnum.MULTI_FILE]: {
    label: '原生多文件模式',
    value: cosTypeEnum.MULTI_FILE,
  },
} as const

/**
 * 代码生成类型选项（用于下拉选择）
 */
export const CODE_GEN_TYPE_OPTIONS = Object.values(CODE_GEN_TYPE_CONFIG).map((config) => ({
  label: config.label,
  value: config.value,
}))

/**
 * 格式化代码生成类型
 * @param type 代码生成类型
 * @returns 格式化后的类型描述
 */
export const formatcosType = (type: string | undefined): string => {
  if (!type) return '未知类型'

  const config = CODE_GEN_TYPE_CONFIG[type as cosTypeEnum]
  return config ? config.label : type
}

/**
 * 获取所有代码生成类型
 */
export const getAllcosTypes = () => {
  return Object.values(cosTypeEnum)
}

/**
 * 检查是否为有效的代码生成类型
 * @param type 待检查的类型
 */
export const isValidcosType = (type: string): type is cosTypeEnum => {
  return Object.values(cosTypeEnum).includes(type as cosTypeEnum)
}
