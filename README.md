### SUN-IM
使用netty实现的聊天

优化项
1. 动态口令
2. 消息体加密

待完成
1. 每个用户连接的im节点存在redis：需考虑占用
2. 发送的时候取出如果节点为当前节点，直接发送。否则通过对其他节点的通道做转发
3. 节点的connect字段更新
4. 停止节点其他节点报错问题