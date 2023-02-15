package dev.yuri.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class PriceConsumer {

    // URL of the JMS server
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // default broker URL is : tcp://localhost:61616"
    private static String jmsQueue = "price";

    public String consume() {
        try {
            // Getting JMS connection from the server
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Creating session for sending messages
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);

            // Getting the queue
            Destination destination = session.createQueue(jmsQueue);

            // MessageConsumer is used for receiving (consuming) messages
            MessageConsumer consumer = session.createConsumer(destination);

            // Here we receive the message.
            Message message = consumer.receive();

            // MessageProducer sent us a TextMessage
            if (message instanceof TextMessage textMessage) {
                System.out.println("PRICE Received successfully: '" + textMessage.getText() + "'");
                String response = getResponse(textMessage);
                System.out.println(response);

                return response;
            }

            connection.close();
            return "Invalid Instance.";
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    private String getResponse(TextMessage textMessage) {
        String msg = null;
        try {
            msg = textMessage.getText();
            String resp = "";

            if (msg == null || msg.equals(""))
                resp = "Invalid price";
            else {
                double price = Double.parseDouble(msg);
                double tax = 0;

                if (price >= 50)
                    tax = (price / 100) * 10;

                resp = "Price: " + msg + " Taxes: " + tax + " Total: " + (price + tax);
            }

            return resp;
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
