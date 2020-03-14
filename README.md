# IM 鸿雁
> 主要由Netty实现的即时通讯项目


## NOTICE 项目须知

### 1.项目技术栈
- Netty
- Gradle
- Docker
- Swagger
- Spring Boot

### 2.分支
- master：采用前后端分离，本项目只提供服务端处理
- master-console：原 master，本项目的 Console 方式实现

### 3.项目 Docker 打包
- gradlew build docker
- docker run -d -p 8080:8080 --name im im

### 4.核心内容
- 协议（codec 方便数据解析处理）
    - 协议里规定了魔数、版本号、序列化算法、命令、数据长度、数据域
    - 协议编码 Encode：将对象传入编码方法，返回ByteBuf数据
    - 协议解码 Decode：讲要 ByteBuf 数据传入解码方法，返回对象

- 序列化（serializable 方便数据传输）
    - 序列化：对象转byte[]
    - 反序列化：byte[]转对象

## COMM 通信知识
- TCP 三次握手
    - 客户端 第一次：你好，能听到吗？
    - 服务器 第二次：你好，能听到的。
        - 表明客户端连接服务端正常
    - 客户端 第三次：好的，我们开始谈正事！
        - 表明服务端连接客户端正常

- 互发心跳
    - 服务器给客户端发送心跳：监测客户端是否下线
    - 客户端给服务器发送心跳：监测网络是否连接正常

## 众里寻他千百度 (使用protobuf需升级Gradle)
- [org/gradle/api/attributes/LibraryElements](https://github.com/google/protobuf-gradle-plugin/issues/378)
- Task :compileJava error: cannot find symbol
  - 乍一看，gradle的问题？非也，Lombok的配置要和gradle同步升级
