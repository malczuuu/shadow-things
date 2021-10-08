package io.github.malczuuu.shadowthings.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TokenModel {

  private final String token;

  @JsonCreator
  public TokenModel(@JsonProperty("token") String token) {
    this.token = token;
  }

  @JsonProperty("token")
  public String getToken() {
    return token;
  }
}
