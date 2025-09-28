# AiCharacterCoser

议题二：开发一个利用 AI 来做角色扮演的网站，用户可以搜索自己感兴趣的角色例如哈利波特、苏格拉底等并可与其进行语音聊天。
# AiCharacterCoser 框架设计文档

## 1. 项目概述
 
AiCharacterCoser 是一个利用 AI 实现角色扮演功能的网站平台，用户可以搜索感兴趣的角色（如哈利波特、苏格拉底等）并与其进行语音聊天互动。该项目采用前后端分离架构，结合 AI 大模型能力，提供沉浸式的角色对话体验。

## 2. 技术架构

### 2.1 整体架构

采用前后端分离架构：
- 前端：Vue 3 + TypeScript + Ant Design Vue
- 后端：Spring Boot 3 + MyBatis-Plus
- 数据库：MySQL 8.0+
- 缓存：Redis 6.0+
- AI 集成：LangChain4j + DeepSeek API
- 构建工具：前端使用 Vite，后端使用 Maven

### 2.2 前端架构

#### 2.2.1 技术栈详情
- 核心框架：Vue 3（Composition API）
- 类型系统：TypeScript
- UI 组件库：Ant Design Vue
- 状态管理：Pinia
- 路由管理：Vue Router
- 构建工具：Vite
- HTTP 客户端：Axios
- 代码规范：ESLint + Prettier
- Markdown 处理：markdown-it + highlight.js

#### 2.2.2 目录结构
```
AiCharacter-frontend/
├── src/
│   ├── api/               # API 接口定义
│   ├── components/        # 通用组件
│   ├── layouts/           # 布局组件
│   ├── pages/             # 页面组件
│   ├── router/            # 路由配置
│   ├── stores/            # Pinia 状态管理
│   ├── App.vue            # 根组件
│   └── main.ts            # 入口文件
├── index.html             # HTML 入口
├── package.json           # 依赖配置
└── vite.config.ts         # Vite 配置
```

#### 2.2.3 核心模块
- 路由管理：基于 Vue Router 实现页面跳转和权限控制
- 状态管理：使用 Pinia 管理全局状态（如用户信息、应用列表）
- API 通信：封装统一的接口请求工具，与后端进行数据交互
- UI 组件：基于 Ant Design Vue 构建用户界面，包含自定义组件（如 AppCard、MarkdownRenderer）

### 2.3 后端架构

#### 2.3.1 技术栈详情
- 核心框架：Spring Boot 3
- ORM 框架：MyBatis-Plus
- 数据库：MySQL 8.0+
- 缓存：Redis + Redisson
- AI 集成：LangChain4j + DeepSeek API
- 接口文档：Knife4j（基于 OpenAPI）
- 工具库：Hutool
- 构建工具：Maven

#### 2.3.2 目录结构
```
AiCharacterCoser/
├── src/main/java/com/example/aicharactercoser/
│   ├── ai/                # AI 相关服务
│   ├── controller/        # 控制器
│   ├── mapper/            # 数据访问层
│   ├── model/             # 数据模型
│   ├── service/           # 业务逻辑层
│   └── AiCharacterCoserApplication.java  # 应用入口
├── src/main/resources/
│   ├── application.yml    # 应用配置
│   └── prompt/            # AI 提示词模板
└── pom.xml                # Maven 配置
```

#### 2.3.3 核心模块
- 控制器层：处理 HTTP 请求，提供 RESTful API
- 服务层：实现业务逻辑，包括用户管理、对话管理等
- AI 服务层：封装 LangChain4j，实现角色对话功能
- 数据访问层：基于 MyBatis-Plus 操作数据库
- 配置中心：管理数据库、Redis、AI 接口等配置

## 3. 核心功能模块

### 3.1 用户管理模块
- 用户注册、登录、信息管理
- 基于 Spring Session 实现会话管理
- 权限控制（普通用户、管理员）

### 3.2 角色对话模块
- 角色选择（哈利波特、苏格拉底、哪吒、敖丙等）
- 对话历史记录与展示
- 实时流式对话（基于 SSE）
- 对话记忆管理（基于 Redis）

### 3.3 AI 服务模块
- 角色提示词模板管理
- AI 模型调用封装（DeepSeek）
- 流式响应处理
- 对话记忆存储与加载

### 3.4 应用管理模块
- 对话应用创建与管理
- 应用列表展示（我的应用、精选应用）
- 分页查询与排序

## 4. 数据库设计

核心数据表包括：
- 用户表（user）：存储用户信息
- 应用表（app）：存储对话应用信息
- 对话历史表（chat_history）：存储对话记录

主要字段设计：
- 应用表：id、名称、封面、初始提示词、角色类型、用户ID、创建时间等
- 对话历史表：id、消息内容、消息类型、应用ID、用户ID、创建时间等

## 5. 配置说明

