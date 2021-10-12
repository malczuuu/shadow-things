package io.github.malczuuu.silhouette.configuration;

import java.util.HashMap;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfiguration {

  public static final String TOPIC_EXCHANGE = "amq.topic";
  public static final String SHADOW_QUERIES_QUEUE_NAME = "shadow-queries";
  public static final String SHADOW_UPDATES_QUEUE_NAME = "shadow-updates";
  public static final String SHADOW_QUERIES_ROUTING_KEY = "api.things.*.shadow.query";
  public static final String SHADOW_UPDATES_ROUTING_KEY = "api.things.*.shadow.update";
  public static final String SHADOW_QUERIES_ROUTING_KEY_REGEXP =
      "^api\\.things\\.(.*)\\.shadow\\.query$";
  public static final String SHADOW_UPDATES_ROUTING_KEY_REGEXP =
      "^api\\.things\\.(.*)\\.shadow\\.update$";

  public static String shadowMessageTopic(String thingId) {
    return "api.things." + thingId + ".shadow.receive";
  }

  @Bean
  public Queue shadowQueriesQueue() {
    return new Queue(SHADOW_QUERIES_QUEUE_NAME, true, false, false);
  }

  @Bean
  public Queue shadowUpdatesQueue() {
    return new Queue(SHADOW_UPDATES_QUEUE_NAME, true, false, false);
  }

  @Bean
  public Binding shadowQueryBinding() {
    return new Binding(
        SHADOW_QUERIES_QUEUE_NAME,
        DestinationType.QUEUE,
        TOPIC_EXCHANGE,
        SHADOW_QUERIES_ROUTING_KEY,
        new HashMap<>());
  }

  @Bean
  public Binding shadowUpdateBinding() {
    return new Binding(
        SHADOW_UPDATES_QUEUE_NAME,
        DestinationType.QUEUE,
        TOPIC_EXCHANGE,
        SHADOW_UPDATES_ROUTING_KEY,
        new HashMap<>());
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
