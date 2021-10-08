package io.github.malczuuu.shadowthings.rabbit;

import io.github.malczuuu.shadowthings.configuration.RabbitConfiguration;
import io.github.malczuuu.shadowthings.core.ShadowService;
import io.github.malczuuu.shadowthings.model.ReportedEnvelope;
import io.github.malczuuu.shadowthings.model.ShadowEnvelope;
import io.github.malczuuu.shadowthings.model.ShadowModel;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ShadowUpdateListener {

  private final ShadowService shadowService;
  private final RabbitOperations rabbitOperations;

  public ShadowUpdateListener(ShadowService shadowService, RabbitOperations rabbitOperations) {
    this.shadowService = shadowService;
    this.rabbitOperations = rabbitOperations;
  }

  @RabbitListener(queues = {RabbitConfiguration.SHADOW_UPDATES_QUEUE_NAME})
  public void onShadowQuery(Message message, @Payload ReportedEnvelope reported) {
    Optional<String> thingId = getThingUid(message.getMessageProperties().getReceivedRoutingKey());
    if (thingId.isEmpty()) {
      return;
    }

    ShadowModel shadow = shadowService.updateShadow(thingId.get(), reported.getReported());
    ShadowEnvelope envelope = new ShadowEnvelope(shadow, reported.getToken());

    rabbitOperations.convertAndSend(
        RabbitConfiguration.TOPIC_EXCHANGE,
        RabbitConfiguration.shadowMessageTopic(thingId.get()),
        envelope);
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