### 5.1 前端配置
- API 基础地址配置：`.env.development` 和 `env.ts` 中设置 `VITE_API_BASE_URL`
- 依赖管理：`package.json` 定义项目依赖

### 5.2 后端配置
- 服务器配置：端口、上下文路径（`server` 配置）
- 数据库配置：MySQL 连接信息
- Redis 配置：缓存连接信息
- AI 接口配置：DeepSeek API 地址、API Key 等（`langchain4j` 配置）
- MyBatis-Plus 配置：数据库操作相关配置
- 接口文档配置：Knife4j 相关配置

## 6. 部署流程

### 6.1 环境准备
- JDK 11+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- Node.js 16+

### 6.2 后端部署
1. 配置数据库，执行 `sql/create_table.sql` 创建表结构
2. 配置 `application.yml`，设置数据库、Redis、AI 接口等信息
3. 使用 Maven 构建项目：`mvn clean package`
4. 启动应用：`java -jar AiCharacterCoser.jar`

### 6.3 前端部署
1. 配置 API 基础地址：`VITE_API_BASE_URL`
2. 安装依赖：`npm install`
3. 开发环境启动：`npm run dev`
4. 生产环境构建：`npm run build`，部署生成的 `dist` 目录

## 7. 扩展与维护

### 7.1 新增角色
1. 添加角色提示词模板到 `resources/prompt` 目录
2. 在 `AiCharacterService` 接口中添加对应方法，并使用 `@SystemMessage` 注解指定提示词
3. 在 `AiCharacterServiceFacade` 中添加对应的处理逻辑
4. 前端添加角色选择选项和描述信息

### 7.2 性能优化
- AI 服务实例缓存：使用 Caffeine 缓存 AI 服务实例，设置过期策略
- 对话记忆管理：限制单对话最大消息数，优化内存使用
- 数据库查询优化：添加适当索引，优化分页查询

### 7.3 可扩展性考虑
- 角色类型通过枚举管理，便于扩展新角色
- AI 服务通过工厂模式创建，便于替换不同的 AI 模型
- 前后端通过 API 通信，便于各自独立升级

## 8. 总结

AiCharacterCoser 项目通过前后端分离架构，结合 AI 大模型能力，实现了一个功能完善的角色扮演聊天平台。项目架构清晰，模块划分合理，具有良好的可扩展性和维护性。通过 LangChain4j 框架整合 AI 能力，结合 Redis 实现对话记忆，为用户提供了沉浸式的角色对话体验。

# AiCharacterCoser 项目前端启动指南
## 项目前端启动
## 1. 环境准备

- 开发工具：推荐使用 VS Code（需安装 Vue 相关插件）
- Node.js 环境：v16+（建议使用 nvm 管理 Node 版本）
- 包管理工具：npm（Node.js 自带）或 yarn

## 2. 项目初始化

### 2.1 进入前端目录
打开终端，进入项目的前端目录：
```bash
cd AiCharacter-frontend
```

### 2.2 安装依赖
执行以下命令安装项目所需依赖：
```bash
npm install
```
> 注意：首次安装可能耗时较长，若安装失败可尝试使用 `npm install --registry=https://registry.npm.taobao.org` 切换国内镜像源

## 3. 配置 API 地址

前端需要配置与后端服务通信的 API 基础地址，有两种配置方式：

### 3.1 通过 .env.development 文件配置（推荐）
在 `AiCharacter-frontend` 目录下创建或编辑 `.env.development` 文件：
```env
# API 基础地址，需与后端服务地址一致
VITE_API_BASE_URL=http://localhost:8080/api
```

### 3.2 通过 env.ts 文件配置
编辑 `src/config/env.ts` 文件：
```typescript
// API 基础地址
export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'
```
> 优先级：.env.development 文件配置 > env.ts 文件默认值

## 4. 启动开发环境

执行以下命令启动前端开发服务器：
```bash
npm run dev
```

启动成功后，终端会显示访问地址，通常为：
```
  VITE v7.0.0  ready in X ms

  ➜  Local:   http://localhost:5173/
  ➜  Network: http://192.168.x.x:5173/
```

打开浏览器访问上述地址即可看到前端页面。



## 5. 构建生产环境
可执行以下命令构建生产环境包：
```bash
npm run build
```

构建完成后，会在 `dist` 目录下生成可部署的静态文件，可将该目录部署到 Nginx、Apache 等 Web 服务器。

## 项目后端启动

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
### 步骤 4：启动项目
在 IDEA 中找到项目的启动类 AiCharacterCoserApplication（在 com.example.aicharactercoser 包下）
右键该类，选择 Run 'AiCharacterCoserApplication' 或 Debug 'AiCharacterCoserApplication'
观察控制台输出，若出现类似以下日志，说明项目启动成功：
```
Tomcat started on port(s): 8080 (http) with context path '/api'
Started AiCharacterCoserApplication in x.x seconds (JVM running for x.x)
```
