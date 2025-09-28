# AiCharacterCoser

议题二：开发一个利用 AI 来做角色扮演的网站，用户可以搜索自己感兴趣的角色例如哈利波特、苏格拉底等并可与其进行语音聊天。

## 项目前端启动：

使用vscode 等开发工具打开该项目的AiCharacter-fronted目录
在.env.development文件下和env.ts文件下 配置API_BASE_URL地址

`VITE_API_BASE_URL=http://localhost:8080/api`

`// API 基础地址
export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'`

输入命令

`npm install`

来安装必要运行环境
然后输入命令

`npm run dev`

来启动前端

## 项目后端启动

# AiCharacterCoser 项目部署指南

本文档用于指导如何快速配置并启动 AiCharacterCoser 项目，包含依赖下载、数据库配置、YAML 配置及项目启动全流程。


## 1. 环境准备
- 开发工具：IntelliJ IDEA（或其他支持 Maven、Spring Boot 的 IDE）
- 依赖管理：Maven 3.6+
- 数据库：MySQL 8.0+
- 缓存：Redis 6.0+
- JDK 版本：JDK 11+


## 2. 项目初始化步骤
### 步骤 1：导入项目并下载依赖
1. 用 IDEA 打开本项目；
2. 在 IDEA 中找到项目根目录下的 `pom.xml` 文件，右键选择 **Maven → Reload Project**；
3. 等待 Maven 自动下载所有依赖（确保网络通畅，首次下载可能耗时较长）。


### 步骤 2：创建数据库
1. 打开项目的 `sql` 目录，找到 `create_table.sql` 文件；
2. 用 MySQL 客户端（如 Navicat、DataGrip）连接你的 MySQL 服务；
3. 执行 `create_table.sql` 中的 SQL 语句，创建项目所需的 `ai_character` 数据库及表结构。


### 步骤 3：配置 application.yml 文件
项目启动前需配置数据库、Redis、AI 接口等信息，具体操作如下：
1. 在项目路径 `src/main/resources` 下，新建文件并命名为 `application.yml`；
2. 复制以下配置内容到文件中，**根据你的本地环境修改标注 `# 需替换` 的字段**（如数据库账号密码、Redis 地址、AI API Key）：

```yaml
# 项目基础配置
spring:
  application:
    name: AiCharacterCoser  # 项目名称，无需修改
  # 数据库配置
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/ai_character?useSSL=false&serverTimezone=UTC  # 需替换：localhost:3306 为你的 MySQL 地址，ai_character 为数据库名
    username: your-username  # 需替换：你的 MySQL 账号（如 root）
    password: your-password  # 需替换：你的 MySQL 密码
  # Session 配置
  session:
    timeout: 86400  # Session 过期时间（秒），无需修改
  # Redis 配置
  data:
    redis:
      host: localhost  # 需替换：你的 Redis 地址（本地默认 localhost）
      port: 6379       # 需替换：你的 Redis 端口（默认 6379）
      password: ""     # 需替换：你的 Redis 密码（无密码则留空）
      ttl: 3600        # Redis 缓存过期时间（秒），无需修改
      database: 0      # Redis 数据库索引（默认 0），无需修改

# 服务器配置
server:
  port: 8080  # 项目启动端口（可修改，避免端口冲突）
  servlet:
    context-path: /api  # 接口访问前缀（如 http://localhost:8080/api/xxx），无需修改

# MyBatis-Plus 配置
mybatis-plus:
  global-config:
    db-config:
      logic-delete-field: isDelete  # 逻辑删除字段名，无需修改
      logic-delete-value: 1         # 逻辑删除值（1=删除），无需修改
      logic-not-delete-value: 0     # 逻辑未删除值（0=正常），无需修改
  configuration:
    map-underscore-to-camel-case: false  # 关闭下划线转驼峰（默认 true，按需求修改）
    # log-impl: org.apache.ibatis.logging.stdout.StdOutImpl  # 开启后控制台打印 SQL，需启用则取消注释

# SpringDoc-OpenAPI（接口文档）配置
springdoc:
  group-configs:
    - group: 'default'
      paths-to-match: '/**'  # 匹配所有接口
      packages-to-scan: com.example.aicharactercoser.controller  # 接口所在包，需与项目实际包名一致
# Knife4j 增强配置（接口文档美化，可选）
knife4j:
  enable: true  # 启用 Knife4j（需引入相关依赖）
  setting:
    language: zh_cn  # 文档语言（中文）

# LangChain4j + AI 接口配置（DeepSeek）
langchain4j:
  open-ai:
    chat-model:  # 普通聊天模型
      base-url: https://api.deepseek.com  # DeepSeek API 地址，无需修改
      api-key: your-key  # 需替换：你的 DeepSeek API Key（从 DeepSeek 官网申请）
      model-name: deepseek-chat  # 模型名称，无需修改
      log-requests: true  # 打印请求日志，无需修改
      log-responses: true # 打印响应日志，无需修改
      max-tokens: 8192   # 最大 Token 数，无需修改
      strict-json-schema: true  # 严格 JSON 格式校验，无需修改
      response-format: json_object  # 响应格式为 JSON，无需修改
    streaming-chat-model:  # 流式聊天模型（实时响应）
      base-url: https://api.deepseek.com
      api-key: your-key  # 同上，需替换为你的 DeepSeek API Key
      model-name: deepseek-chat
      max-tokens: 8192
      log-requests: true
      log-responses: true
```
配置完成后直接在项目启动项AiCharacterCoserApplication启动项目