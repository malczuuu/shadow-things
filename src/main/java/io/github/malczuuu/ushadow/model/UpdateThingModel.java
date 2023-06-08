package io.github.malczuuu.ushadow.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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
