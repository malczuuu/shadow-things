package io.github.malczuuu.ushadow.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
