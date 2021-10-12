package io.github.malczuuu.silhouette.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public final class UpdateThingModel {

  private final String name;
  private final Boolean enabled;
  private final Long version;

  @JsonCreator
  public UpdateThingModel(
      @JsonProperty("name") String name,
      @JsonProperty("enabled") Boolean enabled,
      @JsonProperty("version") Long version) {
    this.name = name;
    this.enabled = enabled;
    this.version = version;
  }

  @Pattern(regexp = "^(?:\\w|\\w{2}|\\w[\\w\\- ]*\\w)$")
  @Size(min = 1, max = 64)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("enabled")
  public boolean isEnabled() {
    return enabled != null && enabled;
  }

  @NotNull
  @Min(0)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }
}
