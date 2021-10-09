package io.github.malczuuu.shadowthings.model.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public final class TokenEnvelope {

  private final String token;

  @JsonCreator
  public TokenEnvelope(@JsonProperty("token") String token) {
    this.token = token;
  }

  @NotNull
  @Size(min = 1, max = 36)
  @JsonProperty("token")
  public String getToken() {
    return token;
  }
}
