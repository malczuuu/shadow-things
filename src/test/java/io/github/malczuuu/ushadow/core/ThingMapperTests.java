package io.github.malczuuu.ushadow.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.malczuuu.ushadow.core.mapper.ThingMapper;
import io.github.malczuuu.ushadow.entity.ThingEntity;
import io.github.malczuuu.ushadow.model.ThingModel;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThingMapperTests {

  private ThingMapper thingMapper;

  @BeforeEach
  void beforeEach() {
    thingMapper = new ThingMapper();
  }

  @Test
  void shouldMapThingEntity() {
    ThingEntity entity =
        new ThingEntity(
            ObjectId.get(),
            UUID.randomUUID().toString(),
            "an example name",
            false,
            "password",
            Instant.parse("2020-05-21T06:53:41.443Z"),
            12L);

    ThingModel model = thingMapper.toModel(entity);

    assertEquals(entity.getUid(), model.getId());
    assertEquals(entity.getName(), model.getName());
    assertEquals(entity.isEnabled(), model.isEnabled());
    assertEquals(
        entity.getLastModifiedDate().truncatedTo(ChronoUnit.SECONDS).toString(),
        model.getLastModifiedDate());
    assertEquals(entity.getVersion(), model.getVersion());
  }
}
