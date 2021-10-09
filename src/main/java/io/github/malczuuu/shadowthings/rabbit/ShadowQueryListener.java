package io.github.malczuuu.shadowthings.rabbit;

import io.github.malczuuu.shadowthings.configuration.RabbitConfiguration;
import io.github.malczuuu.shadowthings.core.ShadowService;
import io.github.malczuuu.shadowthings.model.ShadowModel;
import io.github.malczuuu.shadowthings.model.message.ShadowEnvelope;
import io.github.malczuuu.shadowthings.model.message.TokenEnvelope;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ShadowQueryListener {

  private final ShadowService shadowService;
  private final RabbitOperations rabbitOperations;

  private final Validator validator;

  public ShadowQueryListener(
      ShadowService shadowService, RabbitOperations rabbitOperations, Validator validator) {
    this.shadowService = shadowService;
    this.rabbitOperations = rabbitOperations;
    this.validator = validator;
  }

  @RabbitListener(queues = {RabbitConfiguration.SHADOW_QUERIES_QUEUE_NAME})
  public void onShadowQuery(Message message, @Payload TokenEnvelope token) {
    Optional<String> thingId = getThingUid(message.getMessageProperties().getReceivedRoutingKey());
    if (thingId.isEmpty()) {
      return;
    }

    Set<ConstraintViolation<TokenEnvelope>> violations = validator.validate(token);
    if (!violations.isEmpty()) {
      return;
    }

    ShadowModel shadow = shadowService.findShadow(thingId.get());
    ShadowEnvelope envelope = new ShadowEnvelope(shadow, token.getToken());

    rabbitOperations.convertAndSend(
        RabbitConfiguration.TOPIC_EXCHANGE,
        RabbitConfiguration.shadowMessageTopic(thingId.get()),
        envelope);
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
