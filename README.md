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

## 网络通信常识
- TCP 三次握手
    - [第一次] C客户端：你好，能听到吗？
    - [第二次] S服务器：你好，能听到的。
        - S服务端：客户端 发送正常；
    - [第三次] C客户端：好的，我们开始谈正事！
        - C客户端：服务端 接收正常 & 发送正常；
        - S服务端：客户端 接收正常。

- TCP 四次挥手
    - [第一次] C客户端-FIN：好了，我说完了，就这样吧 [C -> FIN_WAIT1]
    - [第二次] S服务端-ACK：等一下，还有点事你注意下。。。[S -> CLOSE_WAIT] | [C -> FIN_WAIT2]
    - [第三次] S服务端-FIN：好了，我也说完了，挂了吧 [S -> LAST_ASK] | [C -> TIME_WAIT]
    - [第四次] C客户端-ACK：好的 [C 等待] | [S -> CLOSED] C客户端会礼貌地等S服务端一会儿 [C -> CLOSED]

- 互发心跳
    - 服务器给客户端发送心跳：监测客户端是否下线
    - 客户端给服务器发送心跳：监测网络是否连接正常

### TCP/UDP
> TCP/IP协议是一个协议簇，里面包括很多协议的，之所以命名为TCP/IP协议，因为TCP、IP协议是两个很重要的协议，就用他两命名了。
- 分为三个层次：网络层、传输层和应用层
  - 网络层：IP协议、ICMP协议、ARP协议、RARP协议和BOOTP协议；
  - 传输层：TCP协议与UDP协议；
  - 应用层：FTP、HTTP、TELNET、SMTP、DNS等协议；
- TCP 面向连接，可靠，传输大量数据，速度慢
- UDP 面向非连接，不可靠，传输少量数据，速度快

## 众里寻他千百度 (使用protobuf需升级Gradle)
- [org/gradle/api/attributes/LibraryElements](https://github.com/google/protobuf-gradle-plugin/issues/378)
- Task :compileJava error: cannot find symbol
  - 乍一看，gradle的问题？非也，Lombok的配置要和gradle同步升级
