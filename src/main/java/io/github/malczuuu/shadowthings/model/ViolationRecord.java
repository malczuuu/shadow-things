package io.github.malczuuu.shadowthings.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ViolationRecord {

  private final String path;
  private final String message;

  @JsonCreator
  public ViolationRecord(
      @JsonProperty("path") String path, @JsonProperty("message") String message) {
    this.path = path;
    this.message = message;
  }

  @JsonProperty("path")
  public String getPath() {
    return path;
  }

  @JsonProperty("message")
  public String getMessage() {
    return message;
  }
}
