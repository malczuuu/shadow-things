package io.github.malczuuu.silhouette.model.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.malczuuu.silhouette.model.ShadowModel;

public final class ShadowEnvelope {

  private final ShadowModel shadow;
  private final String token;

  @JsonCreator
  public ShadowEnvelope(
      @JsonProperty("shadow") ShadowModel shadow, @JsonProperty("token") String token) {
    this.shadow = shadow;
    this.token = token;
  }

  @JsonProperty("type")
  public String getType() {
    return "shadow";
  }

  @JsonProperty("shadow")
  public ShadowModel getShadow() {
    return shadow;
  }

  @JsonProperty("token")
  public String getToken() {
    return token;
  }
}
