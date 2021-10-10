package io.github.malczuuu.shadowthings.rabbit;

import com.rabbitmq.client.Channel;
import io.github.malczuuu.shadowthings.configuration.RabbitConfiguration;
import io.github.malczuuu.shadowthings.core.ShadowService;
import io.github.malczuuu.shadowthings.core.ThingService;
import io.github.malczuuu.shadowthings.core.ViolationService;
import io.github.malczuuu.shadowthings.model.ShadowModel;
import io.github.malczuuu.shadowthings.model.message.ReportedEnvelope;
import io.github.malczuuu.shadowthings.model.message.ShadowEnvelope;
import java.io.IOException;
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
public class ShadowUpdateListener {

  private final ThingService thingService;
  private final ShadowService shadowService;
  private final ViolationService violationService;

  private final RabbitOperations rabbitOperations;
  private final Validator validator;

  public ShadowUpdateListener(
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

  // TODO handle duplication with ShadowQueryListener
  @RabbitListener(
      queues = {RabbitConfiguration.SHADOW_UPDATES_QUEUE_NAME},
      ackMode = "MANUAL")
  public void onShadowQuery(Message message, Channel channel, @Payload ReportedEnvelope reported)
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

    Set<ConstraintViolation<ReportedEnvelope>> violations = validator.validate(reported);
    if (!violations.isEmpty()) {
      violationService.storeViolation(thingId.get(), "reported_update", violations);
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
      return;
    }

    ShadowModel shadow = shadowService.updateShadow(thingId.get(), reported.getReported());
    ShadowEnvelope envelope = new ShadowEnvelope(shadow, reported.getToken());

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
    Pattern pattern = Pattern.compile(RabbitConfiguration.SHADOW_UPDATES_ROUTING_KEY_REGEXP);
    Matcher match = pattern.matcher(routingKey);
    if (match.find()) {
      return Optional.of(match.group(1));
    }
    return Optional.empty();
  }
}
