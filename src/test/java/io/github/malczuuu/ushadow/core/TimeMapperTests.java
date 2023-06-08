package io.github.malczuuu.ushadow.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.malczuuu.ushadow.core.mapper.TimeMapper;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeMapperTests {

  private TimeMapper timeMapper;

  @BeforeEach
  void beforeEach() {
    timeMapper = new TimeMapper();
  }

  @Test
  void shouldMapInstantToIso() {
    String isoString = timeMapper.toIsoString(Instant.parse("2021-05-02T23:44:32.445Z"));

    assertEquals("2021-05-02T23:44:32Z", isoString);
  }
}
