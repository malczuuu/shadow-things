package io.github.malczuuu.shadowthings.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ViolationModel {

  private final String actionType;
  private final List<ViolationRecord> violations;
  private final String createdDate;

  @JsonCreator
  public ViolationModel(
      @JsonProperty("action_type") String actionType,
      @JsonProperty("violations") List<ViolationRecord> violations,
      @JsonProperty("created_date") String createdDate) {
    this.actionType = actionType;
    this.violations = violations != null ? new ArrayList<>(violations) : null;
    this.createdDate = createdDate;
  }

  @JsonProperty("action_type")
  public String getActionType() {
    return actionType;
  }

  @JsonProperty("violations")
  public List<ViolationRecord> getViolations() {
    return violations != null ? Collections.unmodifiableList(violations) : null;
  }

  @JsonProperty("created_date")
  public String getCreatedDate() {
    return createdDate;
  }
}
