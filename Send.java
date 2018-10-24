//package com.roman.rmq.publisher;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

public class Send {

    private final static String QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws java.io.IOException {



        try {
            // Create a connection
            // Abstracts socket, auth
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();

            // Create a channel
            Channel channel = connection.createChannel();

            //Declare a queue
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String message = getMessage(argv);

            // Publish a message to the queue
            //String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            channel.close();
            connection.close();
        } catch (TimeoutException tOutEx) {
            tOutEx.printStackTrace(System.err);
        }
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 1) {
            return "Hello world!";
        }
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;

        if (length == 0) {
            return "";
        }

        StringBuilder words = new StringBuilder(strings[0]);

        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }

        return words.toString();
    }

}