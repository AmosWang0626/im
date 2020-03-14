# im 改造篇
> console 模式实现后，一直在想着怎么实现一个 Web 版的

## 思路很重要
```text
原本想的是，改造后，Client 连接还在后端维护，前端只需访问后端即可。

经过一系列尝试后发现，Client 要想和 Server Netty 通信，还是需要用到 WebSocket 的。

这样之前 Console 模式下，Client 中定义的一系列 pipeline 就用不到了。

然后恍然大悟，服务端根本用不到过去Client那一套，服务端单纯提供服务即可。

想来也是，何为服务端，占个端口，客户端能连上而已。
```


## 吸取教训，走更远
> 2020/03/14 再次看下边内容，岂不是很有问题。。。

- console模式下，server、client，分别启动的是不同的console，所以之前保存channel的工具类是单例的。

- ~~web模式下，server、client，都在同一个环境，服务端和客户端必定是有冲突的。~~
  - ~~首先：server端，重写一个channel工具类，保存channel~~
  - ~~其次：client端，多个client是可以根据用户表示保存在一起的，所以一个工具类就够了，再不济，可以使用LocalThread。~~

- web模式下，只需提供server服务即可。