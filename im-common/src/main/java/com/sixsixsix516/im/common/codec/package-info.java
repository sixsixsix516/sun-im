package com.sixsixsix516.im.common.codec;

/*

  此包定义了消息的序列化协议实现，包括了消息的序列化（编码）与反序列化（解码）
  使用Protobuf序列化协议实现

  数据包如何保证安全性？
  编码内容按照如下格式定义与解析

  动态口令+版本号+消息长度+Protobuf消息内容

  字段说明：
  动态口令：定时刷新，防止被获取一直发送消息（待优化）
  版本号：当前消息的版本号，可做兼容等处理
  消息长度：解决粘包、半包问题，读取到消息长度后算作一个包
  Protobuf消息内容：真正的消息主体，直接使用Protobuf解析

 */