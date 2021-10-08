package io.github.malczuuu.shadowthings.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

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

  @JsonProperty("reported")
  public Map<String, Object> getReported() {
    return reported;
  }

  @JsonProperty("token")
  public String getToken() {
    return token;
  }
}
