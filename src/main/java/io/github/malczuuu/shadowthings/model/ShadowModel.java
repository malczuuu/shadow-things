package io.github.malczuuu.shadowthings.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ShadowModel {

  private final Map<String, Object> desired;
  private final Map<String, Object> reported;
  private final String lastModifiedDate;
  private final Long version;

  public ShadowModel(
      Map<String, Object> desired,
      Map<String, Object> reported,
      String lastModifiedDate,
      Long version) {
    this.desired = desired != null ? new HashMap<>(desired) : null;
    this.reported = reported != null ? new HashMap<>(reported) : null;
    this.lastModifiedDate = lastModifiedDate;
    this.version = version;
  }

  @JsonProperty("desired")
  public Map<String, Object> getDesired() {
    return desired != null ? Collections.unmodifiableMap(desired) : null;
  }

  @JsonProperty("reported")
  public Map<String, Object> getReported() {
    return reported != null ? Collections.unmodifiableMap(reported) : null;
  }

  @JsonProperty("last_modified_date")
  public String getLastModifiedDate() {
    return lastModifiedDate;
  }

  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }
}
