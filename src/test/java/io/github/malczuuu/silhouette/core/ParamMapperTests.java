package io.github.malczuuu.silhouette.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.malczuuu.silhouette.core.mapper.ParamMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParamMapperTests {

  private ParamMapper paramMapper;

  @BeforeEach
  void beforeEach() {
    paramMapper = new ParamMapper();
  }

  @Test
  void shouldParseParam() {
    int parsedParam = paramMapper.parseInteger("32", 20, 0);

    assertEquals(32, parsedParam);
  }

  @Test
  void shouldGreaterMaximalValueHaveNoImpact() {
    int parsedParam = paramMapper.parseInteger("32", 20, 0, 50);

    assertEquals(32, parsedParam);
  }

  @Test
  void shouldTrimValueByMaximalValue() {
    int parsedParam = paramMapper.parseInteger("32", 20, 0, 30);

    assertEquals(30, parsedParam);
  }

  @Test
  void shouldTrimValueByMinimalValue() {
    int parsedParam = paramMapper.parseInteger("32", 45, 40);

    assertEquals(40, parsedParam);
  }

  @Test
  void shouldTakeDefaultValueOnInvalidValue() {
    int parsedParam = paramMapper.parseInteger("invalid", 20, 0);

    assertEquals(20, parsedParam);
  }
}
