package io.github.malczuuu.ushadow.model.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.malczuuu.ushadow.model.constraint.ValidShadowState;
import java.util.Map;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public final class ReportedEnvelope {

  private final Map<String, Object> reported;
  private final String token;

  @JsonCreator
  public ReportedEnvelope(
      @JsonProperty("reported") Map<String, Object> reported, @JsonProperty("token") String token) {
    this.reported = reported;
    this.token = token;
  }

  @JsonProperty("type")
  public String getType() {
    return "reported";
  }

  @NotNull
  @ValidShadowState
  @JsonProperty("reported")
  public Map<String, Object> getReported() {
    return reported;
  }

  @NotNull
  @Size(min = 1, max = 36)
  @JsonProperty("token")
  public String getToken() {
    return token;
  }
}
