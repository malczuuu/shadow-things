package io.github.malczuuu.shadowthings.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public class Violation {

  public static final String PATH = "path";
  public static final String MESSAGE = "message";

  @Field(name = PATH)
  private String path;

  @Field(name = MESSAGE)
  private String message;

  public Violation() {}

  public Violation(String path, String message) {
    this.path = path;
    this.message = message;
  }

  public String getPath() {
    return path;
  }

  public String getMessage() {
    return message;
  }
}
