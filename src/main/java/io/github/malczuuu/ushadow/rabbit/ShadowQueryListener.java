package io.github.malczuuu.ushadow.rabbit;

import com.rabbitmq.client.Channel;
import io.github.malczuuu.ushadow.configuration.RabbitConfiguration;
import io.github.malczuuu.ushadow.core.ShadowService;
import io.github.malczuuu.ushadow.core.ThingService;
import io.github.malczuuu.ushadow.core.ViolationService;
import io.github.malczuuu.ushadow.model.ShadowModel;
import io.github.malczuuu.ushadow.model.message.ShadowEnvelope;
import io.github.malczuuu.ushadow.model.message.TokenEnvelope;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ShadowQueryListener {

  private final ThingService thingService;
  private final ShadowService shadowService;
  private final ViolationService violationService;

  private final RabbitOperations rabbitOperations;
  private final Validator validator;

  public ShadowQueryListener(
      ThingService thingService,
      ShadowService shadowService,
      ViolationService violationService,
      RabbitOperations rabbitOperations,
      Validator validator) {
    this.thingService = thingService;
    this.shadowService = shadowService;
    this.violationService = violationService;
    this.rabbitOperations = rabbitOperations;
    this.validator = validator;
  }

  // TODO handle duplication with ShadowUpdateListener
  @RabbitListener(
      queues = {RabbitConfiguration.SHADOW_QUERIES_QUEUE_NAME},
      ackMode = "MANUAL")
  public void onShadowQuery(Message message, Channel channel, @Payload TokenEnvelope token)
      throws IOException {
    Optional<String> thingId = getThingUid(message.getMessageProperties().getReceivedRoutingKey());
    if (thingId.isEmpty()) {
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
      return;
    }

    if (!thingService.doesThingExists(thingId.get())) {
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
      return;
    }

    Set<ConstraintViolation<TokenEnvelope>> violations = validator.validate(token);
    if (!violations.isEmpty()) {
      violationService.storeViolation(thingId.get(), "shadow_query", violations);
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
      return;
    }

    ShadowModel shadow = shadowService.findShadow(thingId.get());
    ShadowEnvelope envelope = new ShadowEnvelope(shadow, token.getToken());

    rabbitOperations.convertAndSend(
        RabbitConfiguration.TOPIC_EXCHANGE,
        RabbitConfiguration.shadowMessageTopic(thingId.get()),
        envelope);

    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
  }

  private Optional<String> getThingUid(String routingKey) {
    if (routingKey == null) {
      return Optional.empty();
    }
    Pattern pattern = Pattern.compile(RabbitConfiguration.SHADOW_QUERIES_ROUTING_KEY_REGEXP);
    Matcher match = pattern.matcher(routingKey);
    if (match.find()) {
      return Optional.of(match.group(1));
    }
    return Optional.empty();
  }
}
