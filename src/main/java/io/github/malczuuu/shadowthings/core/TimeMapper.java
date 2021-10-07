package io.github.malczuuu.shadowthings.core;

import java.time.Instant;
import java.time.ZoneOffset;

public class TimeMapper {

  public String toIsoString(Instant instant) {
    return instant.atOffset(ZoneOffset.UTC).toString();
  }
}
