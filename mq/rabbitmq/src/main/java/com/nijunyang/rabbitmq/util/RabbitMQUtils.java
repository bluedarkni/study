package com.nijunyang.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description:
 * Created by nijunyang on 2020/5/27 10:24
 */
public abstract class RabbitMQUtils {

    private static ConnectionFactory connectionFactory = new ConnectionFactory();
    //设置连接工厂信息
    static {
        connectionFactory.setHost("111.229.53.45");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
    }

    public static Connection getConnection() throws IOException, TimeoutException {

        Connection connection = connectionFactory.newConnection();
        return connection;
    }

    public static void close(AutoCloseable...closeables) throws Exception {
        for (AutoCloseable closeable : closeables) {
            closeable.close();
        }
    }

}
