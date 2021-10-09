package io.github.malczuuu.shadowthings.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public final class ViolationPage extends AbstractPageModel<ViolationModel> {

  @JsonCreator
  public ViolationPage(
      @JsonProperty("content") List<ViolationModel> content,
      @JsonProperty("page") Integer page,
      @JsonProperty("size") Integer size,
      @JsonProperty("total_elements") Long totalElements,
      @JsonProperty("total_pages") Integer totalPages) {
    super(content, page, size, totalElements, totalPages);
  }
}
