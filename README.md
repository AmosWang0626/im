# IM 鸿雁
> 主要由Netty实现的即时通讯项目


## 1. 项目技术栈
Netty、Gradle、Docker、Swagger、Spring Boot

## 2. 分支
```text
1.0
纯 Netty 实现的控制台聊天系统；
包括：自定义交互协议；请求编解码，解决半包/粘包等问题。

2.0
网页版聊天的服务端实现。引入 Spring Boot 相关技术；
客户端可以调用后端接口，获取 ws 地址，然后通过 WebSocket 接入。

webflux
由于 webflux 默认是以 Netty 启动的，就想着将其结合。
但由于其封装太好，与实践 Netty 相去甚远，仅简单实现了单聊功能。
```

## 3. Docker
- gradlew build docker
- docker run -d -p 8080:8080 --name im im
