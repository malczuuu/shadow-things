package io.github.malczuuu.silhouette.core.mapper;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import org.bson.types.ObjectId;

public class TimeMapper {

  public String toIsoString(ObjectId id) {
    Instant instant = id.getDate().toInstant();
    return toIsoString(instant);
  }

  public String toIsoString(Instant instant) {
    return instant.truncatedTo(ChronoUnit.SECONDS).atOffset(ZoneOffset.UTC).toString();
  }
}
