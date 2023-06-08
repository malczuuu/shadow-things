package io.github.malczuuu.ushadow.core.exception;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemException;
import org.springframework.http.HttpStatus;

public class ThingDuplicateException extends ProblemException {

  public ThingDuplicateException(String id) {
    super(
        Problem.builder()
            .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .status(HttpStatus.BAD_REQUEST.value())
            .detail("Thing \"" + id + "\" duplicate")
            .build());
  }
}
