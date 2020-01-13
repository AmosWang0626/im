# im
> console 模式已实现，现在计划实现 web 版

## 登录

## 在线

## 单聊

## 群聊

## 吸取教训，走更远
- console模式下，server、client，分别启动的是不同的console，
所以之前保存channel的工具类是单例的。
- web模式下，server、client，都在同一个环境，服务端和客户端必定是有冲突的。
  - 首先：server端，重写一个channel工具类，保存channel
  - 其次：client端，多个client是可以根据用户表示保存在一起的，所以一个工具类就够了，
  再不济，可以使用LocalThread。