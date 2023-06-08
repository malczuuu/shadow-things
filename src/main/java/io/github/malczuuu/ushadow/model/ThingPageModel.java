package io.github.malczuuu.ushadow.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public final class ThingPageModel extends AbstractPageModel<ThingModel> {

  @JsonCreator
  public ThingPageModel(
      @JsonProperty("content") List<ThingModel> content,
      @JsonProperty("page") Integer page,
      @JsonProperty("size") Integer size,
      @JsonProperty("total_elements") Long totalElements,
      @JsonProperty("total_pages") Integer totalPages) {
    super(content, page, size, totalElements, totalPages);
  }
}
