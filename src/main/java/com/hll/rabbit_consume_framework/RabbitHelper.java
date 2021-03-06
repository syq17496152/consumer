package com.hll.rabbit_consume_framework;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeoutException;

/**
 * Created by hll on 2016/4/28.
 * RabbitMQ帮助类，读取配置文件，建立和rabbitmq的连接
 */
public class RabbitHelper {

  private static Logger logger = LoggerFactory.getLogger(RabbitHelper.class);
  private static Connection connection;

  static {
    String host = "localhost";
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setUsername("guest");
    connectionFactory.setPassword("guest");
    connectionFactory.setHost(host);
    connectionFactory.setThreadFactory(r -> new Thread(r,"rabbit"));
    connectionFactory.setAutomaticRecoveryEnabled(true);
    try {
      connection = connectionFactory.newConnection();
    } catch (IOException | TimeoutException e) {
      logger.error("连接rabbitmq失败", e);
    }
  }

  public static Channel getChannel() throws IOException {
    Channel channel = connection.createChannel();
    return channel;
  }

  public static void close() throws IOException {
    connection.close();
  }

  public static void main(String[] args) {
    connection.addShutdownListener(System.out::println);
  }
}
