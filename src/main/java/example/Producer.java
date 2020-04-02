package example;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

    public static void main(String[] args){
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("TEST.QUEUE");
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            String text = "Hello world!";
            TextMessage message = session.createTextMessage(text);

            // send the JMS message
            producer.send(message);
            // returned JMS message ID must be logged
            System.out.println("JMS message ID: "+message.getJMSMessageID());

            session.close();
            connection.close();
        }
        catch (JMSException e){
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
