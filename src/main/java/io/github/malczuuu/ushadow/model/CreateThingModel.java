package io.github.malczuuu.ushadow.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public final class CreateThingModel {

  private final String id;
  private final String name;
  private final Boolean enabled;

  @JsonCreator
  public CreateThingModel(
      @JsonProperty("id") String id,
      @JsonProperty("name") String name,
      @JsonProperty("enabled") Boolean enabled) {
    this.id = id;
    this.name = name;
    this.enabled = enabled;
  }

  @NotNull
  @Size(min = 1, max = 36)
  @Pattern(regexp = "^(?:\\w|\\w{2}|\\w[\\w\\-]*\\w)$")
  @JsonProperty("id")
  public String getId() {
    return id;
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
}
