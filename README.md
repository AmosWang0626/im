# im
> instant messaging 鸿雁
> - Netty Gradle Docker

## build docker image

- gradlew build docker
- docker run -d -p 8080:8080 --name im im

## 通信知识
- TCP 三次握手
  - 客户端 第一次：喂，你好，能听到吗？
  - 服务器 第二次：你好，能听到的。
    - 表明客户端连接服务端正常
  - 客户端 第三次：好的，Say something！
    - 表明服务端连接客户端正常

- TCP 四次挥手
  - 主动结束放 第一次：断了吧
  - 另一方------ 第二次：等下断
  - 另一方------ 第三次：断了吧
  - 另一方------ 第四次：断了

- 互发心跳
  - 服务器给客户端发送心跳：监测客户端是否下线
  - 客户端给服务器发送心跳：监测网络是否连接正常