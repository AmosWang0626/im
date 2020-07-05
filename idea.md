# im 改造篇
> console 模式实现后，一直在想着怎么实现一个 Web 版的

## 改造初期 --- 思路很重要
```text
原本想的是，改造后，Client 连接还在后端维护，前端只需访问后端即可。

经过一系列尝试后发现，Client 要想和 Server Netty 通信，还是需要用到 WebSocket 的。

这样之前 Console 模式下，Client 中定义的一系列 pipeline 就用不到了。

然后恍然大悟，服务端根本用不到过去Client那一套，服务端单纯提供服务即可。

想来也是，何为服务端，占个端口，客户端能连上而已。
```

## 改造初期 --- 前端改造
```text
前端改造，本想多个页面共用一个 Websocket

但是发现页面刷新，原先的 Websocket 就失效了，失效的时候也会调用后端的 Websocket close 方法

直接结果就是，多个页面共用一个 Websocket 是行不通的

其次，也会发现，前端共用一个 Websocket 时，后端返回的数据，只会被一个 websocket.onmessage 处理，
当然也可以通过 websocket.addEventListener 的方式监听，这样任何地方都能监听到后端返回的数据，有违初衷。

结论，前端改造成，每个页面一个Websocket

后端需要做相应的适配，如果前端携带 token 过来，token 合法，就将该 token 所属的 channel 改为最新的 channel。
```


## 吸取教训，走更远
> 2020/03/14 再次看下边内容，岂不是很有问题。。。

- console模式下，server、client，分别启动的是不同的console，所以之前保存channel的工具类是单例的。

- ~~web模式下，server、client，都在同一个环境，服务端和客户端必定是有冲突的。~~
  - ~~首先：server端，重写一个channel工具类，保存channel~~
  - ~~其次：client端，多个client是可以根据用户表示保存在一起的，所以一个工具类就够了，再不济，可以使用LocalThread。~~

- web模式下，只需提供server服务即可。

## 请求表单

### 1. 登录
```java
LoginRequest loginRequest = new LoginRequest().setUsername(username).setPassword(password);
channel.writeAndFlush(loginRequest);
```

|字段名|备注|默认值|
|---|---|---|
|username|用户名|---|
|password|密码|---|

### 2. 单聊
```java
MessageRequest request = new MessageRequest();
request.setReceiver(token).setMessage(message).setCreateTime(LocalDateTime.now());
```

|字段名|备注|默认值|
|---|---|---|
|receiver|接收人token|---|
|message|消息|---|
|createTime|创建时间|---|

### 3. 创建群聊
```java
GroupCreateRequest groupCreateRequest = new GroupCreateRequest();
groupCreateRequest.setSponsor(AttributeLoginUtil.getLoginInfo(channel).getToken())
.setGroupName(groupName).setTokenList(tokenList).setCreateTime(LocalDateTime.now());
```

|字段名|备注|默认值|
|---|---|---|
|sponsor|发起人token|---|
|groupName|群聊名称|---|
|tokenList|用户token列表|---|
|createTime|创建时间|---|

### 4. 加入群聊
```java
GroupJoinRequest groupJoinRequest = new GroupJoinRequest();
groupJoinRequest.setGroupId(groupId);
```

|字段名|备注|默认值|
|---|---|---|
|groupId|群聊ID|---|

### 5. 退出群聊
```java
GroupQuitRequest quitRequest = new GroupQuitRequest();
quitRequest.setGroupId(groupId);
```

|字段名|备注|默认值|
|---|---|---|
|groupId|群聊ID|---|

### 6. 群聊
```java
GroupMessageRequest request = new GroupMessageRequest();
request.setGroupId(groupId).setMessage(message).setCreateTime(LocalDateTime.now());
```

|字段名|备注|默认值|
|---|---|---|
|groupId|群聊ID|---|
|message|消息|---|
|createTime|创建时间|---|
