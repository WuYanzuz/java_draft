package edu.jxut.rabbit;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "boot_queue")
    public void RabbitMQLin(Channel channel,Message message) throws IOException {
            byte[] body = message.getBody();
            System.out.println(new String(body));
            try {
//                int i =1/0;
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
            } catch (IOException e) {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),true,true);
                e.printStackTrace();
            }

    }
//    @RabbitListener(queues = "boot_queue")
//    public void RabbitMQLinz(Message message){
//
//        for (int i = 0; i < 10; i++) {
//            byte[] body = message.getBody();
//            System.out.println(new String(body));
//        }
//    }

}
