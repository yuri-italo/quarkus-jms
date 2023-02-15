package dev.yuri.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class PriceSender {

    //URL of the JMS server.
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // default broker URL is : tcp://localhost:61616"
    private static String jmsQueue = "price";

    public void send(String price) {
        try {
            // Getting JMS connection from the server and starting it
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            //Creating a session to send/receive JMS message.
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);

            //The queue will be created automatically on the server.
            Destination destination = session.createQueue(jmsQueue);

            // MessageProducer is used for sending messages to the queue.
            MessageProducer producer = session.createProducer(destination);

            // We will send a small text message with small Json'
            TextMessage message = session
                    .createTextMessage(price);

            // Here we are sending our message!
            producer.send(message);

            System.out.println("PRICE Sent successfully: " + message.getText());
            connection.close();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }
}
