### 安装插件（windows版本）
F:\softs\rabbitmq\rabbitmq_server-3.8.5\sbin> rabbitmq-plugins.bat enable rabbitmq_management
F:\softs\rabbitmq\rabbitmq_server-3.8.5\sbin> rabbitmq‐plugins enable rabbitmq_web_stomp
### Spring集成Rabbitmq
```
<dependency>
    <groupId>org.springframework.amqp</groupId>
    <artifactId>spring-rabbit</artifactId>
    <version>2.2.9.RELEASE</version>
</dependency>
```

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:rabbit="http://www.springframework.org/schema/rabbit"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/rabbit
        http://www.springframework.org/schema/rabbit/spring-rabbit.xsd">
    <rabbit:connection-factory id="connectionFactory" host="127.0.0.1" port="5672" username="guest" password="guest" />

    <rabbit:template id="rabbitTemplate" connection-factory="connectionFactory" />

</beans>
```

### 发送消息
Direct模式：exchange参数为空，routingKey为queue名称
Fanout模式：exchange为exchange名称，routingKey为空表示群发，若指定则发生到指定的queue
```
rabbitTemplate.convertAndSend("", "queue.test", "direct model发送测试息");
rabbitTemplate.convertAndSend("exchange.fanout-test", "", "Fanout模式测试消息");
```

### 消息监听
```
public class MessageConsumer implements MessageListener {
    public void onMessage(Message message) {
        System.out.println("接收到消息：" + new String(message.getBody())
        );
    }
}
```
Direct模式
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:rabbit="http://www.springframework.org/schema/rabbit"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/rabbit
        http://www.springframework.org/schema/rabbit/spring-rabbit.xsd">
    <rabbit:connection-factory id="connectionFactory" host="127.0.0.1" port="5672" username="guest" password="guest" />

    <rabbit:queue name="queue.test" />

    <bean id="messageConsumer" class="com.demos.queue.MessageConsumer"/>

    <rabbit:listener-container connection-factory="connectionFactory" >
        <rabbit:listener queue-names="queue.test" ref="messageConsumer"/>
    </rabbit:listener-container>
</beans>
```

Fanout模式
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:rabbit="http://www.springframework.org/schema/rabbit"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/rabbit
        http://www.springframework.org/schema/rabbit/spring-rabbit.xsd">
    <rabbit:connection-factory id="connectionFactory" host="127.0.0.1" port="5672" username="guest" password="guest" />

    <rabbit:queue name="queue.test1" />
    <rabbit:queue name="queue.test2" />

    <bean id="messageConsumer1" class="com.demos.fanout.MessageConsumer1"/>
    <bean id="messageConsumer2" class="com.demos.fanout.MessageConsumer2"/>

    <rabbit:listener-container connection-factory="connectionFactory" >
        <rabbit:listener queue-names="queue.test1" ref="messageConsumer1"/>
        <rabbit:listener queue-names="queue.test2" ref="messageConsumer2"/>
    </rabbit:listener-container>
</beans>
```

### Rabbitmq Admin
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:rabbit="http://www.springframework.org/schema/rabbit"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/rabbit
        http://www.springframework.org/schema/rabbit/spring-rabbit.xsd">

    <rabbit:connection-factory id="connectionFactory" host="127.0.0.1" virtual-host="/" port="5672" username="guest" password="guest" />

    <rabbit:admin id="rabbitAdmin" connection-factory="connectionFactory"/>
</beans>
```

创建fanout exchange
```
public class RabbitAdminExample {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring-rabbit-admin.xml");
        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
        FanoutExchange fanoutExchange = new FanoutExchange("created-fanout-exchange", true, true);
        rabbitAdmin.declareExchange(fanoutExchange);
        System.out.println("exchange created.");

        Queue queue1 = new Queue("created-queue1", true, false, true);
        rabbitAdmin.declareQueue(queue1);
        System.out.println("queue created.");

        Binding binding = BindingBuilder.bind(queue1).to(fanoutExchange);
        rabbitAdmin.declareBinding(binding);
        System.out.println("binding created.");

        context.close();

    }
}
```

### 实现延时消息
通过设置队列的ttl时间和死信exchange可以实现延时消息
* x-message-ttl: 超时时间，单位：毫秒
* x-dead-letter-exchange: 死信消息exchange
* x-dead-letter-routing-key: 死信消息exchange绑定的queue
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:rabbit="http://www.springframework.org/schema/rabbit"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/rabbit
        http://www.springframework.org/schema/rabbit/spring-rabbit.xsd">

    <rabbit:connection-factory id="connectionFactory" host="127.0.0.1" virtual-host="/" port="5672" username="guest" password="guest" />

    <rabbit:template id="rabbitTemplate" connection-factory="connectionFactory" />

    <!--死信队列-->
    <rabbit:queue name="dead-letter-queue" auto-declare="true"/>
    <!--死信exchange 同时将死信队列绑定到exchange-->
    <rabbit:fanout-exchange name="dead-letter-exchange" auto-declare="true">
        <rabbit:bindings>
            <rabbit:binding queue="dead-letter-queue" />
        </rabbit:bindings>
    </rabbit:fanout-exchange>

    <!--设置消息队列超时和死信exchange-->
    <rabbit:queue id="ttl-queue" name="ttl-queue" auto-declare="true" >
        <rabbit:queue-arguments>
            <entry key="x-message-ttl" value="5000" value-type="long"/>
            <entry key="x-dead-letter-exchange" value="dead-letter-exchange"/>
            <entry key="x-dead-letter-routing-key" value=""/>
        </rabbit:queue-arguments>
    </rabbit:queue>

    <bean id="consumer1" class="com.demos.ttl.MessageConsumer"/>
    <bean id="consumer2" class="com.demos.ttl.DeadMessageConsumer"/>

    <rabbit:listener-container connection-factory="connectionFactory">
        <rabbit:listener ref="consumer1" queue-names="ttl-queue"/> <!--去掉这一行可以测试死信消息-->
        <rabbit:listener ref="consumer2" queue-names="dead-letter-queue"/>
    </rabbit:listener-container>

</beans>
```

### 使用Stomp.js连接rabbitmq
```
<script th:src="@{/static/stomp.min.js}"></script>
    <script>
        var client = Stomp.client('ws://127.0.0.1:15674/ws');
        /*
         fanout模式 /exchange/fanout-web (需要手动创建exchange，会自动对每个消费者生成一个queue)
         direct模式 /queue/queue-web (会自动创建queue)
          */
        var topic = "/exchange/fanout-web";
        var on_connect = function(x) {
            id = client.subscribe(topic, function(d) {
                alert(d.body);
            });
        };
        var on_error = function() {
            console.log('error');
        };
        client.connect('webguest', 'webguest', on_connect, on_error, '/web');
    </script>
```