package io.github.malczuuu.ushadow.core.mapper;

public class ParamMapper {

  public int parseInteger(String param, int defaultValue, int minimalValue) {
    return parseInteger(param, defaultValue, minimalValue, Integer.MAX_VALUE);
  }

  public int parseInteger(String param, int defaultValue, int minimalValue, int maximalValue) {
    try {
      int value = Integer.parseInt(param);
      if (value < minimalValue) {
        return minimalValue;
      } else if (value > maximalValue) {
        return maximalValue;
      }
      return value;
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }
}
