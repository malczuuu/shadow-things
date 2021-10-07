package io.github.malczuuu.shadowthings.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class PasswordModel {

  private final String password;
  private final Long version;

  @JsonCreator
  public PasswordModel(
      @JsonProperty("password") String password, @JsonProperty("version") Long version) {
    this.password = password;
    this.version = version;
  }

  @NotNull
  @NotBlank
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  @NotNull
  @Min(0)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }
}
