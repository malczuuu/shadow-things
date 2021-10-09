package io.github.malczuuu.shadowthings.core;

import java.time.Instant;
import java.time.ZoneOffset;
import org.bson.types.ObjectId;

public class TimeMapper {

  public String toIsoString(ObjectId id) {
    Instant instant = id.getDate().toInstant();
    return toIsoString(instant);
  }

  public String toIsoString(Instant instant) {
    return instant.atOffset(ZoneOffset.UTC).toString();
  }
}
