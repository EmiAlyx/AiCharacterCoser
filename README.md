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

使用IDEA等开发工具打开该项目，利用Maven打开pom.xml文件下载项目依赖
打开sql目录下的create_table.sql文件创建数据库
在项目的src.main.resources目录下新建application.yml配置文件
输入以下配置
`spring:
    application:
        name: AiCharacterCoser
    datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost:3306/ai_character # 换为自己url
        username: your-username
        password: your-password
    session:
        timeout: 86400
    data:
        redis:
            host: localhost # 换为自己url
            port: 6379 
            password:
            ttl: 3600
            database: 

server:
    port: 8080
    servlet:
        context-path: /api

mybatis-plus:
    global-config:
        db-config:
            logic-delete-field: isDelete #逻辑删除字段
            logic-delete-value: 1
            logic-not-delete-value: 0 #逻辑未删除值
    configuration:
        map-underscore-to-camel-case: false # 关闭下划线转驼峰（默认是 true）
        # log-impl: org.apache.ibatis.logging.stdout.StdOutImpl  # 控制台打印SQL

    # springdoc-openapi项目配置
springdoc:
    group-configs:
        - group: 'default'
        paths-to-match: '/**'
        packages-to-scan: com.example.aicharactercoser.controller
    # knife4j的增强配置，不需要增强可以不配
knife4j:
    enable: true
    setting:
        language: zh_cn

    # AI
langchain4j:
    open-ai:
        chat-model:
            base-url: https://api.deepseek.com
            api-key: your-key
            model-name: deepseek-chat
            log-requests: true
            log-responses: true
            max-tokens: 8192
            strict-json-schema: true
            response-format: json_object
        streaming-chat-model:
            base-url: https://api.deepseek.com
            api-key: your-key
            model-name: deepseek-chat
            max-tokens: 8192
            log-requests: true
            log-responses: true
`
配置完成后直接在项目启动项AiCharacterCoserApplication启动项目