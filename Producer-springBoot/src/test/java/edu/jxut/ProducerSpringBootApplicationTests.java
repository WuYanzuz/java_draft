package edu.jxut;

import edu.jxut.rabbit.config.RabbitMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerSpringBootApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void contextLoads() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"boot.aaa","妹妹号啊");
    }

    @Test
    public void TestConfirm(){
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                if (b) {
                    System.out.println("接受消息成功！" + s);
                } else {
                    System.out.println("接受消息失败！" + s);
                }
            }
        });

        rabbitTemplate.convertAndSend("boot_topic_exchange","boot.zzz","love");
    }

    @Test
    public void TestReturn(){
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                System.out.println(message);
                System.out.println(i);
                System.out.println(s);
                System.out.println(s1);
                System.out.println(s2);
            }
        });

        rabbitTemplate.convertAndSend("boot_topic_exchange","boot.zzz","baga".getBytes(StandardCharsets.UTF_8));
    }
}
