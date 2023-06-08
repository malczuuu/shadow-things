package io.github.malczuuu.ushadow.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract class AbstractPageModel<T> {

  private final List<T> content;
  private final Integer page;
  private final Integer size;
  private final Long totalElements;
  private final Integer totalPages;

  protected AbstractPageModel(
      List<T> content, Integer page, Integer size, Long totalElements, Integer totalPages) {
    this.content = content != null ? new ArrayList<>(content) : null;
    this.page = page;
    this.size = size;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
  }

  @JsonProperty("content")
  public List<T> getContent() {
    return Collections.unmodifiableList(content);
  }

  @JsonProperty("page")
  public Integer getPage() {
    return page;
  }

  @JsonProperty("size")
  public Integer getSize() {
    return size;
  }

  @JsonProperty("total_elements")
  public Long getTotalElements() {
    return totalElements;
  }

  @JsonProperty("total_pages")
  public Integer getTotalPages() {
    return totalPages;
  }
}
