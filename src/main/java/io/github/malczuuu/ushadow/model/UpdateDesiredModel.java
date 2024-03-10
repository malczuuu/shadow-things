package io.github.malczuuu.ushadow.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.malczuuu.ushadow.model.constraint.ValidShadowState;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public final class UpdateDesiredModel {

  private final Map<String, Object> desired;
  private final Long version;

  @JsonCreator
  public UpdateDesiredModel(
      @JsonProperty("desired") Map<String, Object> desired, @JsonProperty("version") Long version) {
    this.desired = desired;
    this.version = version;
  }

  @NotNull
  @ValidShadowState
  @JsonProperty("desired")
  public Map<String, Object> getDesired() {
    return desired;
  }

  @NotNull
  @Min(0)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }
}
