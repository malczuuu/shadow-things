package io.github.malczuuu.shadowthings.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ThingModel {

  private final String id;
  private final String name;
  private final Boolean enabled;
  private final String lastModifiedDate;
  private final Long version;

  @JsonCreator
  public ThingModel(
      @JsonProperty("id") String id,
      @JsonProperty("name") String name,
      @JsonProperty("enabled") Boolean enabled,
      @JsonProperty("last_modified_date") String lastModifiedDate,
      @JsonProperty("version") Long version) {
    this.id = id;
    this.name = name;
    this.enabled = enabled;
    this.lastModifiedDate = lastModifiedDate;
    this.version = version;
  }

  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("enabled")
  public boolean isEnabled() {
    return enabled != null && enabled;
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
